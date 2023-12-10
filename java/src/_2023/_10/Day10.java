package _2023._10;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10 {
    public static void main(String[] args) throws IOException {
        Day10 day10Part1 = new Day10();
        day10Part1.solve();
    }

    public void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_10\\input.txt"));
        int nbligne = (int) reader.lines().count();
        reader = new BufferedReader(new FileReader("src\\_2023\\_10\\input.txt"));
        String ligne = "";

        ligne = reader.readLine();
        Segment[][] segments = new Segment[ligne.length()][nbligne];
        Segment start = null;
        int i = 0;
        while (ligne != null) {
            int j = 0;
            for (String s : ligne.split("")) {
                segments[j][i] = new Segment(j, i, s);
                if (segments[j][i].direction == 'S') {
                    start = segments[j][i];
                }
                j++;
            }
            i++;
            ligne = reader.readLine();
        }
        assert start != null;

        int length = 0;
        Segment current = start;
        Segment previous = null;
        while (current != start || length == 0) {
            Point2D next = current.next(previous, segments);
            previous = current;
            previous.inPath = true;
            current = segments[(int) next.getX()][(int) next.getY()];
            length++;
        }
        for (i = 0; i < segments[0].length; i++) {
            for (int j = 0; j < segments.length; j++) {
                if (segments[j][i].inPath) {
                    segments[j][i].printChar();
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
        System.out.println("Part 1: " + length / 2);
        System.out.println("Now it's time to use Paint ! (dont forget to replace S with the appropriate pipe symbol");
    }

    public static class Segment {
        Point2D pos;
        char direction;
        boolean inPath = false;

        public Segment(int x, int y, String symbol) {
            pos = new Point2D.Double(x, y);
            direction = symbol.charAt(0);
        }

        public void printChar() {
            switch (direction) {
                case 'S':
                    System.out.print("S");
                    return;
                case 'L':
                    System.out.print('└');
                    return;
                case 'J':
                    System.out.print('┘');
                    return;
                case 'F':
                    System.out.print('┌');
                    return;
                case '|':
                    System.out.print('│');
                    return;
                case '7':
                    System.out.print('┐');
                    return;
                case '-':
                    System.out.print('─');
                    return;
                default:
                    System.out.print('X');
            }
        }

        Point2D next(Segment previous, Point2D offsetA, Point2D offsetB) {
            Point2D next = new Point2D.Double(pos.getX() + offsetA.getX(), pos.getY() + offsetA.getY());
            if (previous.pos.equals(next)) {
                return new Point2D.Double(pos.getX() + offsetB.getX(), pos.getY() + offsetB.getY());
            } else {
                return next;
            }
        }

        Point2D next(Segment previous, Segment[][] segments) {
            switch (direction) {
                case '|' -> {
                    return next(previous, new Point2D.Double(0, 1), new Point2D.Double(0, -1));
                }

                case '-' -> {
                    return next(previous, new Point2D.Double(1, 0), new Point2D.Double(-1, 0));
                }
                case 'J' -> {
                    return next(previous, new Point2D.Double(0, -1), new Point2D.Double(-1, 0));
                }
                case 'F' -> {
                    return next(previous, new Point2D.Double(1, 0), new Point2D.Double(0, 1));
                }
                case 'L' -> {
                    return next(previous, new Point2D.Double(1, 0), new Point2D.Double(0, -1));

                }
                case '7' -> {
                    return next(previous, new Point2D.Double(-1, 0), new Point2D.Double(0, 1));
                }
                case 'S' -> {
                    if (pos.getX() != 0) {
                        Segment s = segments[(int) pos.getX() - 1][(int) pos.getY()];
                        if (s.direction == 'L' || s.direction == '-' || s.direction == 'F') {
                            return new Point2D.Double(pos.getX() - 1, pos.getY());
                        }
                    }
                    if (pos.getX() != segments.length - 1) {
                        Segment s = segments[(int) pos.getX() + 1][(int) pos.getY()];
                        if (s.direction == 'J' || s.direction == '-' || s.direction == '7') {
                            return new Point2D.Double(pos.getX() + 1, pos.getY());
                        }
                    }
                    if (pos.getY() != 0) {
                        Segment s = segments[(int) pos.getX()][(int) pos.getY() - 1];
                        if (s.direction == 'F' || s.direction == '|' || s.direction == '7') {
                            return new Point2D.Double(pos.getX(), pos.getY() - 1);
                        }
                    }
                    if (pos.getY() != segments.length - 1) {
                        Segment s = segments[(int) pos.getX()][(int) pos.getY() + 1];
                        if (s.direction == 'J' || s.direction == '|' || s.direction == 'L') {
                            return new Point2D.Double(pos.getX(), pos.getY() + 1);
                        }
                    }
                    assert false;
                    return null;
                }
                default -> {
                    assert false;
                    return null;
                }
            }

        }
    }
}

















