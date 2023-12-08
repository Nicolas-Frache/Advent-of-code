package _2023._08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day08Part1 {
    public static void main(String[] args) throws IOException {
        Day08Part1 main = new Day08Part1();
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
        Node current = nodes.stream().filter(n -> n.id.equals("AAA")).findAny().get();
        int nbSteps = 0;

        while(!current.id.equals("ZZZ")){
            for(String mov : sequence.split("")){
                nbSteps++;
                Node finalCurrent = current;
                if(mov.equals("L")){
                    current = nodes.stream().filter(n -> n.id.equals(finalCurrent.left)).findAny().get();
                }else if(mov.equals("R")){
                    current = nodes.stream().filter(n -> n.id.equals(finalCurrent.right)).findAny().get();
                }
                if(current.id.equals("ZZZ")){
                    break;
                }
            }
        }
        System.out.println(nbSteps);

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