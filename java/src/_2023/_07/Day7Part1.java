package _2023._07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day7Part1 {
    public static void main(String[] args) throws IOException {
        Day7Part1 main = new Day7Part1();
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

    public class Hand implements Comparable<Hand> {
        HashMap<Character, Integer> cards = new HashMap<>();
        ArrayList<Character> cardsOrder = new ArrayList<>();
        Integer bet;

        public Hand(int bet) {
            this.bet = bet;
        }

        public void addCard(Character card) {
            cards.put(card, cards.getOrDefault(card, 0) + 1);
            cardsOrder.add(card);
        }

        @Override
        public int compareTo(Hand o) {
            int delta = this.getMaxCardinality() - o.getMaxCardinality();
            if (delta > 0) {
                return 1;
            }
            if (delta < 0) {
                return -1;
            }

            // FULL / DOUBLE PAIR
            delta = this.getSecondMaxCardinality() - o.getSecondMaxCardinality();
            if (delta > 0) {
                return 1;
            }
            if (delta < 0) {
                return -1;
            }

            // HIGH CARD
            for (int i = 0; i < 5; i++) {
                delta = this.cardsOrder.get(i) - o.cardsOrder.get(i);
                if (delta > 0) {
                    return 1;
                }
                if (delta < 0) {
                    return -1;
                }
            }
            return 0;
        }

        public int getMaxCardinality() {
            return cards.keySet().stream().mapToInt(cards::get).max().getAsInt();
        }

        public int getSecondMaxCardinality() {
            return cards.keySet().stream().mapToInt(cards::get).map(i -> -i).sorted().map(i -> -i).skip(1).findFirst().getAsInt();
        }

        public int getCardScore(int index) {
            char c = cardsOrder.get(index);
            if (!Character.isDigit(c)) {
                int r=0;
                switch (c) {
                    case 'A' -> r = 14;
                    case 'K' -> r = 13;
                    case 'Q' -> r = 12;
                    case 'J' -> r = 11;
                    case 'T' -> r = 10;
                }
                return r;
            }
            return Character.getNumericValue(c);
        }
    }
}