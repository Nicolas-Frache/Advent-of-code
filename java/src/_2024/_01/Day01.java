package _2024._01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

class Solver {
    void solve(ArrayList<String> lines) {
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
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        System.out.println(Day01.class.getClassLoader().getResource("").getPath());

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_01\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        Solver solver = new Solver();
        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}