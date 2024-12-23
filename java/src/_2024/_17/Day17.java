package _2024._17;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

record Instruction(int opCode, int op) {
}

class Solver implements ISolver {
    long A, B, C, instructionPointer;
    ArrayList<Instruction> instructions = new ArrayList<>();
    ArrayList<Long> output = new ArrayList<>();

    public void solve(ArrayList<String> lines) {
        A = Integer.parseInt(lines.get(0).split(": ")[1]);
        B = Integer.parseInt(lines.get(1).split(": ")[1]);
        C = Integer.parseInt(lines.get(2).split(": ")[1]);

        var nbs = Arrays.stream(lines.get(4).split(": ")[1].split(",")).mapToInt(Integer::parseInt).toArray();
        var input = Arrays.stream(nbs).mapToLong(a -> a).boxed().collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < nbs.length; i += 2) {
            instructions.add(new Instruction(nbs[i], nbs[i + 1]));
        }
        System.out.println();

        int nbDigit = 9;
        long i = 0_511_4_26_4632;
        //  4 632
        //        (6|2)
        //          :2 => 114
        //                   (2|7)
        //                    :2 =>          0_114_2_2_4632: 136904921131418
        //                    :7 =>          0_114_7_2_4632: 136904921295258
        //          :6 => 2 511(0|3|4)
        //                      :0 =>       0_511_0_26_4632: 136904920099226 <=== Plus petite
        //                      :3 =>       0_511_3_26_4632: 136904920885658
        //                      :4 =>       0_511_4_26_4632: 136904921147802

        boolean fund = false;
        while (!fund) {
            if (i % 100000000 == 0) {
                System.out.println(i);
            }

            A = i;
            B = 0;
            C = 0;
            instructionPointer = 0;

            while (instructionPointer < instructions.size()) {
                long tmp = instructionPointer;

                try {
                    doInstruction();
                } catch (Exception e) {
                    System.out.println("error: " + i);
                    break;
                }

                if (instructionPointer == tmp) {
                    instructionPointer++;
                }

                if (output.size() > input.size()
                        || (!output.isEmpty() && output.getLast() != input.get(output.size() - 1))) {

                    if(output.size() >= 13){
                        System.out.printf("%o > ", i);
                        output.forEach(l -> System.out.printf("%o|", l));
                        System.out.println();
                    }
                    break;
                } else if (output.size() == input.size() && output.getLast() == output.getLast()) {
                    fund = true;
                    System.out.println("A: " + i);
                    break;
                }
            }
            output.clear();
            i+= (long) Math.pow(8, nbDigit);
        }
    }

    void doInstruction() {
        var i = instructions.get((int) instructionPointer);
        switch (i.opCode()) {
            case 0:
                A = Math.floorDiv(A, (int) Math.pow(2, comboVal(i.op())));
                break;
            case 1:
                B = B ^ i.op();
                break;
            case 2:
                B = comboVal(i.op()) % 8;
                break;
            case 3:
                if (A != 0) {
                    instructionPointer = (i.op() / 2);
                }
                break;
            case 4:
                B = B ^ C;
                break;
            case 5:
                output.add(comboVal(i.op()) % 8);
                break;
            case 6:
                B = Math.floorDiv(A, (int) Math.pow(2, comboVal(i.op())));
                break;
            case 7:
                C = Math.floorDiv(A, (int) Math.pow(2, comboVal(i.op())));
                break;
        }
    }

    long comboVal(int v) {
        if (v <= 3) {
            return v;
        }
        return switch (v) {
            case 4 -> A;
            case 5 -> B;
            case 6 -> C;
            default -> throw new IllegalStateException("");
        };
    }
}


public class Day17 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 17, new Solver());
    }
}