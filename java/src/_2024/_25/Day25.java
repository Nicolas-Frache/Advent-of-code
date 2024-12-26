package _2024._25;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;

class Solver implements ISolver {
    public void solve(ArrayList<String> lines) {
        ArrayList<int[]> locks = new ArrayList<>(), keys = new ArrayList<>();

        for (int patternIdx = 0; patternIdx < lines.size(); patternIdx += 8) {
            int[] arr = new int[5];
            for (int ligne = 1; ligne <= 5; ligne++) {
                var chars = lines.get(patternIdx + ligne).toCharArray();
                for (int col = 0; col < 5; col++) {
                    if (chars[col] == '#') {
                        arr[col]++;
                    }
                }
            }
            if (lines.get(patternIdx).startsWith("#")) {
                locks.add(arr);
            } else {
                keys.add(arr);
            }
        }

        int fitting = 0;
        for(var lock : locks) {
            for (var key : keys) {
                boolean valid = true;
                for (int i = 0; i < 5; i++) {
                    if(lock[i] + key[i] > 5) {
                        valid = false;
                        break;
                    }
                }
                if(valid) {
                    fitting++;
                }
            }
        }
        System.out.println(fitting);
    }
}


public class Day25 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 25, new Solver());
    }
}