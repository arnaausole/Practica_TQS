package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testConstructor() {
        Card card = new Card("Hearts", "A");

        // Caixa Negra: Partició Equivalent (getters retornen el valor correcte)
        assertEquals("Hearts", card.getSuit());
        assertEquals("A", card.getRank());

        // Caixa Negra: Partició Equivalent (coherència amb altres valors)
        Card card2 = new Card("Clubs", "10");
        assertEquals("Clubs", card2.getSuit());
        assertEquals("10", card2.getRank());

        // Caixa Negra: Partició No Vàlida (suit null)
        assertThrows(AssertionError.class, () -> new Card(null, "A"));

        // Caixa Negra: Partició No Vàlida (rank null)
        assertThrows(AssertionError.class, () -> new Card("Hearts", null));


    }


    @Test
    void testGetNumericValue() {

        // Caixa Negra: Partició Equivalent (cartes numèriques)
        assertEquals(2,  new Card("Hearts", "2").getNumericValue());
        assertEquals(9,  new Card("Clubs", "9").getNumericValue());
        assertEquals(10, new Card("Diamonds", "10").getNumericValue());

        // Caixa Negra: Partició Equivalent (figures J/Q/K)
        assertEquals(10, new Card("Clubs", "J").getNumericValue());
        assertEquals(10, new Card("Spades", "Q").getNumericValue());
        assertEquals(10, new Card("Hearts", "K").getNumericValue());

        // Caixa Negra: Valor Límit/Frontera (As pren 11 inicialment)
        assertEquals(11, new Card("Hearts", "A").getNumericValue());
    }
}
