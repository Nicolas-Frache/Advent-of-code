package _2024._21;

import utils.Dir;
import utils.Grid;
import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

class Solver implements ISolver {
    public void solve(ArrayList<String> lines) {
        Controller keypad, remote1, remote2;

        ArrayList<String> tmp = new ArrayList<>(List.of(new String[]{"789", "456", "123", ".0A"}));
        keypad = new Controller(new Grid<>(tmp, "", Character.class));

        tmp = new ArrayList<>(List.of(new String[]{".^A", "<v>"}));
        remote1 = new Controller(new Grid<>(tmp, "", Character.class));
        remote2 = new Controller(new Grid<>(tmp, "", Character.class));

        ArrayList<Character> l1 = new ArrayList<>(), l2 = new ArrayList<>(), l3 = new ArrayList<>(), l4 = new ArrayList<>();

        int score = 0;
        for (String line : lines) {

            var code = Arrays.stream(line.split("")).map(s -> s.toCharArray()[0]).toList();
            for (Character d1 : code) {
                l1.add(d1);
                for (Character d1i : keypad.goToAndPress(d1)) {
                    l2.add(d1i);
                    for (Character d2i : remote1.goToAndPress(d1i)) {
                        l3.add(d2i);
                        var d3 = remote2.goToAndPress(d2i);
                        l4.addAll(d3);
                    }
                }
            }
            System.out.println();
            l1.forEach(System.out::print);
            System.out.println();
            l2.forEach(System.out::print);
            System.out.println();
            l3.forEach(System.out::print);
            System.out.println();
            l4.forEach(System.out::print);
            System.out.println();

            var matcher = Pattern.compile("\\d").matcher(line);
            var digitPart = Integer.parseInt(matcher.results().map(MatchResult::group).reduce("", String::concat));

            System.out.println(digitPart + " " + l4.size());
            score += l4.size() * digitPart;
            l4.clear();
            l3.clear();
            l2.clear();
            l1.clear();
        }
        System.out.println(score);
    }
}

class Controller {
    Grid<Character> grid;
    Grid<Character>.Cell currentPointer;

    public Controller(Grid<Character> grid) {
        this.grid = grid;
        reserPointer();
    }

    public void reserPointer() {
        currentPointer = grid.getFirstCell(c -> c.val == 'A');
    }

    public ArrayList<Character> goToAndPress(Character toPress) {
        ArrayList<Character> actions = new ArrayList<>();
        Grid<Character>.Cell cellToPress = grid.getFirstCell(c -> c.val == toPress);

        while (currentPointer != cellToPress) {
            if(currentPointer.val == '.'){
                throw new RuntimeException("Illegal character");
            }

            if (currentPointer.y() > cellToPress.y() && currentPointer.getDir(Dir.T).val != '.') {
                currentPointer = currentPointer.getDir(Dir.T);
                actions.add('^');
            } else if (currentPointer.y() < cellToPress.y()  && currentPointer.getDir(Dir.B).val != '.') {
                currentPointer = currentPointer.getDir(Dir.B);
                actions.add('v');
            } else if (currentPointer.x() > cellToPress.x() && currentPointer.getDir(Dir.L).val != '.') {
                currentPointer = currentPointer.getDir(Dir.L);
                actions.add('<');
            } else if (currentPointer.x() < cellToPress.x()  && currentPointer.getDir(Dir.R).val != '.') {
                currentPointer = currentPointer.getDir(Dir.R);
                actions.add('>');
            }
        }
        actions.add('A');
        return actions;
    }
}

public class Day21 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 21, new Solver());
    }
}