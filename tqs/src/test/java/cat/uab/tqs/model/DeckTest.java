package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import cat.uab.tqs.mocks.MockShuffleRandom;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void testDeckConstructor() {

        Deck deck = new Deck();

        // Caixa Negra: Partició Equivalent (mida correcta)
        assertEquals(52, deck.size());

        // Caixa Negra: Partició Equivalent (baralla no buida)
        assertFalse(deck.isEmpty());

        // Caixa Negra: Partició Equivalent (cartes vàlides existeixen)
        assertTrue(deck.contains(new Card("Hearts", "A")));
        assertTrue(deck.contains(new Card("Clubs", "10")));
        assertTrue(deck.contains(new Card("Spades", "2")));

        // Caixa Negra: Partició No Vàlida (no conté cartes impossibles)
        assertFalse(deck.contains(new Card("Joker", "5")));
        assertFalse(deck.contains(new Card("Diamonds", "15")));

        // Caixa Blanca: Loop Testing Aniuat (no conté cartes duplicades)

        assertFalse(deck.hasDuplicateCards());

        // Caixa Negra: Valor Límit/Frontera (getCardAt fora de rang)
        assertThrows(AssertionError.class, () -> deck.getCardAt(-1));
        assertThrows(AssertionError.class, () -> deck.getCardAt(52));


    }

    @Test
    void testGetCards() {

        Deck deck = new Deck();

        // Caixa Negra: Partició Equivalent (llista de cartes mai és null)
        assertNotNull(deck.getCards());

        // Caixa Negra: Partició Equivalent (mida inicial correcta)
        assertEquals(52, deck.getCards().size());

        // Caixa Negra: Partició Equivalent (carta vàlida té rank i suit)
        Card first = deck.getCardAt(0);
        Card last  = deck.getCardAt(deck.size() - 1);
        assertNotNull(first.getRank());
        assertNotNull(first.getSuit());
        assertNotNull(last.getRank());
        assertNotNull(last.getSuit());

        // Caixa Negra: Partició No Vàlida (contains amb carta null)
        assertThrows(AssertionError.class, () -> deck.contains(null));
    }

    @Test
    void testDrawCard() {

        Deck deck = new Deck();
        int initialSize = deck.size();

        // Caixa Negra: Partició Equivalent (draw retorna carta)
        Card c = deck.drawCard();
        assertNotNull(c);

        // Caixa Negra: Partició Equivalent (mida disminueix en 1)
        assertEquals(initialSize - 1, deck.size());

        // Caixa Negra: Partició Equivalent (carta robada ja no hi és)
        assertFalse(deck.contains(c));

        // Caixa Negra: Valor Límit/Frontera (draw amb baralla buida retorna null)
        Deck empty = new Deck(new MockShuffleRandom());
        while (!empty.isEmpty())
            empty.drawCard();

        assertTrue(empty.isEmpty());
        assertNull(empty.drawCard());
    }

    @Test
    void testShuffleWithMock() {

        Shuffler mock = new MockShuffleRandom();
        Deck deck = new Deck(mock);

        // Caixa Negra: Mock object (ordre controlat, primera carta del mock)
        Card first = deck.getCardAt(0);
        assertEquals("Hearts", first.getSuit());
        assertEquals("A", first.getRank());

        // Caixa Negra: Partició Equivalent (mida segueix la del mock)
        assertEquals(4, deck.size()); // El mock te 4 cartes per tests
    }

    @Test
    void testShuffleChangesOrder() {

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Caixa Blanca: Loop Testing Aniuat (barreja aleatòria no ha de coincidir)
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
