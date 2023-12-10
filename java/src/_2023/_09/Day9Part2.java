package _2023._09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Day9Part2 {
    public static void main(String[] args) throws IOException {
        var main = new Day9Part2();
        main.solve();
    }

    public void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_09\\input.txt"));
        String ligne = "";
        int result = 0;
        while ((ligne = reader.readLine()) != null) {
            ArrayList<ArrayList<Integer>> sequences = Day9Part1.getSequences(ligne);

            sequences.get(sequences.size() - 1).add(0, 0);
            IntStream.range(0, sequences.size() - 1).boxed()
                    .sorted(Comparator.reverseOrder())
                    .forEach(seqI -> sequences.get(seqI).add(0,
                            sequences.get(seqI).get(0) - sequences.get(seqI + 1).get(0)));
            result += sequences.get(0).get(0);
        }
        System.out.println(result);
    }
}
