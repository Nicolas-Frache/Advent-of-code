package _2023._07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day7Part2 {
    public static void main(String[] args) throws IOException {
        Day7Part2 main = new Day7Part2();
        main.solve();
    }

    public void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_07\\input.txt"));
        String ligne;
        var hands = new ArrayList<Hand>();


        while ((ligne = reader.readLine()) != null) {
            Hand hand = new Hand(Integer.parseInt(ligne.split(" ")[1]));
            String cards = ligne.split(" ")[0];
            for (char card : cards.toCharArray()) {
                hand.addCard(card);
            }
            hands.add(hand);
        }
        int score = 0;
        List<Hand> sorted = hands.stream().sorted().toList();
        for (int i = 0; i < sorted.size(); i++) {
            score += sorted.get(i).bet * (i + 1);
        }
        System.out.println(score);
    }


}