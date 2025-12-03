package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import cat.uab.tqs.mocks.MockShuffleRandom;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void testDeckConstructor() {

        Deck deck = new Deck();

        // Cas 1: mida correcta
        assertEquals(52, deck.size());

        // Cas 2: no ha destar buida
        assertFalse(deck.isEmpty());

        // Cas 3: conte cartes vàlides
        assertTrue(deck.contains(new Card("Hearts", "A")));
        assertTrue(deck.contains(new Card("Clubs", "10")));
        assertTrue(deck.contains(new Card("Spades", "2")));

        // Cas 4: no conte cartes impossibles
        assertFalse(deck.contains(new Card("Joker", "5")));
        assertFalse(deck.contains(new Card("Diamonds", "15")));
    }

    @Test
    void testGetCards() {

        Deck deck = new Deck();

        // Cas 1: mai null
        assertNotNull(deck.getCards());

        // Cas 2: mida correcta
        assertEquals(52, deck.getCards().size());

        // Cas 3: carta vàlida
        Card c = deck.getCardAt(0);
        assertNotNull(c.getRank());
        assertNotNull(c.getSuit());
    }

    @Test
    void testDrawCard() {

        Deck deck = new Deck();
        int initialSize = deck.size();

        // Cas 1: retorna carta
        Card c = deck.drawCard();
        assertNotNull(c);

        // Cas 2: mida disminueix
        assertEquals(initialSize - 1, deck.size());

        // Cas 3: carta robada ja no hi és
        assertFalse(deck.contains(c));

        // Cas 4: quan es buida --> null
        Deck empty = new Deck(new MockShuffleRandom());
        while (!empty.isEmpty())
            empty.drawCard();

        assertTrue(empty.isEmpty());
        assertNull(empty.drawCard());
    }

    @Test
    void testShuffleWithMock() {

        MockShuffleRandom mock = new MockShuffleRandom();
        Deck deck = new Deck(mock);

        // Cas 1: la primera carta es exactament la del mock
        Card first = deck.getCardAt(0);
        assertEquals("Hearts", first.getSuit());
        assertEquals("A", first.getRank());

        // Cas 2: mida continua sent 52 o mida del mock
        assertEquals(4, deck.size()); // El mock te 4 cartes per tests
    }

    @Test
    void testShuffleChangesOrder() {

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Cas: en baralles random  no haurien de coincidir
        boolean identical = true;
        for (int i = 0; i < deck1.size(); i++) {
            Card c1 = deck1.getCardAt(i);
            Card c2 = deck2.getCardAt(i);

            if (!c1.getSuit().equals(c2.getSuit()) ||
                !c1.getRank().equals(c2.getRank())) {
                identical = false;
                break;
            }
        }

        assertFalse(identical);
    }
}
