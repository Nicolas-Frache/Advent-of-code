package _2023._11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day11Part1 {
    public static void main(String[] args) throws IOException {
        Day11Part1 day11Part1 = new Day11Part1();
        day11Part1.solve();
    }
    void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_11\\input.txt"));
        String ligne_r = "";

        ArrayList<ArrayList<Integer>> universe = new ArrayList<>();

        while ((ligne_r = reader.readLine()) != null) {
            universe.add(new ArrayList<>(Arrays.stream(ligne_r.split("")).map(s -> s.equals(".") ? 0 : 1).toList()));
        }
        for (int ligne = universe.size() - 1; ligne >= 0; ligne--) {
            if (universe.get(ligne).stream().noneMatch(val -> val == 1)) {
                universe.add(ligne + 1, new ArrayList<>(Collections.nCopies(universe.get(ligne).size(), 0)));
            }
        }
        for (int col = universe.get(0).size() - 1; col >= 0; col--) {
            int finalCol = col;
            if (universe.stream().noneMatch(ligne -> ligne.get(finalCol) == 1)) {
                universe.forEach(ligne -> ligne.add(finalCol + 1, 0));
            }
        }
        ArrayList<Star> stars = new ArrayList<>();

        IntStream.range(0, universe.size()).boxed().forEach(
                ligne -> IntStream.range(0, universe.get(ligne).size()).boxed().forEach(
                        col -> {
                            if (universe.get(ligne).get(col) == 1) {
                                stars.add(new Star(ligne, col));
                            }
                        }
                )
        );

        AtomicInteger total = new AtomicInteger();
        IntStream.range(0, stars.size()).boxed().forEach(
                i -> IntStream.range(i, stars.size()).boxed().forEach(
                        j -> {
                            Star A  = stars.get(j);
                            Star B  = stars.get(i);
                            total.getAndAdd(Math.abs(A.x - B.x) + Math.abs(A.y - B.y));
                        }
                ));
        System.out.println(total.get());
    }

    static class Star {
        public int x;
        public int y;

        public Star(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Star{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
















