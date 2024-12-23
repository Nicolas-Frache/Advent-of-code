package _2024._10;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static utils.ArraysUtils.addPadding;

class Solver implements ISolver {
    int[][] grid;

    public void solve(ArrayList<String> lines) {
        var arrayInput = new int[lines.size()][];
        int i = 0;
        for (String line : lines) {
            var lineArr = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray();
            arrayInput[i] = lineArr;
            i++;
        }
        grid = addPadding(arrayInput, 1, -1);
        int totalScore1 = 0;
        int totalScore2 = 0;
        for (int j = 0; j < grid.length; j++) {
            for (int k = 0; k < grid[j].length; k++) {
                if (grid[j][k] == 0) {
                    totalScore1 += doPathStep(j, k, -1, new boolean[grid.length][grid[j].length], true);
                    totalScore2 += doPathStep(j, k, -1, new boolean[grid.length][grid[j].length], false);
                }
            }
        }
        System.out.println(totalScore1);
        System.out.println(totalScore2);
    }

    int doPathStep(int x, int y, int previousValue, boolean[][] visited, boolean isPart1) {
        int val = grid[x][y];
        if ((isPart1 && visited[x][y]) || val != previousValue + 1) {
            return 0;
        }
        visited[x][y] = true;
        if (val == 9) {
            return 1;
        }
        int score = 0;
        score += doPathStep(x - 1, y, val, visited, isPart1);
        score += doPathStep(x + 1, y, val, visited, isPart1);
        score += doPathStep(x, y + 1, val, visited, isPart1);
        score += doPathStep(x, y - 1, val, visited, isPart1);

        return score;
    }

}


public class Day10 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 10, new Solver());
    }
}