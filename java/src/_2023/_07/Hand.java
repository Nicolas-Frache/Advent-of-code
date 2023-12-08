package _2023._07;

import java.util.ArrayList;
import java.util.HashMap;

public class Hand implements Comparable<Hand>{
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
        return Math.max(cards.keySet().stream().filter(c -> c != 'J').mapToInt(cards::get).max().orElseGet(() -> 0)
                        + cards.getOrDefault('J', 0),
                cards.getOrDefault('J', 0));
    }

    public int getSecondMaxCardinality() {
        return cards.keySet().stream().filter(c -> c != 'J')
                .mapToInt(cards::get).map(i -> -i).sorted().map(i -> -i)
                .skip(1).findFirst().orElseGet(() -> 0);
    }

    public int getCardScore(int index) {
        char c = cardsOrder.get(index);
        if (!Character.isDigit(c)) {
            int r = 0;
            switch (c) {
                case 'A' -> r = 14;
                case 'K' -> r = 13;
                case 'Q' -> r = 12;
                case 'J' -> r = 1;
                case 'T' -> r = 10;
            }
            return r;
        }
        return Character.getNumericValue(c);
    }
}

