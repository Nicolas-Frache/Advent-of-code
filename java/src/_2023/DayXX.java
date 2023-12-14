package _2023;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

class Solver{
    void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_XX\\input.txt"));
        String ligne = "";

        while ((ligne = reader.readLine()) != null) {

        }
    }
}


public class DayXX {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Solver solver = new Solver();
        solver.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}