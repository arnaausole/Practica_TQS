package cat.uab.tqs.model;

import java.util.ArrayList;
import java.util.List;

// Classe que representa la mà d'un jugador o del dealer
public class Hand {

    private List<Card> cards = new ArrayList<>();

    private boolean invariant() {
        return cards != null;
    }

    public void addCard(Card c) {

        assert invariant();
        //precond
        assert c != null;

        int oldSize = cards.size();
        cards.add(c);

        // postcond
        assert cards.size() == oldSize + 1;
        assert cards.get(cards.size() - 1) == c;
        assert invariant();

    }

    public List<Card> getCards() {
        assert invariant();
        return cards;
    }

    public int getValue() {

        assert invariant();

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
        assert invariant();
        return total;
    }

    public boolean isBlackjack() {
        assert invariant();
        return cards.size() == 2 && getValue() == 21;
    }


    public boolean isBust() {
        assert invariant();
        return getValue() > 21;
    }
}
