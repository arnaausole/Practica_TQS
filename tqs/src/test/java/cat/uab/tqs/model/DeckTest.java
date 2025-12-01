package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void testDeckConstructor() {
        Deck deck = new Deck();

        // Cas 1: mida correcta
        assertNotEquals(-1, deck.size());
        assertNotEquals(53, deck.size());
        assertEquals(52, deck.size());

        // Cas 2: no ha d'estar buida
        assertFalse(deck.isEmpty());

        // Cas 3: conté cartes bàsiques i no conté cartes inexistents
        assertTrue(deck.contains(new Card("Hearts", "A")));
        assertTrue(deck.contains(new Card("Clubs", "10")));
        assertTrue(deck.contains(new Card("Spades", "2")));
        assertFalse(deck.contains(new Card("Joker", "5")));
        assertFalse(deck.contains(new Card("Diamonds", "15")));

        // Cas 4: primera carta és la correcta (per ordre de creació)
        Card first = deck.getCardAt(0);
        assertEquals("Hearts", first.getSuit());
        assertEquals("2", first.getRank());
    }

    @Test
    void testGetCards() {
        Deck deck = new Deck();

        // Cas 1: mai ha de ser null
        assertNotNull(deck.getCards());

        // Cas 2: mida correcta
        assertEquals(52, deck.getCards().size());

        // Cas 3: cartes vàlides
        Card sample = deck.getCardAt(10);
        assertNotNull(sample.getSuit());
        assertNotNull(sample.getRank());
    }

    @Test
    void testDrawCard() {
        Deck deck = new Deck();
        int initialSize = deck.size();

        // Cas 1: retorna una carta
        Card c = deck.drawCard();
        assertNotNull(c);

        // Cas 2: disminueix la mida
        assertEquals(initialSize - 1, deck.size());

        // Cas 3: la carta robada ja no és a la baralla
        assertFalse(deck.contains(c));

        // Cas 4: quan la baralla és buida retorna null
        Deck emptyDeck = new Deck();
        while (emptyDeck.size() > 0)
            emptyDeck.drawCard();

        assertTrue(emptyDeck.isEmpty());
        assertNull(emptyDeck.drawCard());
    }
}
