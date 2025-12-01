package cat.uab.tqs.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards = new ArrayList<>();

    public void addCard(Card c) {
        cards.add(c);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getValue() {
        int total = 0;
        int aces = 0;

        for (Card c : cards) {
            if (c.getRank().equals("A")) {
                aces++;
                total += 11;  // provisionalment 11
            } else {
                total += c.getNumericValue();
            }
        }

        // si ens passem de 21 convertim As de 11 a 1
        while (total > 21 && aces > 0) {
            total -= 10; // canvi 11 a 1
            aces--;
        }

        return total;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }


    public boolean isBust() {
        return getValue() > 21;
    }
}
