package _2024._03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Solver {
    void solve(ArrayList<String> lines) {
        int sum = 0;
        var doList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        for (var delimetedByDo : String.join("", lines).split("do\\(\\)")) {
            doList.add(delimetedByDo.split("don't\\(\\)")[0]);
        }
        for (var toAnalyse : doList) {
            Matcher matcher = pattern.matcher(toAnalyse);
            while (matcher.find()) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        System.out.println(sum);
    }
}


public class Day03 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_03\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        Solver solver = new Solver();
        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}