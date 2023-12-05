package _2023._05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day5Part1 {
    public static void main(String[] args) throws IOException {
        Day5Part1 solver = new Day5Part1();
        solver.solve();
    }

    public void solve() throws IOException {
        String inputFile = "src\\_2023\\_05\\input.txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;
        ArrayList<ArrayList<Converter>> maps = new ArrayList<>();
        String[] seeds = reader.readLine().split(": ")[1].split(" ");

        while ((ligne = reader.readLine()) != null) {
            if (ligne.isEmpty()) {

            } else if (ligne.contains("map")) {
                maps.add(new ArrayList<>());
            } else {
                String[] split = ligne.split(" ");
                maps.get(maps.size() - 1).add(new Converter(
                        Long.parseLong(split[0]),
                        Long.parseLong(split[1]),
                        Long.parseLong(split[2])
                ));
            }
        }

        Long lowestLocation = Long.MAX_VALUE;
        for (String s : seeds) {
            long location = Long.parseLong(s);
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
        System.out.println(lowestLocation);
    }

    public class Converter {
        public long sourceRangeStart, destinationRangeStart, rangeLenght;

        public Converter(long destinationRangeStart, long sourceRangeStart, long rangeLength) {
            this.sourceRangeStart = sourceRangeStart;
            this.destinationRangeStart = destinationRangeStart;
            this.rangeLenght = rangeLength;
        }

        public boolean isInRange(long sourceValue) {
            return sourceValue >= sourceRangeStart && sourceValue < sourceRangeStart + rangeLenght;
        }

        public long convert(long sourceValue) {
            return destinationRangeStart + (sourceValue - sourceRangeStart);
        }

    }

}
