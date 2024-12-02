package _2024._02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Solver {
    void solve(ArrayList<String> lines) {
        int sum = 0;
        for(String line : lines) {
            var report = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            boolean isCorrect = true;
            //boolean isIncreasing = report[1] >= report[0] || report[2] > report[1];
            //boolean knowDirection = false;

            int nbIncreasing = 0;
            for(int i=1; i<=4; i++){
                if(report[i] > report[i-1]){
                    nbIncreasing++;
                }
            }
            boolean isIncreasing = nbIncreasing >= 2;

            //if(report[0] != report[1]){
            //    isIncreasing = report[1] > report[0];
            //} else {
            //    isIncreasing = report[2] > report[1];
            //}

            boolean jokerUsed = false;
            for(int i = 1; i<report.length; i++){
                //if(!knowDirection && report[i-1] != report[i]){
                //    knowDirection = true;
                //    isIncreasing = report[i] > report[i-1];
                //}

                if ( //!knowDirection
                        Math.abs(report[i] - report[i - 1]) > 3
                        || isIncreasing && report[i] <= report[i - 1]
                        || !isIncreasing && report[i] >= report[i - 1]
                ) {
                    if(jokerUsed){
                        isCorrect = false;
                        break;
                    }
                    jokerUsed = true;
                    if(Math.abs(report[i] - report[i - 1]) <= 3){
                        report[i] = report[i - 1];
                    } else {
                        if(!isIncreasing){
                            report[i] = report[i - 1];
                        }
                    }

                }
            }
            if(isCorrect){
                sum++;
            }
            System.out.println(isCorrect ? "YES" : "NO");
        }
        System.out.println(sum);
    }
}


public class Day02 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_02\\input.txt"));
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