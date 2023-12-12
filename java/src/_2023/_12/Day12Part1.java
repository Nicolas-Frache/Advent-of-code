package _2023._12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day12Part1 {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Day12Part1 day12 = new Day12Part1();
        day12.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
    private void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_12\\input.txt"));
        String ligne_r = "";
        int nbSol = 0;
        while ((ligne_r = reader.readLine()) != null) {
            ArrayList<Integer> numbers = Arrays.stream(ligne_r.split(" ")[1].split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
            //char[] sequences = Arrays.stream(ligne_r.split(" ")[0].split("\\.")).filter(s -> !s.isEmpty()).reduce("", (acc, s) -> acc + "." + s).toCharArray();
            char[] sequences = ligne_r.split(" ")[0].toCharArray();


            nbSol += solveStep(sequences, 0, numbers, false, false);
        }
        System.out.println(nbSol);
    }

    public int solveStep(char[] toProcess, int pos, ArrayList<Integer> toFind, boolean inSeq, boolean needSpace) {
        if(false){
            for(int i = pos; i<toProcess.length; i++){
                System.out.print(toProcess[i]);
            }
            System.out.print("          ->");
            toFind.forEach(System.out::print);
            System.out.println();
        }

        if (pos == toProcess.length) {
            if (!toFind.isEmpty()) {
                return 0;
            }
            return 1;
        }
        if (needSpace) {
            if (toProcess[pos] == '#') return 0;
            return solveStep(toProcess, pos + 1, toFind, false, false);
        }

        int nbSol = 0;
        if (inSeq) {
            if (toProcess[pos] == '.' || toFind.isEmpty()) return 0;

            if (toFind.get(0) == 1) {
                int old = toFind.remove(0);
                nbSol += solveStep(toProcess, pos + 1, toFind, false, true);
                toFind.add(0, old);
                return nbSol;
            }

            int old = toFind.set(0, toFind.get(0) - 1);
            nbSol += solveStep(toProcess, pos + 1, toFind, true, false);
            toFind.set(0, old);
            return nbSol;
        }

        if (toProcess[pos] == '.') {
            return solveStep(toProcess, pos + 1, toFind, false, false);
        }

        nbSol += solveStep(toProcess, pos, toFind, true, false);

        if (toProcess[pos] == '?') {
            nbSol += solveStep(toProcess, pos + 1, toFind, false, false);
        }
        return nbSol;
    }


}
