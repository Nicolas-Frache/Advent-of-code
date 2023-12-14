package _2023._14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class SolverP2 {
    char[][] values;

    void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_14\\input.txt"));
        int nbLines = (int) reader.lines().count();
        reader = new BufferedReader(new FileReader("src\\_2023\\_14\\input.txt"));

        String ligne = reader.readLine();
        values = new char[nbLines][ligne.length()];
        int score = 0;

        int i = 0;
        while (ligne != null) {
            values[i] = ligne.toCharArray();

            i++;
            ligne = reader.readLine();
        }

        ArrayList<char[][]> cache = new ArrayList<>();
        ArrayList<Integer> cacheI = new ArrayList<>();

        float pourcent = 0;
        int cycle = 0;
        int nbCycle = 1000000000;

        int count = 0;
        while (cycle < nbCycle) {
            up();
            left();
            down();
            right();

            boolean skipped = false;
            for (int cacheIdx = 0; cacheIdx < cache.size(); cacheIdx++) {
                if (Arrays.deepEquals(cache.get(cacheIdx), values)) {
                    int looplen = (cycle - cacheI.get(cacheIdx));
                    int remaining = nbCycle - cycle;
                    cycle += remaining - (remaining % looplen);
                    skipped = true;

                    cache.remove(cacheIdx);
                    cacheI.remove(cacheIdx);

                    break;
                }
            }

            if (!skipped) {
                var c = new char[nbLines][values[0].length];
                for (int l = 0; l < values.length; l++) {
                    for (int col = 0; col < values[0].length; col++) {
                        c[l][col] =  values[l][col];
                    }
                }
                cache.add(c);
                cacheI.add(cycle);

                score = getLoad();

                cycle++;
                count++;
            }
        }
        System.out.println("nb tour: " + count);
        System.out.println("\n\n" + score);
    }

    void up() {
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
    }

    void left() {
        for (int li = 0; li < values.length; li++) {
            for (int righterBound = 1; righterBound <= values[0].length - 1; righterBound++) {
                for (int col = righterBound; col > 0; col--) {
                    if (values[li][col] == 'O' && values[li][col - 1] == '.') {
                        values[li][col] = '.';
                        values[li][col - 1] = 'O';
                    }
                }
            }
        }
    }

    void down() {
        for (int col = 0; col < values[0].length; col++) {
            for (int lowerBound = values.length - 1; lowerBound >= 0; lowerBound--) {
                for (int l = lowerBound; l < values.length - 1; l++) {
                    if (values[l][col] == 'O' && values[l + 1][col] == '.') {
                        values[l][col] = '.';
                        values[l + 1][col] = 'O';
                    }
                }
            }
        }
    }

    void right() {
        for (int li = 0; li < values.length; li++) {
            for (int lefterBound = values[0].length - 2; lefterBound >= 0; lefterBound--) {
                for (int col = lefterBound; col <= values[0].length - 2; col++) {
                    if (values[li][col] == 'O' && values[li][col + 1] == '.') {
                        values[li][col] = '.';
                        values[li][col + 1] = 'O';
                    }
                }
            }
        }
    }

    int getLoad() {
        int score = 0;
        for (int l = 0; l < values.length; l++) {
            int nbRock = 0;
            for (int col = 0; col < values[0].length; col++) {
                if (values[l][col] == 'O') {
                    nbRock++;
                }
            }
            score += nbRock * (values.length - l);
        }
        return score;
    }

}


public class Day14Part2 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();


        SolverP2 solver = new SolverP2();
        solver.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}

