package _2023._06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6Part2 {
    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_06\\input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;

        long time = Long.parseLong(Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(x -> !x.isEmpty())
                .reduce("", (x, y) -> x + y));

        long distance = Long.parseLong(Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(x -> !x.isEmpty())
                .reduce("", (x, y) -> x + y));

        System.out.println(time + " " + distance);

        int score = 0;

        long b = time;
        long c = -distance;
        long a = -1;

        long det = b * b - 4 * a * c;
        double x1 = (-b + Math.sqrt(det)) / (2 * a);
        double x2 = (-b - Math.sqrt(det)) / (2 * a);

        score += (int) (Math.floor(x2) - Math.ceil(x1) + 1);
        System.out.println(score);
    }
}
