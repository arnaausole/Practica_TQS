package cat.uab.tqs.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean contains(Card c) {
        for (Card card : cards) {
            boolean sameRank = card.getRank().equals(c.getRank());
            boolean sameSuit = card.getSuit().equals(c.getSuit());

            if (sameRank && sameSuit) {
                return true;
            }
        }
        return false;
    }


    public Card getCardAt(int index) {
        return cards.get(index);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public void shuffle() {
        java.util.Collections.shuffle(cards);
    }
}
