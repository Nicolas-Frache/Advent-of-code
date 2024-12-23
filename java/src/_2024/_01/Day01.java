package _2024._01;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

class Solver implements ISolver {
    public void solve(ArrayList<String> lines) {
        ArrayList<Integer> left = new ArrayList<>(), right = new ArrayList<>();
        for (String l : lines) {
            var x = l.split(" {3}");
            left.add(Integer.parseInt(x[0]));
            right.add(Integer.parseInt(x[1]));
        }

        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);
        int sum = 0;
        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(left.get(i) - right.get(i));
        }
        System.out.println("part 1: " + sum);

        int similarityScore = 0;
        for(var x : left){
            similarityScore += (int) (x * right.stream().filter((bi) -> Objects.equals(bi, x)).count());
        }
        System.out.println("part 2: " + similarityScore);
    }
}


public class Day01 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 1, new Solver());
    }
}