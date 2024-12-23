package _2024._23;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

class Node {
    public String name;
    public ArrayList<Node> linked = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }
}

class Solver implements ISolver {
    HashMap<String, Node> nodes = new HashMap<>();
    ArrayList<ArrayList<Node>> cycles = new ArrayList<>();
    ArrayList<Node> largestSet = new ArrayList<>();
    ArrayList<ArrayList<Node>> visited = new ArrayList<>();

    public void solve(ArrayList<String> lines) {
        for (String line : lines) {
            var parts = line.split("-");
            nodes.putIfAbsent(parts[0], new Node(parts[0]));
            nodes.putIfAbsent(parts[1], new Node(parts[1]));
            nodes.get(parts[0]).linked.add(nodes.get(parts[1]));
            nodes.get(parts[1]).linked.add(nodes.get(parts[0]));
        }

        nodes.values().forEach(n -> {
            var cycle = new ArrayList<Node>();
            cycle.add(n);
            search(cycle);
        });
        System.out.println(cycles.size());
        for (ArrayList<Node> cycle : cycles) {
            extendsCycle(cycle);
        }
        System.out.println(largestSet.size());
        largestSet.stream().sorted(Comparator.comparing(o -> o.name))
                .forEach(o -> System.out.print(o.name + ","));
    }

    void extendsCycle(ArrayList<Node> cycle) {
        if (visited.stream().anyMatch(v -> v.containsAll(cycle))) {
            return;
        }

        var valids = nodes.values().stream()
                .filter(n -> cycle.stream().noneMatch(o -> o.name.equals(n.name)))
                .filter(n -> cycle.stream().allMatch(o -> o.linked.contains(n)))
                .toList();
        if (valids.isEmpty()) {
            if (cycle.size() > largestSet.size()) {
                largestSet = new ArrayList<>(cycle);
            }
            return;
        }

        for (Node node : valids) {
            cycle.add(node);
            extendsCycle(cycle);
            visited.add(cycle);
            cycle.removeLast();
        }
        visited.removeIf(v -> v.containsAll(cycle));
        visited.add(cycle);
    }

    void search(ArrayList<Node> currentChain) {
        if (currentChain.size() == 3
                // && currentChain.stream().anyMatch(n -> n.name.startsWith("t"))
                && currentChain.getLast().linked.contains(currentChain.getFirst())
                && cycles.stream().noneMatch(c -> c.containsAll(currentChain))) {
            cycles.add(new ArrayList<>(currentChain));
            return;
        }
        if (currentChain.size() == 3) {
            return;
        }

        var last = currentChain.getLast();
        for (var next : last.linked) {
            if (!currentChain.contains(next)) {
                currentChain.add(next);
                search(currentChain);
                currentChain.removeLast();
            }
        }
    }
}

public class Day23 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 23, new Solver());
    }
}