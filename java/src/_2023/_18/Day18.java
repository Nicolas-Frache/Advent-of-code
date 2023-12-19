package _2023._18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

class Solver {
    void solve() throws IOException {
        int nbLines = (int) new BufferedReader(new FileReader("src\\_2023\\_18\\input.txt")).lines().count();
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_18\\input.txt"));
        String ligne;

        double borderDistanceP1 = 0, borderDistanceP2 = 0;
        double[] xP2 = new double[nbLines], xP1 = new double[nbLines];
        double[] yP2 = new double[nbLines], yP1 = new double[nbLines];

        int xp1 = 0, yp1 = 0, xp2 = 0, yp2 = 0;
        int i = 0;

        while ((ligne = reader.readLine()) != null) {
            char dir = ligne.charAt(0);
            int len = Integer.parseInt(ligne.split(" ")[1]);
            switch (dir) {
                case 'R' -> xp1 += len;
                case 'D' -> yp1 += len;
                case 'L' -> xp1 -= len;
                case 'U' -> yp1 -= len;
            }
            xP1[i] = xp1;
            yP1[i] = yp1;
            borderDistanceP1 += len;

            String hexa = ligne.split("#")[1].split("\\)")[0];
            len = Integer.parseInt(hexa.substring(0, hexa.length() - 1), 16);
            int d = Integer.parseInt(hexa.substring(hexa.length() - 1), 16);
            dir = '_';

            borderDistanceP2 += len;
            switch (d) {
                case 0 -> xp2 += len;
                case 1 -> yp2 += len;
                case 2 -> xp2 -= len;
                case 3 -> yp2 -= len;
            }
            xP2[i] = xp2;
            yP2[i] = yp2;
            i++;
        }

        double interior = shoelaceFormula(xP1, yP1) - (borderDistanceP1 / 2) + 1;
        BigInteger result = BigInteger.valueOf((long) (interior + borderDistanceP1));
        System.out.println("Part 1: " + result);


        interior = shoelaceFormula(xP2, yP2) - (borderDistanceP2 / 2) + 1;
        result = BigInteger.valueOf((long) (interior + borderDistanceP2));
        System.out.println("Part 2: " + result);
    }


    public static double shoelaceFormula(double[] x, double[] y) {
        int n = x.length;
        double sum = 0;

        for (int i = 0; i < n - 1; i++) {
            sum += (x[i] * y[i + 1] - x[i + 1] * y[i]);
        }
        sum += (x[n - 1] * y[0] - x[0] * y[n - 1]);
        return sum / 2.0;
    }
}

public class Day18 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Solver solverP2 = new Solver();
        solverP2.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000 + " s");
    }
}