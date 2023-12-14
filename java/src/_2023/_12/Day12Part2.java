package _2023._12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12Part2 {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Day12Part2 day12 = new Day12Part2();
        day12.solve();

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }


    ArrayList<State> toCompute;
    boolean valueComing;

    // ???.### 1,1,3
    private void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_12\\input.txt"));
        String ligne_r = "";
        long total = 0;
        while ((ligne_r = reader.readLine()) != null) {
            ArrayList<Integer> numbers = Arrays.stream(ligne_r.split(" ")[1].split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
            //String seq = Arrays.stream(ligne_r.split(" ")[0].split("\\.")).filter(s -> !s.isEmpty()).reduce("", (acc, s) -> acc + "." + s);
            String seq = ligne_r.split(" ")[0];

            System.out.println((seq + "?" + seq + "?" + seq + "?" + seq + "?" + seq).toCharArray());
            ArrayList<Integer> numbers5 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                numbers5.addAll(numbers);
            }

            toCompute = new ArrayList<>();
            ArrayList<State> newValues;
            valueComing = seq.contains("#");

            long nbSol = 0;
            nbSol += solveStep(seq.toCharArray(), 0, numbers5, false, false, 1);
            System.out.println(nbSol);
            for (int i = 0; i < 4; i++) {
                if (i == 3) {
                    valueComing = false;
                }

                newValues = toCompute;
                toCompute = new ArrayList<>();

                System.out.print("- nb state: " + newValues.size() + "\n");
                for (var s : newValues) {
                    System.out.println("     - " + s.nbOccurrence + "  | " + s.numbersToProcess.stream().map(a -> "" + a).reduce("", (a, b) -> a + b));
                }
                for (var num : newValues) {
                    nbSol += num.nbOccurrence * solveStep(("?" + seq).toCharArray(), 0, num.numbersToProcess, num.inSeq, num.needSpace, num.nbOccurrence);
                }
                System.out.println("   -> " + nbSol);
            }
            total += nbSol;
        }
        System.out.println("===> " + total);
    }

    public void addState(State newState) {
        State existingState = toCompute.stream().filter(st -> st.equals(newState)).findFirst().orElse(null);
        if (existingState != null) {
            existingState.nbOccurrence += newState.nbOccurrence;
        } else {
            toCompute.add(newState);
        }
    }

    public long solveStep(char[] toProcess, int pos, ArrayList<Integer> toFind, boolean inSeq, boolean needSpace, int factor) {
        if (false) {
            for (int i = pos; i < toProcess.length; i++) {
                System.out.print(toProcess[i]);
            }
            System.out.print("          ->");
            toFind.forEach(System.out::print);
            System.out.println();
        }

        if (pos == toProcess.length) {
            if (!toFind.isEmpty()) {
                addState(new State(new ArrayList<>(toFind), inSeq, needSpace, factor));
                return 0;
            }
            return (valueComing) ? 0 : 1;
        }
        if(toFind.isEmpty()){
            return (valueComing) ? 0 : 1;
        }

        if (needSpace) {
            if (toProcess[pos] == '#') return 0;
            return solveStep(toProcess, pos + 1, toFind, false, false, factor);
        }

        int nbSol = 0;
        if (inSeq) {
            if (toProcess[pos] == '.') return 0;

            if (toFind.get(0) == 1) {
                int old = toFind.remove(0);
                nbSol += solveStep(toProcess, pos + 1, toFind, false, true, factor);
                toFind.add(0, old);
                return nbSol;
            }

            int old = toFind.set(0, toFind.get(0) - 1);
            nbSol += solveStep(toProcess, pos + 1, toFind, true, false, factor);
            toFind.set(0, old);
            return nbSol;
        }

        if (toProcess[pos] == '.') {
            return solveStep(toProcess, pos + 1, toFind, false, false, factor);
        }

        nbSol += solveStep(toProcess, pos, toFind, true, false, factor);

        if (toProcess[pos] == '?') {
            nbSol += solveStep(toProcess, pos + 1, toFind, false, false, factor);
        }
        return nbSol;
    }
}

class State {

    ArrayList<Integer> numbersToProcess;
    boolean inSeq, needSpace;
    int nbOccurrence;

    public State(ArrayList<Integer> numbersToProcess, boolean inSeq, boolean needSpace, int nbOccurrence) {
        this.numbersToProcess = numbersToProcess;
        this.inSeq = inSeq;
        this.needSpace = needSpace;
        this.nbOccurrence = nbOccurrence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;

        if (inSeq != state.inSeq) return false;
        if (needSpace != state.needSpace) return false;
        //return Objects.equals(numbersToProcess, state.numbersToProcess);

        if (numbersToProcess.size() != state.numbersToProcess.size()) return false;
        return IntStream.range(0, numbersToProcess.size()).boxed().allMatch(i -> numbersToProcess.get(i).equals(state.numbersToProcess.get(i)));

        //return numbersToProcess.containsAll(state.numbersToProcess) && state.numbersToProcess.containsAll(numbersToProcess);
    }
}