package _2024._15;

import utils.Dir;
import utils.Grid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.IntStream;

class Solver {
    Grid<Tile> grid;
    ArrayList<Character> moves;
    void solve(ArrayList<String> lines) {
        ArrayList<String> mapInput = new ArrayList<>();
        int i = 0;
        while (!lines.get(i).isEmpty()) {
            mapInput.add(lines.get(i));
            i++;
        }
        grid = new Grid<>(mapInput, "", Tile.getConverter());

        moves = new ArrayList<>();
        i++;
        while(i < lines.size() && !lines.get(i).isEmpty()) {
            int finalI = i;
            IntStream.range(0, lines.get(i).length()).forEachOrdered(j -> moves.add(lines.get(finalI).charAt(j)));
            i++;
        }

        solvep1();
        System.out.println(getScore());
    }

    void solvep1(){
        var robotCell = grid.map.values().stream().filter(c -> c.value == Tile.ROBOT).findFirst().get();
        for(Character dirc : moves){
            Dir dir = switch(dirc){
                case '^' -> Dir.T;
                case 'v' -> Dir.B;
                case '<' -> Dir.L;
                default ->  Dir.R;
            };
            var neig = robotCell.getDir(dir);

            if(neig.value == Tile.WALL){
                continue;
            }
            if(neig.value == Tile.EMPTY){
                grid.switchCellValues(robotCell, neig);
                robotCell = neig;
                continue;
            }

            var farthestBox = neig;
            var limit = farthestBox.getDir(dir);
            while(limit.value == Tile.BOX){
                farthestBox = limit;
                limit = farthestBox.getDir(dir);
            }
            if(limit.value == Tile.WALL){
                continue;
            }
            limit.value = Tile.BOX;
            neig.value = Tile.ROBOT;
            robotCell.value = Tile.EMPTY;

            robotCell = neig;
        }
    }

    int getScore(){
        return grid.map.values().stream()
                .filter(c -> c.value == Tile.BOX)
                .mapToInt(c -> c.pos.x + 100*c.pos.y)
                .sum();
    }

}

enum Tile{
    EMPTY, WALL, ROBOT, BOX;

    static Function<String, Tile> getConverter(){
        return s -> switch (s) {
            case "." -> Tile.EMPTY;
            case "#" -> Tile.WALL;
            case "@" -> Tile.ROBOT;
            default -> Tile.BOX;
        };
    };
}

public class Day15 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_15\\input.txt"));
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