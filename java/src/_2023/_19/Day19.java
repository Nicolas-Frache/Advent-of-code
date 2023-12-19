package _2023._19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solver {
    ArrayList<Part> parts = new ArrayList<>();
    static HashMap<String, Workflow> workflows = new HashMap<>();
    static final int MAX_VALUE = 4000;

    void solve(ArrayList<String> lines) {
        // PARSING 1
        while (!lines.get(0).isEmpty()) {
            Workflow workflow = new Workflow();
            String label = lines.get(0).split("\\{")[0];
            String[] conds = lines.get(0).split("\\{")[1].split("\\}")[0].split(",");
            for (int i = 0; i < conds.length - 1; i++) {
                workflow.conditions.add(Condition.parse(conds[i]));
            }
            workflow.goToFinally = conds[conds.length - 1];
            workflows.put(label, workflow);
            lines.remove(0);
        }
        lines.remove(0);
        // PARSING 2
        while (!lines.isEmpty()) {
            Matcher matcher = Pattern.compile("\\{x=(.+),m=(.+),a=(.+),s=(.+)}").matcher(lines.get(0));
            if (matcher.find()) {
                HashMap<Cat, Integer> map = new HashMap<>();
                map.put(Cat.X, Integer.parseInt(matcher.group(1)));
                map.put(Cat.M, Integer.parseInt(matcher.group(2)));
                map.put(Cat.A, Integer.parseInt(matcher.group(3)));
                map.put(Cat.S, Integer.parseInt(matcher.group(4)));
                parts.add(new Part(map));
                lines.remove(0);
            }
        }
        workflows.put("A", Workflow.ACCEPT);
        workflows.put("R", Workflow.REJECT);

        int total = 0;
        Workflow start = workflows.get("in");
        for (var part : parts) {
            if (start.resolve(part)) {
                total += part.getTotal();
            }
        }
        System.out.println("Part1: " + total);
        System.out.println("Part2: " + start.getValidatedRanges(ValidRanges.getDefault()));
    }
}

enum Cat {X, M, A, S}

record Condition(boolean isInferior, int value, Cat category, String goToIfTrue) {
    boolean check(int toCompare) {
        if (isInferior) {
            return toCompare < value;
        } else {
            return toCompare > value;
        }
    }

    static Condition parse(String s) {
        boolean isInf = s.contains("<");
        Matcher matcher = Pattern.compile("(.+)[><](.+):(.+)").matcher(s);
        Cat cat = null;
        matcher.find();
        switch (matcher.group(1)) {
            case "x" -> cat = Cat.X;
            case "m" -> cat = Cat.M;
            case "a" -> cat = Cat.A;
            case "s" -> cat = Cat.S;
        }
        return new Condition(isInf, Integer.parseInt(matcher.group(2)), cat, matcher.group(3));
    }

    ConditionOuput processRanges(List<Range> input) {
        var accepted = new ArrayList<Range>();
        var rejected = new ArrayList<Range>();
        for (Range r : input) {
            if ((value < r.start() && !isInferior)
                    || (value > r.end() && isInferior)) {
                accepted.add(r.getCopy());
            } else {
                if (isInferior && r.isIn(value)) {
                    accepted.add(new Range(r.start(), value - 1));
                } else if (!isInferior && r.isIn(value)) {
                    accepted.add(new Range(value + 1, r.end()));
                }
            }
        }
        // On déduit les rejected de input
        boolean inRange = false;
        int start = 1;
        for (int i = 1; i <= Solver.MAX_VALUE; i++) {
            int finalI = i;
            if (input.stream().anyMatch(a -> a.isIn(finalI)) && accepted.stream().noneMatch(a -> a.isIn(finalI))) {
                if (!inRange) {
                    start = i;
                    inRange = true;
                }
            } else {
                if (inRange) {
                    inRange = false;
                    rejected.add(new Range(start, i - 1));
                }
            }
        }
        if (inRange) {
            rejected.add(new Range(start, Solver.MAX_VALUE));
        }

        return new ConditionOuput(accepted, rejected);
    }
}

record ConditionOuput(List<Range> accepted, List<Range> rejected) { }

record Range(int start, int end) {
    static Range defaultRange = new Range(1, Solver.MAX_VALUE);

    int count() {
        return end - start + 1;
    }

    boolean isIn(int val) {
        return val >= start && val <= end;
    }

    public Range getCopy() {
        return new Range(start, end);
    }
}

class ValidRanges {
    public HashMap<Cat, List<Range>> ranges;

    ValidRanges(HashMap<Cat, List<Range>> ranges) {
        this.ranges = ranges;
    }

    static ValidRanges getDefault() {
        ValidRanges output = getEmpty();
        Arrays.stream(Cat.values()).forEach(a -> {
            output.ranges.get(a).add(Range.defaultRange);
        });
        return output;
    }

    public ValidRanges getCopy() {
        HashMap<Cat, List<Range>> copy = new HashMap<>();
        Arrays.stream(Cat.values()).forEach(a -> {
            copy.put(a, new ArrayList<>());
            this.ranges.get(a).forEach(b -> copy.get(a).add(b.getCopy()));
        });
        return new ValidRanges(copy);
    }

    static ValidRanges getEmpty() {
        HashMap<Cat, List<Range>> map = new HashMap<>();
        Arrays.stream(Cat.values()).forEach(a -> {
            map.put(a, new ArrayList<>());
        });
        return new ValidRanges(map);
    }

    long count() {
        long total = 1;
        for (Cat cat : Cat.values()) {
            long totalInCat = 0;
            for (var range : ranges.get(cat)) {
                totalInCat += range.count();
            }
            total *= totalInCat;
        }
        return (total != 1) ? total : 0;
    }
}

class Workflow {
    ArrayList<Condition> conditions = new ArrayList<>();
    String goToFinally;
    static final Workflow ACCEPT = new Workflow(), REJECT = new Workflow();

    long getValidatedRanges(ValidRanges input) {
        if (this == ACCEPT) return input.count();
        if (this == REJECT) return 0;

        long sumAccepted = 0;
        ValidRanges condInput = input.getCopy();
        for (var cond : conditions) {
            ConditionOuput condResult = cond.processRanges(condInput.ranges.get(cond.category()));

            ValidRanges accepted = condInput.getCopy();
            accepted.ranges.put(cond.category(), condResult.accepted());
            sumAccepted += Solver.workflows.get(cond.goToIfTrue()).getValidatedRanges(accepted);

            condInput.ranges.put(cond.category(), condResult.rejected());
        }
        return sumAccepted + Solver.workflows.get(this.goToFinally).getValidatedRanges(condInput);
    }

    boolean resolve(Part part) {
        if (this == ACCEPT) return true;
        if (this == REJECT) return false;

        for (var cond : conditions) {
            if (cond.check(part.values().get(cond.category()))) {
                return Solver.workflows.get(cond.goToIfTrue()).resolve(part);
            }
        }
        return Solver.workflows.get(goToFinally).resolve(part);
    }
}

record Part(HashMap<Cat, Integer> values) {
    int getTotal() {
        return values().values().stream().mapToInt(Integer::valueOf).sum();
    }
}


public class Day19 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_19\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        reader.lines().forEach(lines::add);

        Solver solver = new Solver();
        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}