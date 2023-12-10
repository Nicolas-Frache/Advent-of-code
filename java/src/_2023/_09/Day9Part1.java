package _2023._09;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day9Part1 {
    public static void main(String[] args) throws IOException {
        var main = new Day9Part1();
        main.solve();
    }

    public void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_09\\input.txt"));
        String ligne = "";
        int result = 0;

        while ((ligne = reader.readLine()) != null) {
            ArrayList<ArrayList<Integer>> sequences = getSequences(ligne);
            sequences.get(sequences.size() - 1).add(0);

            for (int i = sequences.size() - 2; i >= 0; i--) {
                ArrayList<Integer> current = sequences.get(i);
                int left = current.get(current.size() - 1);
                current.add(left + sequences.get(i + 1).get(sequences.get(i + 1).size() - 1));
            }
            result += sequences.get(0).get(sequences.get(0).size() - 1);
        }
        System.out.println(result);
    }


    public static ArrayList<ArrayList<Integer>> getSequences(String ligne) {
        ArrayList<ArrayList<Integer>> sequences = new ArrayList<>();
        sequences.add(new ArrayList<>(Arrays.stream(ligne.split(" ")).map(Integer::parseInt).toList()));

        int lastSeq = 0;
        while (!sequences.get(lastSeq).stream().allMatch(n -> n == 0)) {
            ArrayList<Integer> newSeq = new ArrayList<>();
            for (int i = 0; i < sequences.get(lastSeq).size() - 1; i++) {
                newSeq.add(sequences.get(lastSeq).get(i + 1) - sequences.get(lastSeq).get(i));
            }
            sequences.add(newSeq);
            lastSeq++;
        }
        return sequences;
    }
}
