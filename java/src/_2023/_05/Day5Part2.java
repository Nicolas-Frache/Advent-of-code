package _2023._05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day5Part2 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
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
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<ThreadSolver> threadSolvers = new ArrayList<>();
        for (int i = 0; i < seeds.length; i += 2) {
            var threadSolver = new ThreadSolver(maps, Long.parseLong(seeds[i]), Long.parseLong(seeds[i + 1]));
            Thread thread = new Thread(threadSolver);
            threads.add(thread);
            threadSolvers.add(threadSolver);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
                System.out.println(threadSolvers.get(0).lowestLocation);
                if (threadSolvers.get(0).lowestLocation < lowestLocation) {
                    lowestLocation = threadSolvers.get(0).lowestLocation;
                }
                threadSolvers.remove(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        System.out.println("> " + lowestLocation);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime/1000.0 + " s");
    }
}

