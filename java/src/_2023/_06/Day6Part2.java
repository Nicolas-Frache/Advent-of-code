package _2023._06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6Part2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_06\\input.txt"));

        long time = Long.parseLong(Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(x -> !x.isEmpty())
                .reduce("", (x, y) -> x + y));

        long distance = Long.parseLong(Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(x -> !x.isEmpty())
                .reduce("", (x, y) -> x + y));

        long det = time * time - 4 * distance;
        double x1 = (-time + Math.sqrt(det)) / -2;
        double x2 = (-time - Math.sqrt(det)) / -2;

        System.out.println((int) (Math.floor(x2) - Math.ceil(x1) + 1));
    }
}