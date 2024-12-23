package _2024._15;

import utils.Dir;
import utils.Grid;
import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

enum Tile {
    EMPTY, WALL, ROBOT, BOX, BOX_L, BOX_R;

    static Function<String, Tile> getConverter() {
        return s -> switch (s) {
            case "#" -> Tile.WALL;
            case "@" -> Tile.ROBOT;
            case "O" -> Tile.BOX;
            case "[" -> Tile.BOX_L;
            case "]" -> Tile.BOX_R;
            default -> Tile.EMPTY;
        };
    }
}

class Solver implements ISolver {
    Grid<Tile> grid;
    ArrayList<Character> moves;

    private static final Map<Character, Dir> DIRECTION_MAP = Map.of(
            '^', Dir.T, 'v', Dir.B, '<', Dir.L, '>', Dir.R
    );

    public void solve(ArrayList<String> lines) {
        ArrayList<String> mapInput = new ArrayList<>();
        int i = 0;
        while (!lines.get(i).isEmpty()) {
            mapInput.add(lines.get(i));
            i++;
        }
        grid = new Grid<>(mapInput, "", Tile.getConverter());

        moves = new ArrayList<>();
        i++;
        while (i < lines.size() && !lines.get(i).isEmpty()) {
            int finalI = i;
            IntStream.range(0, lines.get(i).length()).forEachOrdered(j -> moves.add(lines.get(finalI).charAt(j)));
            i++;
        }

        solveP1();
        System.out.println(getScore());

        // Partie 2
        mapInput = new ArrayList<>();
        i = 0;
        while (!lines.get(i).isEmpty()) {
            mapInput.add(lines.get(i)
                    .replaceAll("\\.", "..")
                    .replaceAll("#", "##")
                    .replaceAll("O", "[]")
                    .replaceAll("@", "@."));
            i++;
        }
        grid = new Grid<>(mapInput, "", Tile.getConverter());

        solveP2();
        System.out.println(getScore());
    }

    void solveP1() {
        var robotCell = grid.getFirstCell(c -> c.val == Tile.ROBOT);
        for (Character dirc : moves) {
            Dir dir = DIRECTION_MAP.get(dirc);
            if (moveP1(robotCell, dir)) {
                robotCell = robotCell.getDir(dir);
            }
        }
    }

    boolean moveP1(Grid<Tile>.Cell cell, Dir dir) {
        if (cell.val == Tile.EMPTY) {
            return true;
        }
        if (cell.val == Tile.WALL) {
            return false;
        }
        var target = cell.getDir(dir);
        if (moveP1(target, dir)) {
            grid.switchCellValues(cell, target);
            return true;
        }
        return false;
    }

    void solveP2() {
        var robotCell = grid.getFirstCell(c -> c.val == Tile.ROBOT);
        for (Character dirc : moves) {
            Dir dir = DIRECTION_MAP.get(dirc);
            var target = robotCell.getDir(dir);
            if (canMoveP2(target, dir)) {
                moveP2(target, dir);
                grid.switchCellValues(robotCell, target);
                robotCell = target;
            }
        }
    }

    boolean canMoveP2(Grid<Tile>.Cell cell, Dir dir) {
        if (cell.val == Tile.EMPTY) {
            return true;
        }
        if (cell.val == Tile.WALL) {
            return false;
        }
        var target = cell.getDir(dir);
        var dirOtherPart = (cell.val == Tile.BOX_L) ? Dir.R : Dir.L;
        var otherPartTarget = cell.getDir(dirOtherPart).getDir(dir);

        if (dir == Dir.T || dir == Dir.B) { // Déplacement vertical : on essaie de bouger les deux morceaux
            return canMoveP2(otherPartTarget, dir) && canMoveP2(target, dir);
        }
        return canMoveP2(otherPartTarget, dir); // Horizontal : Seulement celui à l'extremité
    }

    void moveP2(Grid<Tile>.Cell cell, Dir dir) {
        if (cell.val == Tile.EMPTY) {
            return;
        }
        var otherPart = cell.getDir((cell.val == Tile.BOX_L) ? Dir.R : Dir.L);
        var target1 = cell.getDir(dir);
        var target2 = otherPart.getDir(dir);

        if (dir == Dir.T || dir == Dir.B) { // On bouge devant le premier morceau uniquement si déplacement vertical
            moveP2(target1, dir);
        }
        moveP2(target2, dir);
        grid.switchCellValues(otherPart, target2);
        grid.switchCellValues(cell, target1);
    }

    int getScore() {
        return grid.getCells(c -> c.val == Tile.BOX || c.val == Tile.BOX_L).stream()
                .mapToInt(c -> c.pos.x + 100 * c.pos.y)
                .sum();
    }
}

public class Day15 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 15, new Solver());
    }
}