package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Solver {
    void solve(ArrayList<String> lines) throws IOException {

    }
}


public class DayXX {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_18\\input.txt"));
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