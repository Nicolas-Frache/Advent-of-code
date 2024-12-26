package _2024._06;

import utils.Dir;
import utils.Grid;
import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

enum Type {EMPTY, OBSTACLE, START}

class Tile {
    Type type;
    boolean isVisited = false;
    HashMap<Dir, Boolean> visited = new HashMap<>();

    public Tile(Type type) {
        this.type = type;
    }

    static Function<String, Tile> parser = s -> switch (s) {
        case "#" -> new Tile(Type.OBSTACLE);
        case "^" -> new Tile(Type.START);
        default -> new Tile(Type.EMPTY);
    };

    @Override
    public String toString() {
        return (isVisited ? "X" : ".");
    }
}

class Solver implements ISolver {
    Grid<Tile> grid;

    public void solve(ArrayList<String> lines) {
        grid = new Grid<>(lines, "", Tile.parser);
        visit();
        System.out.println(grid.getCells(c -> c.val.isVisited).size());

        AtomicInteger sum = new AtomicInteger();

        grid.getCells(c -> c.val.type == Type.EMPTY).forEach(c -> {
            c.val.type = Type.OBSTACLE;
            grid.map.values().forEach(t -> {
                t.val.isVisited = false;
                t.val.visited = new HashMap<>();
            });
            if (visit()) {
                sum.getAndIncrement();
            }
            c.val.type = Type.EMPTY;
        });
        System.out.println(sum.get());
    }

    public boolean visit() {
        Grid<Tile>.Cell guard = grid.getFirstCell(c -> c.val.type == Type.START);
        guard.val.isVisited = true;
        Dir dir = Dir.T;

        while (true) {
            var next = guard.getDir(dir);
            if (next == null) return false;
            if (next.val.type == Type.OBSTACLE) {
                dir = switch (dir) {
                    case L -> Dir.T;
                    case R -> Dir.B;
                    case T -> Dir.R;
                    case B -> Dir.L;
                    default -> null;
                };
                continue;
            }
            guard = next;
            guard.val.isVisited = true;
            if (guard.val.visited.getOrDefault(dir, false)) {
                return true;
            }
            guard.val.visited.put(dir, true);
        }
    }
}

public class Day6 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 6, new Solver());
    }
}