package _2024._14;

import utils.Grid;
import utils.ISolver;
import utils.PuzzleRunner;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solver implements ISolver {
    Grid<ArrayList<Robot>> grid;
    int nblines, nbCols;

    public void solve(ArrayList<String> lines) {
        nblines = 103;
        nbCols = 101;

        ArrayList<ArrayList<ArrayList<Robot>>> linesList = new ArrayList<>();
        for (int i = 0; i < nblines; i++) {
            ArrayList<ArrayList<Robot>> column = new ArrayList<>();
            for (int j = 0; j < nbCols; j++) {
                column.add(new ArrayList<>());
            }
            linesList.add(column);
        }
        grid = new Grid<ArrayList<Robot>>(linesList);


        var pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+).*");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            Point pos = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            grid.map.get(pos).val.add(
                    new Robot(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)), 0));
        }

        int minScore = Integer.MAX_VALUE;
        int minIdx = 0;
        for (int i = 1; i <= nbCols * nblines; i++) {
            moveAllRobots(i);
            if (i == 100) {
                System.out.println("Part1 = " + calculateSafetyScore());
            }
            if(minScore > calculateSafetyScore()){
                minScore = calculateSafetyScore();
                minIdx = i;
            }
        }
        System.out.println("part2 = " + minIdx);
    }

    int calculateSafetyScore() {
        int res = getNumberInSubTab(0, 0, Math.floorDiv(nbCols, 2) - 1, Math.floorDiv(nblines, 2) - 1);
        res *= getNumberInSubTab(0, Math.floorDiv(nblines, 2) + 1, Math.floorDiv(nbCols, 2) - 1, nblines - 1);
        res *= getNumberInSubTab(Math.floorDiv(nbCols, 2) + 1, 0, nbCols - 1, Math.floorDiv(nblines, 2) - 1);
        res *= getNumberInSubTab(Math.floorDiv(nbCols, 2) + 1, Math.floorDiv(nblines, 2) + 1, nbCols - 1, nblines - 1);
        return res;
    }

    void displayPicture() {
        for (int i = 0; i < nblines; i++) {
            System.out.println();
            for (int j = 0; j < nbCols; j++) {
                System.out.print((!grid.map.get(new Point(j, i)).val.isEmpty()) ? "█" : ".");
            }
        }
        System.out.println("========");
    }

    void moveAllRobots(int currentSecond) {
        for (var cell : grid.map.values()) {
            var robots = cell.val;
            for (int i = robots.size() - 1; i >= 0; i--) {
                if (robots.get(i).lastMoved == currentSecond) {
                    continue;
                }
                Robot robot = cell.val.remove(i);
                robot.lastMoved = currentSecond;
                var newPos = new Point((cell.pos.x + robot.dx) % nbCols, (cell.pos.y + robot.dy) % nblines);
                newPos.x = (newPos.x < 0) ? nbCols + newPos.x : newPos.x;
                newPos.y = (newPos.y < 0) ? nblines + newPos.y : newPos.y;
                grid.map.get(newPos).val.add(robot);
            }
        }
    }

    int getNumberInSubTab(int x0, int y0, int x1, int y1) {
        int sum = 0;
        for (int i = x0; i <= x1; i++) {
            for (int j = y0; j <= y1; j++) {
                sum += grid.map.get(new Point(i, j)).val.size();
            }
        }
        return sum;
    }
}

class Robot {
    public int dx, dy, lastMoved;

    public Robot(int dx, int dy, int lastMoved) {
        this.dx = dx;
        this.dy = dy;
        this.lastMoved = lastMoved;
    }
}

public class Day14 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 14, new Solver());
    }
}