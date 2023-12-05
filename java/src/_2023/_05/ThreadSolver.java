package _2023._05;

import java.util.ArrayList;

public class ThreadSolver implements Runnable  {

    private final ArrayList<ArrayList<Converter>> maps;
    private final long start;
    private final long len;
    public long lowestLocation = Long.MAX_VALUE;

    ThreadSolver(ArrayList<ArrayList<Converter>> maps, long start, long len) {
        this.maps = maps;
        this.start = start;
        this.len = len;
    }

    @Override
    public void run() {

        for (long j = start; j < start + len; j++) {
            long location = j;
            for (ArrayList<Converter> map : maps) {
                for (Converter converter : map) {
                    if (converter.isInRange(location)) {
                        location = converter.convert(location);
                        break;
                    }
                }
            }
            if (location < lowestLocation) {
                lowestLocation = location;
            }
        }

    }
}
