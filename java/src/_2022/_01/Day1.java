package _2022._01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day1 {

    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2022\\_01\\input.txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;

        var sums = new ArrayList<Integer>();
        sums.add(0);

        String line = "This order was placed for QT3000! OK?";
        Matcher matcher = Pattern.compile("(.*?)(\\d+)(.*)").matcher(line);

        while ((ligne = reader.readLine()) != null) {
            if (ligne.isEmpty()) {
                sums.add(0);
            } else {
                sums.set(sums.size() - 1, sums.get(sums.size() - 1) + parseInt(ligne));
            }
        }

        var result = sums.stream()
                .sorted(Collections.reverseOrder())
                .toList().subList(0, 3)
                .stream().mapToInt(Integer::intValue).sum();

        System.out.println(result);
        reader.close();
    }
}