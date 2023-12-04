package _2023._04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4Part2 {
    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_04\\input.txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;
        int totalScore = 0;
        ArrayList<Integer> pileCopies = new ArrayList<>();
        List<String> winningsNumbers;

        // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        while ((ligne = reader.readLine()) != null) {
            if (pileCopies.isEmpty()) {
                pileCopies.add(1);
            } else {
                pileCopies.set(0, pileCopies.get(0) + 1);
            }

            winningsNumbers = Arrays.stream(ligne.split("\\|")[0].split(":")[1]
                    .trim().split(" ")).toList();

            List<String> finalWinningsNumbers = winningsNumbers;

            var s = Arrays.stream(ligne.split("\\|")[1].trim().split(" "))
                    .toList().stream()
                    .filter(x -> !x.isEmpty())
                    .filter(finalWinningsNumbers::contains).toList();

            for (int i = 1; i < s.size()+1; i++) {
                if (pileCopies.size() <= i) {
                    pileCopies.add(0);
                }
                pileCopies.set(i, pileCopies.get(i) + pileCopies.get(0));
            }
            totalScore += pileCopies.get(0);
            pileCopies.remove(0);
        }
        System.out.println(totalScore);
        reader.close();
    }
}
