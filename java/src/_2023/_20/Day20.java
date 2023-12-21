package _2023._20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record PulseInput(Module module, Pulse p, String input) {
}

class Solver {
    static HashMap<String, Module> modules = new HashMap<>();
    static ArrayList<PulseInput> pulsesQueue = new ArrayList<>();

    void solve(ArrayList<String> lines) {
        HashMap<String, ArrayList<String>> conjunctionInputs = new HashMap<>();

        // PARSING
        lines.forEach(l -> {
            Matcher matcher = Pattern.compile("([%&]?)(.+) -> (.+)").matcher(l);
            Module m;
            matcher.find();
            switch (matcher.group(1)) {
                case "%" -> m = new FlipFlop();
                case "&" -> m = new Conjunction();
                default -> m = new Broadcaster();
            }
            m.label = matcher.group(2);
            m.outputs.addAll(Arrays.asList(matcher.group(3).split(", ")));
            modules.put(m.label, m);
            Arrays.stream(matcher.group(3).split(", ")).forEach(s -> {
                if (conjunctionInputs.containsKey(s)) {
                    conjunctionInputs.get(s).add(matcher.group(2));
                } else {
                    conjunctionInputs.put(s, new ArrayList<>(Collections.singletonList(matcher.group(2))));
                }
            });
        });
        conjunctionInputs.forEach((key, value) -> {
            if (modules.get(key) instanceof Conjunction c) {
                value.forEach(label -> c.inputs.put(label, LowPulse.instance));
            }
        });

        Broadcaster start = (Broadcaster) modules.get("broadcaster");

        for (int i = 0; i < 1000; i++) {
            pulsesQueue.add(new PulseInput(start, HighPulse.instance, ""));
            while (!pulsesQueue.isEmpty()) {
                PulseInput pulseInput = pulsesQueue.remove(0);
                pulseInput.module().receivePulse(pulseInput.p(), pulseInput.input());
            }
            i++;
        }
        System.out.println("Part 1: " + LowPulse.nbSent * HighPulse.nbSent);
        long[] a = {3851, 3877, 4049, 4021};
        System.out.println("Part 2: " + utils.Maths.lcm(a));
    }
}

abstract class Pulse {
}

class HighPulse extends Pulse {
    static HighPulse instance = new HighPulse();
    static int nbSent = 0;
};

class LowPulse extends Pulse {
    static LowPulse instance = new LowPulse();
    static int nbSent = 0;
};

abstract class Module {
    String label;
    ArrayList<String> outputs = new ArrayList<>();

    abstract Pulse processPulse(Pulse pulse, String input);

    void receivePulse(Pulse pulse, String input) {
        Pulse outputPulse = processPulse(pulse, input);
        if (outputPulse == null) return;

        for (String output : outputs) {
            if (outputPulse instanceof HighPulse) {
                HighPulse.nbSent++;
            } else {
                LowPulse.nbSent++;
            }
            if (Solver.modules.containsKey(output)) {
                Solver.pulsesQueue.add(new PulseInput(Solver.modules.get(output), outputPulse, label));
            }
        }
    }
}

class FlipFlop extends Module {
    boolean state = false;

    Pulse processPulse(Pulse pulse, String input) {
        if (pulse instanceof HighPulse) {
            return null;
        }

        state = !state;
        if (state) {
            return HighPulse.instance;
        }
        return LowPulse.instance;
    }
}

class Broadcaster extends Module {
    Pulse processPulse(Pulse pulse, String input) {
        LowPulse.nbSent++;
        return LowPulse.instance;
    }
}

class Conjunction extends Module {
    public HashMap<String, Pulse> inputs = new HashMap<>();

    Pulse processPulse(Pulse pulse, String input) {
        inputs.put(input, pulse);

        if (inputs.values().stream().allMatch(a -> a instanceof HighPulse)) {
            return LowPulse.instance;
        }
        return HighPulse.instance;
    }
}

public class Day20 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String s;
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_20\\input.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        Solver solver = new Solver();
        solver.solve(lines);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed : " + estimatedTime / 1000.0 + " s");
    }
}