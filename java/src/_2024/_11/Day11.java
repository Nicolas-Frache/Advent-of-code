package _2024._11;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class Solver implements ISolver {
    HashMap<Long, Node> allNodes, toAdd;

    public void solve(ArrayList<String> lines) {
        part2(lines, 25);
        part2(lines, 75);
    }

    void part2(ArrayList<String> lines, int nbBlink) {
        var list = Arrays.stream(lines.getFirst().split(" "))
                .map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new));

        allNodes = new HashMap<>();
        toAdd = new HashMap<>();

        list.forEach(n -> {
            allNodes.putIfAbsent(n, new Node(n));
            allNodes.get(n).currentNumber++;
        });

        for (int i = 0; i < nbBlink; i++) {
            for (Node node : allNodes.values()) {
                if (node.currentNumber == 0) {
                    continue;
                }
                if (node.childs == null) {
                    node.childs = new ArrayList<>();
                    long val = node.val;
                    String valStr = Long.toString(val);
                    if (val == 0) {
                        node.addChild(1L);
                    } else if (valStr.length() % 2 == 0) {
                        node.addChild(Long.valueOf(valStr.substring(valStr.length() / 2)));
                        node.addChild(Long.valueOf(valStr.substring(0, valStr.length() / 2)));
                    } else {
                        node.addChild(val * 2024);
                    }
                }
                node.doOneStep();
            }
            allNodes.putAll(toAdd);
            toAdd.clear();
            allNodes.values().forEach(Node::reset);
        }
        var sum = (Long) allNodes.values().stream().mapToLong(e -> e.currentNumber).sum();
        System.out.println(sum);
    }

    class Node {
        public long val;
        public long currentNumber, newlyCreatedNumber;
        public ArrayList<Node> childs;

        public Node(long val) {
            this.val = val;
            currentNumber = 0;
            newlyCreatedNumber = 0;
        }

        public void addChild(Long val) {
            Node child;
            if (allNodes.containsKey(val)) {
                child = allNodes.get(val);
            } else {
                if (toAdd.containsKey(val)) {
                    child = toAdd.get(val);
                } else {
                    child = new Node(val);
                    toAdd.put(val, child);
                }
            }
            this.childs.add(child);
        }

        public void doOneStep() {
            childs.forEach(child -> child.newlyCreatedNumber += this.currentNumber);
        }

        public void reset() {
            currentNumber = newlyCreatedNumber;
            newlyCreatedNumber = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(val);
        }
    }
}

public class Day11 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 11, new Solver());
    }
}