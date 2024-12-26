package _2024._05;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

record Rule(int a, int b) {
}

class Solver implements ISolver {
    ArrayList<ArrayList<Integer>> updates = new ArrayList<>();
    ArrayList<Rule> rules = new ArrayList<>();

    public void solve(ArrayList<String> lines) {
        int i = 0;
        while (!lines.get(i).isBlank()) {
            var vals = Arrays.stream(lines.get(i).split("\\|")).mapToInt(Integer::parseInt).toArray();
            rules.add(new Rule(vals[0], vals[1]));
            i++;
        }
        i++;
        while (!lines.get(i).isBlank()) {
            updates.add(Arrays.stream(lines.get(i).split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new)));
            i++;
        }

        int sum = 0;
        for (var update : updates) {
            if (isValidUpdate(update)) {
                sum += update.get((int) Math.floor((float) (update.size() / 2)));
            } else {

            }
        }
        System.out.println(sum);
    }

    boolean isValidUpdate(ArrayList<Integer> update) {
        for (int idx = 0; idx < update.size(); idx++) {
            int finalIdx = idx;
            var leftRules = rules.stream().filter(r -> r.a() == update.get(finalIdx)).toList();
            var rightRules = rules.stream().filter(r -> r.b() == update.get(finalIdx)).toList();

            if (update.subList(0, idx).stream()
                    .anyMatch(val -> leftRules.stream().anyMatch(r -> r.b() == val))
                    || update.subList(idx + 1, update.size()).stream()
                    .anyMatch(val -> rightRules.stream().anyMatch(r -> r.a() == val))
            ) {
                return false;
            }
        }
        return true;
    }
}

public class Day05 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 5, new Solver());
    }
}