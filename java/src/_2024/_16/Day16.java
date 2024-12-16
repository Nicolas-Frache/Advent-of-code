package _2024._16;

import utils.Dir;
import utils.Grid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

enum TileTypes {WALL, START, END, EMPTY;}

class Tile implements Comparable<Tile> {
    public int minCost = Integer.MAX_VALUE;
    public ArrayList<Dir> dirForMinCost = new ArrayList<>();
    public ArrayList<Grid<Tile>.Cell> predecessor = new ArrayList<>();
    public TileTypes type;
    public boolean seatIsCounted, inSolution;

    public Tile(TileTypes type) {
        this.type = type;
    }

    static Function<String, Tile> getConverter() {
        return s -> switch (s) {
            case "#" -> new Tile(TileTypes.WALL);
            case "S" -> new Tile(TileTypes.START);
            case "E" -> new Tile(TileTypes.END);
            default -> new Tile(TileTypes.EMPTY);
        };
    }

    @Override
    public int compareTo(Tile o) {
        return Integer.compare(minCost, o.minCost);
    }

    @Override
    public String toString() {
        if (minCost != Integer.MAX_VALUE) {
            return String.format("|%5d", minCost);
        }
        return "|-----";
    }
}

class Solver {
    void solve(ArrayList<String> lines) {
        Grid<Tile> grid = new Grid<>(lines, "", Tile.getConverter());
        Grid<Tile>.Cell target = grid.getFirstCell(c -> c.val.type == TileTypes.END);
        Grid<Tile>.Cell start = grid.getFirstCell(c -> c.val.type == TileTypes.START);
        ArrayList<Grid<Tile>.Cell> reachable = grid.getCells(c -> c.val.type != TileTypes.WALL);

        start.val.minCost = 0;
        start.val.dirForMinCost.add(Dir.R);

        while (!reachable.isEmpty()) {
            doDijkstraStep(reachable);
        }
        System.out.println(target.val.minCost);

        // PART 2
        Grid<Tile> grid2 = new Grid<>(lines, "", Tile.getConverter());
        var start2 = grid2.getFirstCell(c -> c.val.type == TileTypes.END);
        start2.val.minCost = 0;
        start2.val.dirForMinCost.add(Dir.B);
        ArrayList<Grid<Tile>.Cell> reachable2 = grid2.getCells(c -> c.val.type != TileTypes.WALL);
        while (!reachable2.isEmpty()) {
            doDijkstraStep(reachable2);
        }

        grid.getCells(c -> c.val.minCost + grid2.map.get(c.pos).val.minCost == target.val.minCost)
                .forEach(c -> c.val.inSolution = true);
        int res = grid.getCells(c -> c.val.inSolution).stream().mapToInt(this::countSeat).sum();
        System.out.println(res);
    }

    void doDijkstraStep(ArrayList<Grid<Tile>.Cell> reachable) {
        reachable.sort(Comparator.comparing(o -> o.val));
        var visiting = reachable.removeFirst();

        var dirs = new Dir[]{Dir.L, Dir.R, Dir.T, Dir.B};
        for (var dir : dirs) {
            Tile toTest = visiting.getDir(dir).val;
            if (toTest.type == TileTypes.WALL) {
                continue;
            }
            int newScore = visiting.val.minCost + 1;
            if (!visiting.val.dirForMinCost.contains(dir)) {
                newScore += 1000;
            }
            if (newScore < toTest.minCost) {
                toTest.minCost = newScore;
                toTest.predecessor.clear();
                toTest.dirForMinCost.clear();
            }else if(newScore == toTest.minCost) {
                toTest.dirForMinCost.add(dir);
                toTest.predecessor.add(visiting);
            }
        }
    }

    int countSeat(Grid<Tile>.Cell current) {
        if (current.val.seatIsCounted) {
            return 0;
        }
        int sum = 1;
        for (var pred : current.val.predecessor) {
            sum += countSeat(pred);
        }
        current.val.seatIsCounted = true;
        return sum;
    }
}


public class Day16 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_16\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        Solver solver = new Solver();
        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}