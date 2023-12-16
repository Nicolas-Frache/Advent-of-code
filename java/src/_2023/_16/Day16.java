package _2023._16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import static _2023._16.Tile.*;

class Solver {
    Tile[][] panel;
    int nbLines, nbCol;
    int max = 0;

    void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_16\\input.txt"));
        Stream<String> streamReader = new BufferedReader(new FileReader("src\\_2023\\_16\\input.txt")).lines();
        nbLines = (int) streamReader.count() + 2;
        streamReader = new BufferedReader(new FileReader("src\\_2023\\_16\\input.txt")).lines();
        nbCol = streamReader.findFirst().get().length() + 2;

        // PADDING
        panel = new Tile[nbLines][nbCol];
        for (int i = 0; i < nbCol; i++) {
            panel[0][i] = BORDER;
            panel[nbLines - 1][i] = BORDER;
        }
        for (int i = 0; i < nbLines; i++) {
            panel[i][0] = BORDER;
            panel[i][nbCol - 1] = BORDER;
        }

        // PARSING
        reader = new BufferedReader(new FileReader("src\\_2023\\_16\\input.txt"));
        int l = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            int col = 1;
            for (char symbol : line.toCharArray()) {
                panel[l][col] = new Tile(symbol, l, col);
                col++;
            }
            l++;
        }

        testStart(1, 1, RIGHT);
        System.out.println("Part 1 : " + Tile.NB_ENERGISED);

        for (int col = 1; col < nbCol - 2; col++) {
            testStart(1, col, DOWN);
            testStart(nbLines - 2, col, TOP);
        }
        for (int lig = 1; lig < nbLines - 2; lig++) {
            testStart(lig, 1, RIGHT);
            testStart(lig, nbCol - 1, LEFT);
        }
        System.out.println("Part 2 : " + max);
    }

    public void testStart(int l, int c, int direction) {
        Tile.NB_ENERGISED = 0;
        panel[l][c].travel(panel, direction);
        for (int i = 1; i < nbLines - 1; i++) {
            for (int j = 1; j < nbCol - 1; j++) {
                panel[i][j].reset();
            }
        }
        if (Tile.NB_ENERGISED > max) {
            max = Tile.NB_ENERGISED;
        }
    }

}

class Tile {
    char symbol;
    boolean isEnergised;
    int l, c;
    boolean[] traversedByDir = new boolean[4];
    static int NB_ENERGISED = 0;

    final static int LEFT = 0, RIGHT = 1, TOP = 2, DOWN = 3;
    final static Tile BORDER = new Tile('X', -1, -1);

    public Tile(char symbol, int l, int c) {
        this.symbol = symbol;
        isEnergised = false;
        this.l = l;
        this.c = c;
    }

    public void travel(Tile[][] panel, int direction) {
        if (this == BORDER || traversedByDir[direction]) {
            return;
        }
        if (!traversedByDir[0] && !traversedByDir[1] && !traversedByDir[2] && !traversedByDir[3]) {
            Tile.NB_ENERGISED++;
        }
        this.isEnergised = true;
        this.traversedByDir[direction] = true;

        int newLine = l;
        int newCol = c;
        int dir = -1;

        if (symbol == '.') {
            dir = direction;
        } else if (symbol == '/') {
            switch (direction) {
                case LEFT -> dir = DOWN;
                case RIGHT -> dir = TOP;
                case TOP -> dir = RIGHT;
                case DOWN -> dir = LEFT;
            }
        } else if (symbol == '\\') {
            switch (direction) {
                case LEFT -> dir = TOP;
                case RIGHT -> dir = DOWN;
                case TOP -> dir = LEFT;
                case DOWN -> dir = RIGHT;
            }
        } else if (symbol == '|') {
            switch (direction) {
                case LEFT, RIGHT -> {
                    panel[newLine + 1][newCol].travel(panel, DOWN);
                    dir = TOP;
                }
                case TOP, DOWN -> dir = direction;
            }
        } else if (symbol == '-') {
            switch (direction) {
                case TOP, DOWN -> {
                    panel[newLine][newCol + 1].travel(panel, RIGHT);
                    dir = LEFT;
                }
                case LEFT, RIGHT -> dir = direction;
            }
        }

        switch (dir){
            case TOP -> panel[newLine - 1][newCol].travel(panel, TOP);
            case DOWN -> panel[newLine + 1][newCol].travel(panel, DOWN);
            case LEFT -> panel[newLine][newCol - 1].travel(panel, LEFT);
            case RIGHT -> panel[newLine][newCol + 1].travel(panel, RIGHT);
        }

    }

    public void reset() {
        this.isEnergised = false;
        for (int i = 0; i < 4; i++) {
            this.traversedByDir[i] = false;
        }
    }
}

public class Day16 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Solver solver = new Solver();
        solver.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}
