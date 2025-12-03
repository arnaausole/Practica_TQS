package cat.uab.tqs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class HandTest {

    @Test
    void testAddCard() {
        Hand hand = new Hand();

        // Cas 1: afegir 1 carta
        hand.addCard(new Card("Hearts", "10"));
        assertEquals(1, hand.getCards().size());

        // Cas 2: afegir 2 cartes
        hand.addCard(new Card("Spades", "A"));
        assertEquals(2, hand.getCards().size());

        // Cas 3: comprovar que les cartes són les que toca
        assertEquals("10", hand.getCards().get(0).getRank());
        assertEquals("A", hand.getCards().get(1).getRank());
    }


    @Test
    void testGetValue() {
        Hand hand = new Hand();

        // casos simples sense As
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertEquals(19, hand.getValue());

        // Reset per als següents casos
        hand = new Hand();

        // Tractament de l'As com 11 si no fa bust
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "9"));
        assertEquals(20, hand.getValue());   // 11 + 9

        // Reset
        hand = new Hand();

        // Tractament de l'As com 1 si fa bust
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "K"));
        hand.addCard(new Card("Diamonds", "9"));
        assertEquals(20, hand.getValue());  // 1 + 10 + 9

        // Reset
        hand = new Hand();

        // Múltiples As
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "9"));
        assertEquals(21, hand.getValue()); // 11 + 1 + 9

        // Reset
        hand = new Hand();

        // Mà buida
        assertEquals(0, hand.getValue());

        // Reset
        hand = new Hand();

        // Moltes cartes (loop testing)
        hand.addCard(new Card("Hearts", "5"));
        hand.addCard(new Card("Clubs", "5"));
        hand.addCard(new Card("Diamonds", "5"));
        assertEquals(15, hand.getValue());
    }


    @Test
    void testIsBust() {
        Hand hand = new Hand();

        // particions equivalents: < 21, 21, > 21

        // Cas no bust
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertFalse(hand.isBust());  // 19

        // Cas bust simple
        hand.addCard(new Card("Spades", "5"));
        assertTrue(hand.isBust());   // 24

        // Cas que NO ha de bustar perquè lAs passa a 1
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "9"));
        assertFalse(hand.isBust());  // 1 + 9 + 9 = 19

        // cas exacte 21

        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "A")); // 21
        assertFalse(hand.isBust());

    }


    @Test
    void testIsBlackjack() {
        Hand hand = new Hand();

        // particions equivalents

        // Cas blackjack natural
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Spades", "K"));
        assertTrue(hand.isBlackjack());

        // no es blackjack amb 2 cartes
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertFalse(hand.isBlackjack());

        // No és blackjack amb mes de 2 cartes
        hand.addCard(new Card("Clubs", "2"));
        assertFalse(hand.isBlackjack());

        // No és blackjack: 21 pero amb 3 cartes
        hand = new Hand();
        hand.addCard(new Card("Hearts", "7"));
        hand.addCard(new Card("Clubs", "7"));
        hand.addCard(new Card("Spades", "7"));
        assertFalse(hand.isBlackjack());

    }


}
