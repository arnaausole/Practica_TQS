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
        for (Card c : cards) {
            total += c.getNumericValue();
        }
        return total;
    }

    public boolean isBust() {
        return getValue() > 21;
    }
}
