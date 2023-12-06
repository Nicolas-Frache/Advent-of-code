package _2023._06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6Part1 {
    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_06\\input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;

        var times = Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(x -> !x.isEmpty()).map(Integer::parseInt).toList();

        var distances = Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(x -> !x.isEmpty()).map(Integer::parseInt).toList();

        int score = 1;
        for (int i = 0; i < times.size(); i++) {
            int b = times.get(i);
            int c = -distances.get(i);
            int a = -1;

            int det = b * b - 4 * a * c;
            double x1 = (-b + Math.sqrt(det)) / (2 * a);
            double x2 = (-b - Math.sqrt(det)) / (2 * a);

            // Nécessaire pour les racines exacte
            x1 = (x1 % 1 == 0) ? x1 + 1 : x1;
            x2 = (x2 % 1 == 0) ? x2 - 1 : x2;

            score *= (int) (Math.floor(x2) - Math.ceil(x1) + 1);
        }
        System.out.println(score);

    }
}
