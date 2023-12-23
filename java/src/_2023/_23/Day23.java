package _2023._23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Solver {
    char[][] map;
    int len;
    static boolean part1;

    String solve(ArrayList<String> lines, boolean part1) {
        Solver.part1 = part1;
        len = lines.size();
        map = new char[len][len];

        for (int i = 0; i < len; i++) {
            map[i] = lines.get(i).toCharArray();
        }
        map[0][1] = 'O';

        return ""+dijkstraStep(new Pos(1, 1));
    }

    int dijkstraStep(Pos pos) {
        if (pos.x() == len - 2 && pos.y() == len - 1) {
            return 1;
        }

        int max = 0;
        for (int i = 0; i < Pos.dx.length; i++) {
            Pos newPos = new Pos(pos.x() + Pos.dx[i].x(), pos.y() + Pos.dx[i].y());
            char other = map[newPos.y()][newPos.x()];
            if (other == '.'
                    || other == 'v' && (i == 0 || !part1)
                    || other == '^' && (i == 1 || !part1)
                    || other == '>' && (i == 2 || !part1)
                    || other == '<' && (i == 3 || !part1)) {
                map[newPos.y()][newPos.x()] = 'O';

                max = Math.max(max, dijkstraStep(newPos));

                map[newPos.y()][newPos.x()] = other;
            }
        }
        return max != 0 ? max + 1 : 0;
    }
}

record Pos(int x, int y) {
    static Pos[] dx = new Pos[]{new Pos(0, 1), new Pos(0, -1), new Pos(1, 0), new Pos(-1, 0)};
}


public class Day23 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_23\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        Solver solver = new Solver();
        System.out.println("Part 1: " +solver.solve(lines, true));
        System.out.println("Part 2: " +solver.solve(lines, false));

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}
