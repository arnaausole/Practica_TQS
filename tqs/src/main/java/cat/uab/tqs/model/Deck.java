package cat.uab.tqs.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards;
    private Shuffler shuffler;

    // el palo es valid
    private boolean isValidSuit(String suit) {
        return "Hearts".equals(suit)
            || "Diamonds".equals(suit)
            || "Clubs".equals(suit)
            || "Spades".equals(suit);
    }

    // el numero es valid
    private boolean isValidRank(String rank) {
        return "2".equals(rank) || "3".equals(rank) || "4".equals(rank)
            || "5".equals(rank) || "6".equals(rank) || "7".equals(rank)
            || "8".equals(rank) || "9".equals(rank) || "10".equals(rank)
            || "J".equals(rank) || "Q".equals(rank) || "K".equals(rank)
            || "A".equals(rank);
    }

    private boolean invariant() {
        if (cards == null || shuffler == null) {
            return false;
        }
        if (cards.size() < 0 || cards.size() > 52) {
            return false;
        }
        // totes les cartes dins del domini --> aixo serveix per quan dealer/player fan addCart (es fa drawcard de la baralla)
        // i aixi ens assegurem que siguin cartes vàlides a la hora de jugar
        for (Card c : cards) {
            if (c == null) {
                return false;
            }
            if (!isValidSuit(c.getSuit()) || !isValidRank(c.getRank())) {
                return false;
            }
        }
        return true;
    }


    public Deck(Shuffler shuffler) {

        //precondicio
        assert shuffler != null;

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

        assert invariant();
    }

    public Deck() {
        this(new ShuffleRandom());
        assert invariant();
    }

    public int size() {
        assert invariant();

        return cards.size();

    }

    public boolean isEmpty() {

        assert invariant();

        return cards.isEmpty();
    }

    public boolean contains(Card c) {

        assert invariant();

        //precond
        assert c != null;

        for (Card card : cards) {
            if (card.getRank().equals(c.getRank()) &&
                card.getSuit().equals(c.getSuit())) {

                assert invariant();
                return true;
            }
        }
        assert invariant();
        return false;
    }

    public Card getCardAt(int index) {

        assert invariant();

        //pre cond
        assert index >= 0 && index < cards.size();

        return cards.get(index);
    }

    public List<Card> getCards() {

        assert invariant();

        return cards;
    }

    public Card drawCard() {

        assert invariant();

        int oldSize = cards.size();
        Card result = cards.isEmpty() ? null : cards.remove(0);

        if (oldSize == 0) {

            // post cond: quan estava buida, segueix buida i retorna null
            assert result == null;
            assert cards.size() == 0;
        } else {

            // post cond: quan no estava buida, mida baixa  1 i la carta ja no hi es
            assert result != null;
            assert cards.size() == oldSize - 1;
            assert !contains(result);
        }

        assert invariant();
        return result;
    }

    public void shuffle() {
        assert invariant();

        int oldSize = cards.size();
        shuffler.shuffle(cards);

        // post cond
        assert cards.size() == oldSize;
        assert invariant();

    }

    public Shuffler getShuffler() {

        assert invariant();
        return shuffler;
    }

    public void setShuffler(Shuffler shuffler) {

        assert invariant();
        assert shuffler != null;

        this.shuffler = shuffler;
        this.shuffler.shuffle(cards);

        assert invariant();
    }
}
