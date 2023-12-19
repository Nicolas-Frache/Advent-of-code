package _2023._17;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

class Solver {
    static int nbLines, nbCol;
    static Block[][] map;
    static State[][][][] states;
    final static int LEFT = 0, RIGHT = 1, TOP = 2, DOWN = 3, NO_DIRECTION = 4;
    static State finalState;

    static PriorityQueue<BlockState> queue;
    static PriorityQueue<State> statesQueue;

    void solve() throws IOException {
        BufferedReader reader;
        Stream<String> streamReader = new BufferedReader(new FileReader("src\\_2023\\_17\\input.txt")).lines();
        nbLines = (int) streamReader.count() + 2;
        streamReader = new BufferedReader(new FileReader("src\\_2023\\_17\\input.txt")).lines();
        nbCol = streamReader.findFirst().get().length() + 2;

        // PADDING
        map = new Block[nbLines][nbCol];
        states = new State[nbLines][nbCol][5][3];

        for (int i = 0; i < nbCol; i++) {
            map[0][i] = Block.BORDER;
            map[nbLines - 1][i] = Block.BORDER;
        }
        for (int i = 0; i < nbLines; i++) {
            map[i][0] = Block.BORDER;
            map[i][nbCol - 1] = Block.BORDER;
        }

        // PARSING
        reader = new BufferedReader(new FileReader("src\\_2023\\_17\\input.txt"));
        int l = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            int col = 1;
            for (char symbol : line.toCharArray()) {
                map[l][col] = new Block(l, col, Integer.parseInt("" + symbol));
                col++;
            }
            l++;
        }

        for (int a = 0; a < nbLines; a++) {
            for (int b = 0; b < nbCol; b++) {
                for (int c = 0; c < 5; c++) {
                    for (int d = 0; d < 3; d++) {
                        states[a][b][c][d] = new State(a, b, c, d);
                    }
                }
            }
        }
        statesQueue = new PriorityQueue<>(Comparator.comparingInt(s -> s.minValue));
        State start = states[1][1][NO_DIRECTION][0];
        start.minValue = 0;
        statesQueue.add(start);

        while (!statesQueue.isEmpty()) {
            //System.out.println(state.toProcess().l + ", " + state.toProcess().c + ", " + state.cost() + ", " + state.isHorizontal());
            //if (statesQueue.contains() == map[nbLines - 2][nbCol - 2]) {
            //    break;
            //}
            //System.out.println(statesQueue.peek().minValue);
            exploreState(statesQueue.remove());
            if (finalState != null) {
                System.out.println("-> " + finalState.minValue);
                //break;
            }
        }
        ArrayList<State> finalPath = new ArrayList<>();
        State current = finalState;
        while (current != null) {
            finalPath.add(current);
            System.out.println("(" + current.row + ", " + current.col + ") -> " + current.minValue);
            current = current.previous;
        }
        for (int i = 1; i < nbLines - 2; i++) {
            for (int j = 1; j < nbCol - 2; j++) {
                int finalI = i;
                int finalJ = j;
                String s = finalPath.stream().filter(a -> a.row == finalI && a.col == finalJ).map(a -> "X").findFirst().orElse(".");
                System.out.print(s);
            }
            System.out.println();
        }
    }

    public void exploreState(State s) {
        if (s.isVisited) {
            return;
        }
        s.isVisited = true;
        if (s.row == nbLines - 2 && s.col == nbCol - 2) {
            finalState = s;
            return;
        }

        ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(LEFT, RIGHT, TOP, DOWN));
        switch (s.dir) {
            case TOP -> dirs.remove((Integer) DOWN);
            case DOWN -> dirs.remove((Integer) TOP);
            case LEFT -> dirs.remove((Integer) RIGHT);
            case RIGHT -> dirs.remove((Integer) LEFT);
        }

        for (int dir : dirs) {
            if (s.timesMoved == 2 && dir == s.dir) {
                continue;
            }
            int newL = s.row;
            int newC = s.col;
            int newTimesMoved = 1;
            if (dir == s.dir) {
                newTimesMoved = s.timesMoved + 1;
            }

            switch (dir) {
                case LEFT -> newC--;
                case RIGHT -> newC++;
                case TOP -> newL--;
                case DOWN -> newL++;
            }
            if (map[newL][newC] == Block.BORDER) {
                continue;
            }
            State testedState = states[newL][newC][dir][newTimesMoved];
            if (testedState.minValue == Integer.MAX_VALUE || testedState.minValue > s.minValue + map[newL][newC].cost) {
                testedState.minValue = s.minValue + map[newL][newC].cost;
                testedState.previous = s;
                statesQueue.add(testedState);
            }

        }
    }


}

class State {
    int row, col, dir, timesMoved;
    int minValue = Integer.MAX_VALUE;
    State previous;
    boolean isVisited;

    public State(int row, int col, int dir, int timesMoved) {
        this.row = row;
        this.col = col;
        this.dir = dir;
        this.timesMoved = timesMoved;
    }
}

record BlockState(Block toProcess, Block previous, boolean isHorizontal, int cost, boolean isFirst) {
}

class Block {
    final static int LEFT = 0, RIGHT = 1, TOP = 2, DOWN = 3;
    public int l, c, cost;
    int minCost = Integer.MAX_VALUE;
    Block previous;
    boolean isVisitedV, isVisitedH, isVisited, inFinalPath, isHorizontalMin;

    public static Block BORDER = new Block(-1, -1, 0);

    Block(int l, int c, int cost) {
        this.l = l;
        this.c = c;
        this.cost = cost;
    }

    void computeMinCost(BlockState state) {
        if (this == BORDER
                || state.isHorizontal() && isVisitedH
                || !state.isHorizontal() && isVisitedH) {
            //|| isVisited) {
            return;
        }
        if (state.isHorizontal()) {
            isVisitedH = true;
        } else {
            isVisitedV = true;
        }
        //isVisited = true;

        if (l == 3 && c == 12) {
            System.out.println("stop");
        }

        int[] dirs = (state.isFirst()) ? new int[]{RIGHT, DOWN} : (!state.isHorizontal()) ? new int[]{LEFT, RIGHT} : new int[]{TOP, DOWN};
        for (int dir : dirs) {
            int newL = l;
            int newC = c;
            int currentDistanceFromThis = 0;


            for (int i = 0; i < 3; i++) {
                switch (dir) {
                    case LEFT -> newC--;
                    case RIGHT -> newC++;
                    case TOP -> newL--;
                    case DOWN -> newL++;
                }

                Block other = Solver.map[newL][newC];
                if (other == BORDER) {
                    break;
                }
                currentDistanceFromThis += other.cost;

                if (other.minCost == Integer.MAX_VALUE
                        || other.minCost > this.minCost + currentDistanceFromThis) {
                    other.minCost = this.minCost + currentDistanceFromThis;
                    other.previous = this;
                    other.isHorizontalMin = !state.isHorizontal();

                    Solver.queue.add(new BlockState(other, this,
                            !state.isHorizontal(), currentDistanceFromThis + this.minCost, false));

                }

            }
        }

    }

}


public class Day17 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Solver solver = new Solver();
        solver.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}