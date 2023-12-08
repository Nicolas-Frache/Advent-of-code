package _2023._08;

import utils.Maths;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day08Part2 {
    public static void main(String[] args) throws IOException {
        Day08Part2 main = new Day08Part2();
        main.solve();
    }

    public void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_08\\input.txt"));
        String ligne = "";

        String sequence = reader.readLine().trim();
        reader.readLine();

        ArrayList<Node> nodes = new ArrayList<>();

        while ((ligne = reader.readLine()) != null) {
            String baseID = ligne.substring(0, 3);
            String leftID = ligne.substring(7, 10);
            String rightID = ligne.substring(12, 15);
            nodes.add(new Node(baseID, leftID, rightID));
        }

        ArrayList<Node> GhostStartingNodes = new ArrayList<>(nodes.stream().filter(n -> n.id.endsWith("A")).toList());
        long[] endPoints = new long[GhostStartingNodes.size()];

        int indexGhost = 0;
        for (Node ghostNode : GhostStartingNodes) {
            int nbSteps = 0;
            while (nbSteps != -1) {
                for (String mov : sequence.split("")) {
                    nbSteps++;
                    Node finalCurrentNode = ghostNode;
                    if (mov.equals("L")) {
                        ghostNode = nodes.stream().filter(n -> n.id.equals(finalCurrentNode.left)).findAny().get();
                    } else if (mov.equals("R")) {
                        ghostNode = nodes.stream().filter(n -> n.id.equals(finalCurrentNode.right)).findAny().get();
                    }

                    if (ghostNode.id.endsWith("Z")) {
                        endPoints[indexGhost] = (long) nbSteps;
                        nbSteps = -1;
                        indexGhost++;
                    }
                }
            }
        }
        System.out.println(((long) Maths.lcm(endPoints)));
    }

    public class Node {
        public String left;
        public String right;
        public String id;

        public Node(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }
    }

}