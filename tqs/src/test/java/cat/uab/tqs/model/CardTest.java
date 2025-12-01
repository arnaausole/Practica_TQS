package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testConstructor() {
        Card card = new Card("Hearts", "A");

        // Cas 1: els getters retornen el valor correcte
        assertEquals("Hearts", card.getSuit());
        assertEquals("A", card.getRank());

        // Cas 2: més proves de consistència
        Card card2 = new Card("Clubs", "10");
        assertEquals("Clubs", card2.getSuit());
        assertEquals("10", card2.getRank());
    }


    @Test
    void testGetNumericValue() {

        // Cas 1: Cartes numèriques
        assertEquals(2,  new Card("Hearts", "2").getNumericValue());
        assertEquals(9,  new Card("Clubs", "9").getNumericValue());
        assertEquals(10, new Card("Diamonds", "10").getNumericValue());

        // Cas 2: Figures (J, Q, K)
        assertEquals(10, new Card("Clubs", "J").getNumericValue());
        assertEquals(10, new Card("Spades", "Q").getNumericValue());
        assertEquals(10, new Card("Hearts", "K").getNumericValue());

        // Cas 3: As (valor inicial = 11)
        assertEquals(11, new Card("Hearts", "A").getNumericValue());
    }
}
