package _2023._04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day4Part1 {
    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_04\\input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;
        int totalScore = 0;
        List<String> winningsNumbers;

        while ((ligne = reader.readLine()) != null) {
            winningsNumbers = Arrays.stream(ligne.split("\\|")[0].split(":")[1]
                            .trim().split(" ")).toList();

            List<String> wonNumbers = Arrays.stream(ligne.split("\\|")[1].trim().split(" "))
                    .toList().stream()
                    .filter(x -> !x.isEmpty())
                    .filter(winningsNumbers::contains).toList();
            totalScore += (wonNumbers.size() == 0) ? 0 : (int) Math.pow(2, wonNumbers.size() - 1);
        }
        System.out.println(totalScore);
    }
}
