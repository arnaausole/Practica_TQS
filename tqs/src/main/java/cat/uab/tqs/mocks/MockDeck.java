package cat.uab.tqs.mocks;

import cat.uab.tqs.model.Card;
import cat.uab.tqs.model.Deck;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDeck extends Deck {

    private List<Card> forcedCards = new ArrayList<>();

    public MockDeck() {
        super(); 
        // El constructor Deck crea 52 cartes, pero nosaltres
        // farem override dels metodes per ignorarles i fer servir la nostra llista
    }

    // definim quines cartes sortiran i en quin ordre
    public void setCards(Card... cards) {
        this.forcedCards = new ArrayList<>(Arrays.asList(cards));
    }

    @Override
    public Card drawCard() {
        if (forcedCards.isEmpty()) {
            return null;
        }
        return forcedCards.remove(0);
    }

    @Override
    public int size() {
        return forcedCards.size();
    }

    @Override
    public boolean isEmpty() {
        return forcedCards.isEmpty();
    }
    
    // fem override de shuffle pq no faci res i no trenqui el nostre ordre
    @Override
    public void shuffle() {
        // res
    }
}