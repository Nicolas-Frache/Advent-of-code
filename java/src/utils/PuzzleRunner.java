package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PuzzleRunner {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void Launch(String path, ISolver solver) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println(ANSI_GREEN + "<------------------------>" + ANSI_RESET);

        String s;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(ANSI_GREEN + "\n▶ Time elapsed : " + estimatedTime / 1000.0 + " s" + ANSI_RESET);
    }

    public static void Launch(int year, int day, ISolver solver) throws IOException {
        Launch("src\\_" + year + "\\_" + (day > 10 ? day : "" + 0 + day) + "\\input.txt", solver);
    }
}
