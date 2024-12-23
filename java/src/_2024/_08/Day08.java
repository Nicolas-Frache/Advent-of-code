package _2024._08;

import utils.ISolver;
import utils.PuzzleRunner;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Solver implements ISolver {
    int nbLigne;
    int nbCol;

    public void solve(ArrayList<String> lines) {
        char[][] tab = new char[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            tab[i] = lines.get(i).toCharArray();
        }
        nbLigne = tab.length;
        nbCol = tab[0].length;

        HashMap<Character, ArrayList<Point>> frequencies = new HashMap<>();
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbCol; j++) {
                if (tab[i][j] != '.') {
                    frequencies.putIfAbsent(tab[i][j], new ArrayList<>());
                    frequencies.get(tab[i][j]).add(new Point(i, j));
                }
            }
        }
        boolean[][] containsAntinode = new boolean[nbLigne][nbCol];
        for (ArrayList<Point> antennas : frequencies.values()) {
            for (int i = 0; i < antennas.size(); i++) {
                var a = antennas.get(i);
                containsAntinode[a.x][a.y] = true;
                for (int j = i + 1; j < antennas.size(); j++) {
                    var b = antennas.get(j);
                    containsAntinode[b.x][b.y] = true;
                    var tested = b.getLocation();
                    while (isValidPos(translate(tested, b.x - a.x, b.y - a.y))) {
                        containsAntinode[tested.x][tested.y] = true;
                    }
                    tested = a.getLocation();
                    while (isValidPos(translate(tested, a.x - b.x, a.y - b.y))) {
                        containsAntinode[tested.x][tested.y] = true;
                    }

                }
            }
        }
        int sum = 0;
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbCol; j++) {
                if (containsAntinode[i][j]) {
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }

    Point translate(Point point, int dx, int dy) {
        point.translate(dx, dy);
        return point;
    }

    boolean isValidPos(Point p) {
        return p.x >= 0 && p.y >= 0 && p.x < nbLigne && p.y < nbCol;
    }
}

public class Day08 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 8, new Solver());
    }
}