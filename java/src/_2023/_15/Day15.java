package _2023._15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class Solver {
    void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_15\\input.txt"));
        String ligne = reader.readLine();

        System.out.println("Partie 1: " + Arrays.stream(ligne.split(","))
                .map(a -> new Lense(a, 0).hashCode()).mapToInt(Integer::intValue).sum());

        HashMap<Integer, LinkedHashMap<String, Lense>> boxes = new HashMap<>();
        Arrays.stream(ligne.split(",")).forEach(input -> {
            char symbol = '=';
            Lense lense;
            if (input.contains("-")) {
                symbol = '-';
                lense = new Lense(input.substring(0, input.length() - 1), 0);
            } else {
                lense = new Lense(input.substring(0, input.length() - 2), Integer.parseInt("" + input.charAt(input.length() - 1)));
            }

            if (!boxes.containsKey(lense.hash)) {
                boxes.put(lense.hash, new LinkedHashMap<>());
            }

            if (symbol == '-') {
                boxes.get(lense.hash).remove(lense.label);
            } else {
                boxes.get(lense.hash).put(lense.label, lense);
            }
        });

        AtomicInteger total = new AtomicInteger();
        boxes.keySet().forEach(keyBox -> {
            AtomicInteger lensIdx = new AtomicInteger();
            boxes.get(keyBox).forEach((keyLens, value) -> {
                total.addAndGet((1 + keyBox) * (lensIdx.getAndIncrement() + 1) * value.focalLength);
            });
        });
        System.out.println("Partie 2: " + total);
    }
}

class Lense {
    String label;
    int hash;
    int focalLength;

    Lense(String label, int focalLength) {
        this.label = label;
        hash = hashCode();
        this.focalLength = focalLength;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (byte aByte : label.getBytes(StandardCharsets.US_ASCII)) {
            hash = (hash + aByte) * 17 % 256;
        }
        return hash;
    }
}

public class Day15 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Solver solver = new Solver();
        solver.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}