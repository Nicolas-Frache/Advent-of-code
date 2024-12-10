package _2024._09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

class Solver {
    void solve(ArrayList<String> lines) {
        var array = Arrays.stream(lines.getFirst().split("")).mapToInt(Integer::parseInt).toArray();
        var len = Arrays.stream(array).sum();

        int[] disk = new int[len];
        int fileNumber = 0;
        int idxDisk = 0;
        boolean isFile = true;
        for (int k : array) {
            for (int j = 0; j < k; j++) {
                disk[idxDisk] = (isFile) ? fileNumber : -1;
                idxDisk++;
            }
            if (isFile) {
                fileNumber++;
            }
            isFile = !isFile;
        }
        System.out.println(solvePart1(disk));
    }

    long solvePart2(int[] input) {
        boolean isFile = true;
        ArrayList<Block> disk = new ArrayList<>();
        int fileNumber = 0;

        for (int val : input) {
            if (isFile) {
                disk.add(new Block(isFile, val, fileNumber));
                fileNumber++;
            } else {
                disk.add(new Block(isFile, val, -1));
            }
            isFile = !isFile;
        }
        ArrayList<Block> initial = new ArrayList<>(disk);
        for (int i = initial.size() - 1; i >= 0; i--) {
            if (!initial.get(i).isFile()) {
                continue;
            }
            int j = 0;
            while(j)

        }


    }

    long solvePart1(int[] disk) {
        int firstSpaceAvailable = 0;
        int lastFile = disk.length - 1;
        while (firstSpaceAvailable < lastFile) {
            while (disk[firstSpaceAvailable] != -1 && firstSpaceAvailable < lastFile) {
                firstSpaceAvailable++;
            }
            while (disk[lastFile] == -1 && lastFile > firstSpaceAvailable) {
                lastFile--;
            }
            if (disk[firstSpaceAvailable] == -1 && disk[lastFile] != -1) {
                disk[firstSpaceAvailable] = disk[lastFile];
                disk[lastFile] = -1;
            }
        }
        int i = 0;
        long sum = 0;
        while (disk[i] != -1) {
            sum += (long) disk[i] * i;
            i++;
        }
        return sum;
    }
}


record Block(boolean isFile, int len, int fileNumber) {
}

public class Day09 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2024\\_09\\input.txt"));
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