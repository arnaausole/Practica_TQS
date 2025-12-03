package cat.uab.tqs.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards;
    private Shuffler shuffler;

    public Deck(Shuffler shuffler) {
        this.shuffler = shuffler;
        cards = new ArrayList<>();

        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        this.shuffler.shuffle(cards);
    }

    public Deck() {
        this(new ShuffleRandom());
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean contains(Card c) {
        for (Card card : cards) {
            if (card.getRank().equals(c.getRank()) &&
                card.getSuit().equals(c.getSuit())) {
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
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public void shuffle() {
        shuffler.shuffle(cards);
    }

    public Shuffler getShuffler() {
        return shuffler;
    }

    public void setShuffler(Shuffler shuffler) {
        this.shuffler = shuffler;
        this.shuffler.shuffle(cards);
    }
}
