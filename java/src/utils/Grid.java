package utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Grid<T> {
    public HashMap<Point, Cell> map;

    public Grid(){
        map = new HashMap<>();
    }
    public Grid(T[][] array2d) {
        map = new HashMap<>();
        for (int i = 0; i < array2d.length; i++) {
            for (int j = 0; j < array2d[i].length; j++) {
                var pos = new Point(j, i);
                map.put(pos, new Cell(array2d[i][j], pos));
            }
        }
    }

    public Grid(ArrayList<String> lines, String separator, Function<String, T> converter) {
        ArrayList<ArrayList<T>> convertedLines = new ArrayList<>();
        for(var line : lines) {
            convertedLines.add(Arrays.stream(line.split(separator))
                    .map(converter).collect(Collectors.toCollection(ArrayList::new)));
        }
        this(convertedLines);
    }

    public Grid(ArrayList<String> lines, String separator, Class<T> clazz) {
        var converter = new TypeConverter<T>().getConverter(clazz);
        this(lines, separator, converter);
    }

    public Grid(ArrayList<T[]> listOfArrays, boolean a) {
        map = new HashMap<>();
        for (int i = 0; i < listOfArrays.size(); i++) {
            for (int j = 0; j < listOfArrays.get(i).length; j++) {
                var pos = new Point(j, i);
                map.put(pos, new Cell(listOfArrays.get(i)[j], pos));
            }
        }
    }

    public Grid(ArrayList<ArrayList<T>> listOfArrays) {
        map = new HashMap<>();
        for (int i = 0; i < listOfArrays.size(); i++) {
            for (int j = 0; j < listOfArrays.get(i).size(); j++) {
                var pos = new Point(j, i);
                map.put(pos, new Cell(listOfArrays.get(i).get(j), pos));
            }
        }
    }

    @Override
    public String toString() {
        return "TODO";
    }

    public class Cell {
        public T value;
        public Point pos;

        public Cell(T value, Point pos) {
            this.value = value;
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

        public Cell getDir(Dir dir){
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

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public enum Dir {
        L, R, T, B, TL, TR, BL, BR
    }

    @SuppressWarnings("unchecked")
    private T convertToType(String value, Class<T> clazz) {
        return switch (clazz.getSimpleName()) {
            case "Integer" -> (T) Integer.valueOf(value.trim());
            case "Double" -> (T) Double.valueOf(value.trim());
            case "Long" -> (T) Long.valueOf(value.trim());
            case "Boolean" -> (T) Boolean.valueOf(value.trim());
            case "String" -> (T) value;
            case "Character" -> (T) Character.valueOf(value.trim().charAt(0));
            default -> throw new IllegalArgumentException(
                    "Type non supporté: " + clazz.getSimpleName()
            );
        };
    }
}

class TypeConverter<T>{
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









