package _2023._21;

import utils.ArraysUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Solver {
    static int nbRows;
    static int nbCols;

    void solve_(ArrayList<String> lines, boolean part1) {
        int N = part1 ? 64 : 131 * 2 + 65;
        int FACTOR = part1 ? 1 : 6;
        // ====>    3832 + 15158 x + 14977 x^2 with x = 202300    <=====
        // interpolating polynomial {0,3832},{1,33967},{2,94056}

        nbRows = lines.size();
        nbCols = lines.get(0).length();

        char[][] map = new char[nbRows][nbCols];
        int startRow = 0, startCol = 0;

        int row = 0;
        for (String line : lines) {
            int col = 0;
            for (char c : line.toCharArray()) {
                if (c == 'S') {
                    c = '.';
                    startRow = row;
                    startCol = col;
                }
                map[row][col] = c;
                col++;
            }
            row++;
        }
        map = ArraysUtils.extendArray(map, FACTOR);
        map[(nbRows * FACTOR / 2) + startRow][(nbRows * FACTOR / 2) + startCol] = 'O';

        nbRows = map.length;
        nbCols = map[0].length;
        char[][] previousMap;

        if (!part1) {
            System.out.println("<======= https://www.wolframalpha.com/ =======>");
            System.out.print("interpolating polynomial ");
        }

        for (int i = 0; i < N; i++) {
            previousMap = map;
            map = new char[nbRows][nbCols];

            for (int r = 0; r < nbRows; r++) {
                for (int c = 0; c < nbCols; c++) {
                    if (previousMap[r][c] == '#') {
                        map[r][c] = '#';
                        continue;
                    }

                    map[r][c] = '.';
                    outerLoop:
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if ((dr == 0 && dc == 0) || (dr != 0 && dc != 0)) {
                                continue;
                            }
                            int rr = r + dr;
                            int cc = c + dc;
                            if (rr < 0 || rr >= nbRows || cc < 0 || cc >= nbCols) {
                                continue;
                            }
                            if (previousMap[rr][cc] == 'O') {
                                map[r][c] = 'O';
                                break outerLoop;
                            }
                        }
                    }

                }
            }
            if (!part1 && ((i + 2) + 65) % lines.size() == 0) {
                int count = Arrays.stream(map)
                        .flatMapToInt(chars -> new String(chars).chars()).map(c -> c == 'O' ? 1 : 0).sum();
                System.out.print("{" + i / 131 + "," + count + "},");
            }
        }


        if (part1) {
            int count = Arrays.stream(map)
                    .flatMapToInt(chars -> new String(chars).chars()).map(c -> c == 'O' ? 1 : 0).sum();
            System.out.println("Part1 : " + count);
        } else {
            System.out.println("\n============================");
            System.out.println("Part2: Use the given formula with x=202300 in https://www.wolframalpha.com/");
        }

    }
}

public class Day21 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_21\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        Solver solver = new Solver();
        solver.solve_(lines, true);
        solver.solve_(lines, false);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}