package _2024._22;

import utils.ISolver;
import utils.PuzzleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class Solver implements ISolver {
    byte[][] difs, prices;
    int NB_PRICES = 2000;

    public void solve(ArrayList<String> lines) {
        long sumP1 = 0;

        difs = new byte[lines.size()][NB_PRICES];
        prices = new byte[lines.size()][NB_PRICES];
        int l = 0;

        for (String line : lines) {
            long n = Long.parseLong(line);
            short previousDigit = 0;
            for (int i = 1; i < NB_PRICES; i++) {
                n = evolve(n);
                var digit = (byte) (n % 10);
                difs[l][i] = (byte) (digit - previousDigit);
                prices[l][i] = digit;
                previousDigit = digit;
            }
            sumP1 += n;
            l++;
        }
        System.out.println(sumP1);

        int maxBan = 0;

        short[] seq = new short[4];
        short B = 3, A = (short) (B * -1);

        for (short i = A; i <= B; i++) {
            seq[0] = i;
            for (short j = A; j <= B; j++) {
                seq[1] = j;
                for (short k = A; k <= B; k++) {
                    seq[2] = k;
                    for (short ll = A; ll <= B; ll++) {
                        seq[3] = ll;
                        AtomicInteger sumBan = new AtomicInteger();
                        IntStream.range(0, lines.size()).parallel().forEach(nb -> sumBan.addAndGet(getBananas(nb, seq)));
                        maxBan = Math.max(maxBan, sumBan.get());
                    }
                }
            }
        }
        System.out.println(maxBan);
    }

    int getBananas(int l, short[] seq) {
        for (int i = 0; i < NB_PRICES - 4; i++) {
            if (difs[l][i] == seq[0]
                    && difs[l][i + 1] == seq[1]
                    && difs[l][i + 2] == seq[2]
                    && difs[l][i + 3] == seq[3]) {
                return prices[l][i + 3];
            }
        }
        return 0;
    }

    long mix(long n, long secret) {
        return n ^ secret;
    }

    long prune(long secret) {
        return secret % 16777216L;
    }

    long evolve(long secret) {
        secret = prune(mix(secret, secret * 64));
        secret = prune(mix(Math.floorDiv(secret, 32), secret));
        return prune(mix(secret * 2048L, secret));
    }
}


public class Day22 {
    public static void main() throws IOException {
        PuzzleRunner.Launch(2024, 22, new Solver());
    }
}