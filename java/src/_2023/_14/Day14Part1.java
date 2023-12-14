package _2023._14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Solver {
    void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_14\\input.txt"));
        int nbLines = (int) reader.lines().count();
        reader = new BufferedReader(new FileReader("src\\_2023\\_14\\input.txt"));

        String ligne = reader.readLine();
        char[][] values = new char[nbLines][ligne.length()];

        int i = 0;
        while (ligne != null) {
            values[i] = ligne.toCharArray();

            i++;
            ligne = reader.readLine();
        }

        for (int col = 0; col < values[0].length; col++) {
            for (int upperBound = 1; upperBound <= values.length - 1; upperBound++) {
                for (int l = upperBound; l > 0; l--) {
                    if (values[l][col] == 'O' && values[l - 1][col] == '.') {
                        values[l][col] = '.';
                        values[l - 1][col] = 'O';
                    }
                }
            }
        }

        int score = 0;
        for (int l = 0; l < values.length; l++) {
            int nbRock = 0;
            for (int col = 0; col < values[0].length; col++) {
                if (values[l][col] == 'O') {
                    nbRock++;
                }
            }
            score += nbRock*(values.length - l);
        }
        System.out.println("\n\n"+score);
    }
}

public class Day14Part1 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Solver solver = new Solver();
        solver.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}

