package _2024;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Solver implements ISolver {
    public void solve(ArrayList<String> lines) {
        for(String line : lines){
            var list = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
        }

    }
}


public class DayXX {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 1, new Solver());
    }
}