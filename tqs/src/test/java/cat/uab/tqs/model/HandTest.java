package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class HandTest {

    @Test
    void testAddCardStoresCards() {
        Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        assertEquals(1, hand.getCards().size());
    }

    @Test
    void testGetValueWithoutAces() {
        Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));

        assertEquals(19, hand.getValue());
    }

    @Test
    void testIsBustSimple() {
        Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "K"));
        hand.addCard(new Card("Clubs", "Q"));
        hand.addCard(new Card("Spades", "5"));

        assertTrue(hand.isBust());
    }
}
