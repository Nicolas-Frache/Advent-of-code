package _2024._24;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

enum GateType {AND, XOR, OR}

class Gate {
    Wire leftInput, rightInput, output;
    GateType type;

    public Gate(Wire leftInput, Wire rightInput, Wire output, GateType type) {
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.output = output;
        this.type = type;
    }

    void evaluate() {
        if (leftInput.value == null || rightInput.value == null) {
            throw new RuntimeException("Gate has no values");
        }

        output.value = switch (type) {
            case AND -> leftInput.value && rightInput.value;
            case XOR -> leftInput.value ^ rightInput.value;
            case OR -> leftInput.value || rightInput.value;
        };
    }
}

class Wire {
    String name;
    Boolean value = null;

    public Wire(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public Wire(String name) {
        this.name = name;
    }
}

class Solver implements ISolver {
    HashMap<String, Wire> wires = new HashMap<>();
    ArrayList<Gate> gates = new ArrayList<>();

    public void solve(ArrayList<String> lines) {
        var pattern = Pattern.compile("(.{3}): (\\d)");

        int l = 0;
        while (!lines.get(l).isBlank()) {
            var match = pattern.matcher(lines.get(l));
            match.find();
            wires.put(match.group(1), new Wire(match.group(1), match.group(2).equals("1")));
            l++;
        }
        l++;
        pattern = Pattern.compile("(.{3}) (AND|OR|XOR) (.{3}) -> (.{3})");
        while (l < lines.size()) {
            var match = pattern.matcher(lines.get(l));
            match.find();

            var left = match.group(1);
            var right = match.group(3);
            var output = match.group(4);
            wires.putIfAbsent(left, new Wire(left));
            wires.putIfAbsent(right, new Wire(right));
            wires.putIfAbsent(output, new Wire(output));
            var gateType = switch (match.group(2)) {
                case "AND" -> GateType.AND;
                case "OR" -> GateType.OR;
                case "XOR" -> GateType.XOR;
                default -> null;
            };
            gates.add(new Gate(wires.get(left), wires.get(right), wires.get(output), gateType));
            l++;
        }

        var zWires = wires.values().stream().filter(w -> w.name.startsWith("z")).toList();
        while (zWires.stream().anyMatch(z -> z.value == null)) {
            gates.stream()
                    .filter(g -> g.output.value == null && g.leftInput.value != null && g.rightInput.value != null)
                    .forEach(Gate::evaluate);
        }
        var zlist = zWires.stream()
                .sorted(Comparator.comparing(wire -> wire.name))
                .collect(Collectors.toCollection(ArrayList::new));

        long b = 1;
        long sum = 0;
        while (!zlist.isEmpty()) {
            if (zlist.removeFirst().value) {
                sum += b;
            }
            b *= 2;
        }
        System.out.println(sum);
    }
}


public class Day24 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 24, new Solver());
    }
}