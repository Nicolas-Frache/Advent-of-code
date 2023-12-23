package _2023._22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Solver {
    void solve(ArrayList<String> lines) {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (String line : lines) {
            bricks.add(Brick.parse(line));
        }
        bricks.sort(Brick::compareTo);

        ArrayList<Brick> fallen = Brick.fall(bricks).fallen();

        int safeCount = 0;
        int part2Total = 0;
        for (Brick brick : fallen) {
            ArrayList<Brick> copyFallen = new ArrayList<>();
            for (Brick b : fallen) {
                copyFallen.add(b.copy());
            }
            copyFallen.remove(brick);

            var result = Brick.fall(copyFallen);
            part2Total += result.nbMoved();
            if (result.nbMoved() == 0) {
                safeCount++;
            }
        }

        System.out.println("Part 1: " + safeCount);
        System.out.println("Part 2: " + part2Total);
    }
}

class Brick implements Comparable<Brick> {
    ArrayList<Cube> cubes;

    Brick(ArrayList<Cube> cubes) {
        this.cubes = cubes;
        cubes.sort(Cube::compareTo);
    }

    Brick copy() {
        var result = new ArrayList<Cube>();
        for (Cube cube : cubes) {
            result.add(cube.copy());
        }
        return new Brick(result);
    }

    static Brick parse(String line) {
        String[] tmp = line.split("~")[0].split(",");
        Cube A = new Cube(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));

        tmp = line.split("~")[1].split(",");
        Cube B = new Cube(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
        return new Brick(Cube.interpolate(A, B));
    }

    record FallOutput(ArrayList<Brick> fallen, int nbMoved) { }

    static FallOutput fall(ArrayList<Brick> bricks) {
        ArrayList<Brick> result = new ArrayList<>();
        for (Brick brick : bricks) {
            result.add(brick.copy());
        }
        var allCubes = new ArrayList<Cube>();
        for (Brick brick : bricks) {
            allCubes.addAll(brick.cubes);
        }

        int nbMoved = 0;
        for (Brick brick : result) {
            boolean canFall = true;
            boolean brickMoved = false;

            while (canFall) {
                for (Cube cube : brick.cubes) {
                    if (cube.z == 1 || allCubes.contains(new Cube(cube.x, cube.y, cube.z - 1))) {
                        canFall = false;
                        break;
                    }
                    allCubes.remove(cube);
                }
                if (canFall) {
                    brickMoved = true;
                    for (Cube cube : brick.cubes) {
                        cube.z = cube.z - 1;
                    }
                }
            }
            allCubes.addAll(brick.cubes);

            if (brickMoved) {
                nbMoved++;
            }
        }
        return new FallOutput(result, nbMoved);
    }

    @Override
    public int compareTo(Brick o) {
        return Integer.compare(cubes.get(0).z, o.cubes.get(0).z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brick brick)) return false;

        return cubes.containsAll(brick.cubes) && brick.cubes.containsAll(cubes);
    }
}


class Cube implements Comparable<Cube> {
    int x, y, z;

    Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Cube copy() {
        return new Cube(x, y, z);
    }

    static ArrayList<Cube> interpolate(Cube origine, Cube dest) {
        var result = new ArrayList<Cube>();
        int[] step;
        int delta;
        if ((delta = origine.x - dest.x) != 0) {
            step = new int[]{1, 0, 0};
        } else if ((delta = origine.y - dest.y) != 0) {
            step = new int[]{0, 1, 0};
        } else {
            delta = origine.z - dest.z;
            step = new int[]{0, 0, 1};
        }
        int[] current = new int[]{Math.min(origine.x, dest.x), Math.min(origine.y, dest.y), Math.min(origine.z, dest.z)};

        for (int i = 0; i <= Math.abs(delta); i++) {
            result.add(new Cube(current[0], current[1], current[2]));
            current[0] += step[0];
            current[1] += step[1];
            current[2] += step[2];
        }
        return result;
    }

    @Override
    public int compareTo(Cube o) {
        return Integer.compare(z, o.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cube that)) return false;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }
}

public class Day22 {
    public static void main(String[] args) throws IOException {
        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_22\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }

        Solver solver = new Solver();
        long startTime = System.currentTimeMillis();

        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}