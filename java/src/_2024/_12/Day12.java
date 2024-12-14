package _2024._12;

import utils.ArraysUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Solver {
    void solve(ArrayList<String> lines) {
        var array = new char[lines.size()][];
        int i = 0;
        for(String line : lines){
            array[i] = line.toCharArray();
            i++;
        }
        array = ArraysUtils.addPadding(array,1, '.');
        boolean[][] visited = new boolean[array.length][array[0].length];



    }
}


public class Day12 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_12\\input.txt"));
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