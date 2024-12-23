package utils;

import java.awt.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Grid<T> {
    public HashMap<Point, Cell> map;
    public int height, width;

    public Grid() {
        map = new HashMap<>();
    }

    public Grid(T defaultValue, int width, int height) {
        this(new ArrayList<>(Collections.nCopies(height, new ArrayList<>(Collections.nCopies(width, defaultValue)))));
    }

    public Grid(ArrayList<String> lines, String separator, Function<String, T> converter) {
        ArrayList<ArrayList<T>> convertedLines = new ArrayList<>();
        for (var line : lines) {
            convertedLines.add(Arrays.stream(line.split(separator))
                    .map(converter).collect(Collectors.toCollection(ArrayList::new)));
        }
        this(convertedLines);
    }

    public Grid(ArrayList<String> lines, String separator, Class<T> clazz) {
        var converter = new TypeConverter<T>().getConverter(clazz);
        this(lines, separator, converter);
    }

    // ----------

    public Grid(T[][] array2d) {
        map = new HashMap<>();
        height = array2d.length;
        width = array2d[0].length;
        for (int i = 0; i < array2d.length; i++) {
            for (int j = 0; j < array2d[i].length; j++) {
                var pos = new Point(j, i);
                map.put(pos, new Cell(array2d[i][j], pos));
            }
        }
    }


    public Grid(ArrayList<T[]> listOfLinesArray, boolean a) {
        map = new HashMap<>();
        height = listOfLinesArray.size();
        width = listOfLinesArray.get(0).length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                var pos = new Point(j, i);
                map.put(pos, new Cell(listOfLinesArray.get(i)[j], pos));
            }
        }
    }

    public Grid(ArrayList<ArrayList<T>> listOfLines) {
        map = new HashMap<>();
        height = listOfLines.size();
        width = listOfLines.getFirst().size();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                var pos = new Point(j, i);
                map.put(pos, new Cell(listOfLines.get(i).get(j), pos));
            }
        }
    }

    // ----------

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                s.append(get(j, i));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void switchCellValues(Cell a, Cell b){
        var tmp = a.val;
        a.val = b.val;
        b.val = tmp;
    }

    public Cell get(int x, int y){
        return map.get(new Point(x, y));
    }

    public T getVal(int x, int y){
        return get(x, y).val;
    }

    public Cell getFirstCell(Predicate<? super Cell> p){
        var res = map.values().stream().filter(p).findFirst();
        if(res.isEmpty()){
            throw new NoSuchElementException();
        }
        return res.get();
    }

    public ArrayList<Cell> getCells(Predicate<? super Cell> p){
        return map.values().stream().filter(p).collect(Collectors.toCollection(ArrayList::new));
    }


    public class Cell {
        public T val;
        public Point pos;

        public Cell(T value, Point pos) {
            this.val = value;
            this.pos = pos;
        }

        public ArrayList<Cell> getNeighbors4() {
            ArrayList<Cell> neighbors = new ArrayList<>();
            Point tempPoint = new Point();
            Cell neighbor;

            int[] xs = {pos.x - 1, pos.x, pos.x + 1, pos.x};
            int[] xy = {pos.y, pos.y - 1, pos.y, pos.y + 1};

            for (int i = 0; i < 4; i++) {
                tempPoint.setLocation(xs[i], xy[i]);
                if ((neighbor = map.get(tempPoint)) != null) {
                    neighbors.add(neighbor);
                }
            }
            return neighbors;
        }

        public ArrayList<Cell> getNeighbors8() {
            ArrayList<Cell> neighbors = new ArrayList<>();
            Point tempPoint = new Point();
            Cell neighbor;

            int[] xs = {pos.x - 1, pos.x - 1, pos.x, pos.x + 1, pos.x + 1, pos.x + 1, pos.x, pos.x - 1};
            int[] xy = {pos.y, pos.y - 1, pos.y - 1, pos.y - 1, pos.y, pos.y + 1, pos.y + 1, pos.y + 1};

            for (int i = 0; i < 8; i++) {
                tempPoint.setLocation(xs[i], xy[i]);
                if ((neighbor = map.get(tempPoint)) != null) {
                    neighbors.add(neighbor);
                }
            }
            return neighbors;
        }

        public Cell getDir(Dir dir) {
            Point newPos = switch (dir) {
                case L -> new Point(pos.x - 1, pos.y);
                case R -> new Point(pos.x + 1, pos.y);
                case T -> new Point(pos.x, pos.y - 1);
                case B -> new Point(pos.x, pos.y + 1);
                case TL -> new Point(pos.x - 1, pos.y - 1);
                case TR -> new Point(pos.x + 1, pos.y - 1);
                case BL -> new Point(pos.x - 1, pos.y + 1);
                case BR -> new Point(pos.x + 1, pos.y + 1);
            };

            return map.get(newPos);
        }

        public int x(){
            return pos.x;
        }

        public int y(){
            return pos.y;
        }

        @Override
        public String toString() {
            return val.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return Objects.equals(pos, cell.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(pos);
        }
    }
}

class TypeConverter<T> {
    private static final Map<Class<?>, Function<String, ?>> CONVERTERS = new HashMap<>();

    static {
        CONVERTERS.put(Integer.class, str -> Integer.valueOf(str.trim()));
        CONVERTERS.put(Double.class, str -> Double.valueOf(str.trim()));
        CONVERTERS.put(Long.class, str -> Long.valueOf(str.trim()));
        CONVERTERS.put(Boolean.class, str -> Boolean.valueOf(str.trim()));
        CONVERTERS.put(String.class, str -> str);
        CONVERTERS.put(Character.class, str -> str.trim().charAt(0));
    }

    @SuppressWarnings("unchecked")
    public Function<String, T> getConverter(Class<T> clazz) {
        Function<String, ?> converter = CONVERTERS.get(clazz);
        if (converter == null) {
            throw new IllegalArgumentException("Type non supporté: " + clazz.getSimpleName());
        }
        return (Function<String, T>) converter;
    }
}