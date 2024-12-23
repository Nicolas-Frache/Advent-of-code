package _2024._12;

import utils.ArraysUtils;
import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;

class Solver implements ISolver {
    public void solve(ArrayList<String> lines) {
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
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 12, new Solver());
    }
}