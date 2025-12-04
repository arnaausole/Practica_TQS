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
    void testGetValue_Pairwise() {
        Hand hand;

        // Pairwise testing (i LOOP TESTING) sobre 3 factors:
        // F1: nombre de asos (0, 1, +2)
        // F2: suma cartes NO asos (baixa, mitjana, alta)
        // F3: nombre de cartes (1-2, 3, +4)

        // CAS 1: 0 asos, baixa, 1-2
        hand = new Hand();
        hand.addCard(new Card("Hearts", "5"));
        assertEquals(5, hand.getValue());

        // CAS 2: 0 asos, mitjana, 3
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "2"));
        assertEquals(21, hand.getValue());

        // CAS 3: 0 asos, alta, 4+
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "10"));
        hand.addCard(new Card("Diamonds", "5"));
        hand.addCard(new Card("Spades", "2"));
        assertEquals(27, hand.getValue());

        // CAS 4: 1 as, baixa, 1-2
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "5"));
        assertEquals(16, hand.getValue());

        // CAS 5: 1 as, mitjana, 3
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "9"));
        assertEquals(19, hand.getValue());

        // CAS 6: 1 as, alta, 4+
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Spades", "K"));
        hand.addCard(new Card("Clubs", "Q"));
        hand.addCard(new Card("Diamonds", "J"));
        assertEquals(31, hand.getValue());

        // CAS 7: 2+ asos, baixa, 3
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "9"));
        assertEquals(21, hand.getValue());

        // CAS 8: 2+ asos, mitjana, 4+
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "10"));
        hand.addCard(new Card("Diamonds", "9"));
        assertEquals(21, hand.getValue());

        // CAS 9: 2+ asos, alta, 1-2
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "K"));
        assertEquals(12, hand.getValue());

        // Cas extra: mà buida
        hand = new Hand();
        assertEquals(0, hand.getValue());

        // Decision Coverage: Cobrir el while (total > 21 && aces > 0)

        // Cas 1: el while no entra (total ≤ 21)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));  
        hand.addCard(new Card("Clubs", "5")); // = 16
        assertEquals(16, hand.getValue());

        // Cas 2: el while entra (total > 21 amb as)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "K"));
        hand.addCard(new Card("Spades", "9"));  // = 30 --> ajusta = 20
        assertEquals(20, hand.getValue());

        // Condition Coverage --> cobrir if(c.getRank().equals("A"))

        // Cas 1: true (es un as)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A")); // true
        assertEquals(11, hand.getValue());

        // Cas 2: false (no es un as)
        hand = new Hand();
        hand.addCard(new Card("Clubs", "9")); // false
        assertEquals(9, hand.getValue());
    }

    @Test
    void testIsBust() {
        Hand hand = new Hand();

        // Cas < 21
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertFalse(hand.isBust());  // 19

        // Cas > 21 (bust)
        hand.addCard(new Card("Spades", "5"));
        assertTrue(hand.isBust());   // 24

        // As baixa per evitar bust
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "9"));
        assertFalse(hand.isBust());  // 19

        // Cas exacte 21
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "A"));
        assertFalse(hand.isBust());
    }

    @Test
    void testIsBlackjack() {
        Hand hand = new Hand();

        //condition coverage

        // Cas blackjack natural: true/true
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Spades", "K"));
        assertTrue(hand.isBlackjack());

        // 2 cartes però no 21: true/false
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertFalse(hand.isBlackjack());

        // Més de 2 cartes: false/true
        hand.addCard(new Card("Clubs", "2"));
        assertFalse(hand.isBlackjack());

        // 21 amb 3 cartes: false/true
        hand = new Hand();
        hand.addCard(new Card("Hearts", "7"));
        hand.addCard(new Card("Clubs", "7"));
        hand.addCard(new Card("Spades", "7"));
        assertFalse(hand.isBlackjack());

        // no es 21 ni te 2 cartes: false/false
        
        hand = new Hand();
        hand.addCard(new Card("Hearts", "5"));
        hand.addCard(new Card("Clubs", "5"));
        hand.addCard(new Card("Spades", "5")); // !=2 cartes, !=21
        assertFalse(hand.isBlackjack());

    }
}
