package _2023._13;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

public class Day13 {
    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_13\\input.txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;
        ArrayList<String> lignes = new ArrayList<>(), colonnes = new ArrayList<>();
        int total2 = 0, total1 = 0;
        Day13 main = new Day13();

        while ((ligne = reader.readLine()) != null) {
            if (!ligne.isEmpty()) {
                lignes.add(ligne);
            } else {
                ArrayList<String> finalColonnes = colonnes;
                ArrayList<String> finalLignes = lignes;
                IntStream.range(0, finalLignes.get(0).length()).boxed().forEach(colI -> {
                    finalColonnes.add(finalLignes.stream().reduce("", (a, b) -> a + b.charAt(colI)));
                });

                int oldScore = main.solve(finalLignes, finalColonnes, -1);
                total1 += oldScore;
                int newScore = 0;

                outerloop:
                for (int l = 0; l < finalLignes.size(); l++) {
                    String oldLine = finalLignes.get(l);
                    for (int c = 0; c < finalColonnes.size(); c++) {
                        String oldColumn = finalColonnes.get(c);
                        char newChar = (oldLine.charAt(c) == '.') ? '#' : '.';

                        finalLignes.set(l, StringUtils.replaceNthCharacter(finalLignes.get(l), c, newChar));
                        finalColonnes.set(c, StringUtils.replaceNthCharacter(finalColonnes.get(c), l, newChar));

                        newScore = main.solve(finalLignes, finalColonnes, oldScore);
                        if (newScore != 0 && newScore != oldScore) {
                            break outerloop;
                        }
                        finalLignes.set(l, oldLine);
                        finalColonnes.set(c, oldColumn);

                    }
                }
                total2 += newScore;
                lignes = new ArrayList<>();
                colonnes = new ArrayList<>();
            }
        }
        System.out.println("Part 1: " + total1);
        System.out.println("Part 2: " + total2);
    }

    int solve(ArrayList<String> lignes, ArrayList<String> colonnes, int oldScore) {
        for (int i = 1; i < lignes.size(); i++) {
            var left = new ArrayList<>(lignes.subList(0, i));
            Collections.reverse(left);
            var right = lignes.subList(i, lignes.size());

            boolean valid = true;
            for (int j = 0; j < Math.min(left.size(), right.size()); j++) {
                if (!left.get(j).equals(right.get(j)) || 100 * i == oldScore) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return 100 * i;
            }
        }

        for (int i = 1; i < colonnes.size(); i++) {
            var top = new ArrayList<>(colonnes.subList(0, i));
            Collections.reverse(top);
            var bottom = colonnes.subList(i, colonnes.size());

            boolean valid = true;
            for (int j = 0; j < Math.min(top.size(), bottom.size()); j++) {
                if (!top.get(j).equals(bottom.get(j)) || i == oldScore) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return i;
            }
        }

        return 0;
    }
}