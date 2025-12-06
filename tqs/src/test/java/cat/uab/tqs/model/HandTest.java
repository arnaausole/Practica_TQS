package cat.uab.tqs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// per fer data-driven tests
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import org.junit.jupiter.api.Test;

public class HandTest {

    @Test
    void testAddCard() {
        Hand hand = new Hand();

        // Caixa Negra: Partició Equivalent (afegir 1 carta incrementa mida)
        hand.addCard(new Card("Hearts", "10"));
        assertEquals(1, hand.getCards().size());

        // Caixa Negra: Partició Equivalent (afegir 2 cartes manté consistència)
        hand.addCard(new Card("Spades", "A"));
        assertEquals(2, hand.getCards().size());

        // Caixa Negra: Partició Equivalent (ordre i valors correctes a la mà)
        assertEquals("10", hand.getCards().get(0).getRank());
        assertEquals("A", hand.getCards().get(1).getRank());
    }

    @Test
    void testGetValue_Pairwise() {
        Hand hand;

        // Caixa Negra: Pairwise testing (i LOOP TESTING) sobre 3 factors:
        // F1: nombre de asos (0, 1, +2)
        // F2: suma cartes NO asos (baixa, mitjana, alta)
        // F3: nombre de cartes (1-2, 3, +4)

        // Caixa Negra: Pairwise (0 asos, suma baixa, 1-2 cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "5"));
        assertEquals(5, hand.getValue());

        // Caixa Negra: Pairwise (0 asos, suma mitjana, 3 cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "2"));
        assertEquals(21, hand.getValue());

        // Caixa Negra: Pairwise (0 asos, suma alta, 4+ cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "10"));
        hand.addCard(new Card("Diamonds", "5"));
        hand.addCard(new Card("Spades", "2"));
        assertEquals(27, hand.getValue());

        // Caixa Negra: Pairwise (1 as, suma baixa, 1-2 cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "5"));
        assertEquals(16, hand.getValue());

        // Caixa Negra: Pairwise (1 as, suma mitjana, 3 cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "9"));
        assertEquals(19, hand.getValue());

        // Caixa Negra: Pairwise (1 as, suma alta, 4+ cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Spades", "K"));
        hand.addCard(new Card("Clubs", "Q"));
        hand.addCard(new Card("Diamonds", "J"));
        assertEquals(31, hand.getValue());

        // Caixa Negra: Pairwise (>=2 asos, suma baixa, 3 cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "9"));
        assertEquals(21, hand.getValue());

        // Caixa Negra: Pairwise (>=2 asos, suma mitjana, 4+ cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "10"));
        hand.addCard(new Card("Diamonds", "9"));
        assertEquals(21, hand.getValue());

        // Caixa Negra: Pairwise (>=2 asos, suma alta, 1-2 cartes)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "A"));
        hand.addCard(new Card("Spades", "K"));
        assertEquals(12, hand.getValue());

        // Caixa Negra: Valor Extrem (mà buida retorna 0)
        hand = new Hand();
        assertEquals(0, hand.getValue());

        // Caixa Blanca: Decision Coverage sobre el while (total > 21 && aces > 0)

        // Caixa Blanca: Rama sense entrar (total ≤ 21, no ajusta asos)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));  
        hand.addCard(new Card("Clubs", "5")); // = 16
        assertEquals(16, hand.getValue());

        // Caixa Blanca: Rama entrant (total > 21 amb as, ajusta valor)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "K"));
        hand.addCard(new Card("Spades", "9"));  // = 30 --> ajusta = 20
        assertEquals(20, hand.getValue());

        // Caixa Blanca: Condition Coverage sobre if(c.getRank().equals("A"))

        // Caixa Blanca: Condició true (carta és As)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A")); // true
        assertEquals(11, hand.getValue());

        // Caixa Blanca: Condició false (carta no és As)
        hand = new Hand();
        hand.addCard(new Card("Clubs", "9")); // false
        assertEquals(9, hand.getValue());
    }

    @Test
    void testIsBust() {
        Hand hand = new Hand();

        // Caixa Negra: Partició Equivalent (valor dins del rang normal sense Bust)
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertFalse(hand.isBust());  // 19

        // Caixa Negra: Partició Equivalent (valor excedeix 21 → Bust)
        hand.addCard(new Card("Spades", "5"));
        assertTrue(hand.isBust());   // 24

        // Caixa Negra: Valor Límit/Frontera (As ajustat evita Bust)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Clubs", "9"));
        hand.addCard(new Card("Spades", "9"));
        assertFalse(hand.isBust());  // 19

        // Caixa Negra: Valor Límit/Frontera (exactament 21)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "A"));
        assertFalse(hand.isBust());
    }

    @Test
    void testIsBlackjack() {
        Hand hand = new Hand();

        // Caixa Blanca: Condition Coverage sobre (size == 2 && value == 21)

        // Caixa Negra: Cas Base (blackjack natural → true/true)
        hand.addCard(new Card("Hearts", "A"));
        hand.addCard(new Card("Spades", "K"));
        assertTrue(hand.isBlackjack());

        // Caixa Negra: Partició Equivalent (2 cartes però valor < 21 → true/false)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "10"));
        hand.addCard(new Card("Clubs", "9"));
        assertFalse(hand.isBlackjack());

        // Caixa Negra: Partició Equivalent (valor 21 amb >2 cartes → false/true)
        hand.addCard(new Card("Clubs", "2"));
        assertFalse(hand.isBlackjack());

        // Caixa Negra: Valor Límit amb 3 cartes (no compte com blackjack)
        hand = new Hand();
        hand.addCard(new Card("Hearts", "7"));
        hand.addCard(new Card("Clubs", "7"));
        hand.addCard(new Card("Spades", "7"));
        assertFalse(hand.isBlackjack());

        // Caixa Negra: Partició Equivalent (no és 21 ni 2 cartes → false/false)
        
        hand = new Hand();
        hand.addCard(new Card("Hearts", "5"));
        hand.addCard(new Card("Clubs", "5"));
        hand.addCard(new Card("Spades", "5")); // !=2 cartes, !=21
        assertFalse(hand.isBlackjack());

    }


    /**
     * Data-Driven test:
     *   - Entrades: conjunt de ranks, separat per espais
     *   - Sortides: valor total, si es bust, si es blackjack
     *
     *  Cobreix:
     *   - 0 asos, 1 as, molts asos
     *   - casos de frontera (21, >21, +21)
     *   - blackjack natural vs 21 amb 3 cartes
     */

    @ParameterizedTest(name = "Mà \"{0}\" → value={1}, bust={2}, blackjack={3}")
    @CsvFileSource(
        resources = "/cat/uab/tqs/resources/hand_scenarios.csv",
        numLinesToSkip = 1,
        delimiter = ';'
    )

    void testHandBehaviour_DataDriven(String cards, int expectedValue, boolean expectedBust, boolean expectedBlackjack) {

        Hand hand = new Hand();

        // fem la ma a partir dels ranks  (nums) (el pal no afecta)
        for (String rank : cards.split(" ")) {

            if (!rank.isBlank()) {

                hand.addCard(new Card("Hearts", rank));
            }
        }

        // comprovacio dels 3 metodes de Hand
        assertEquals(expectedValue, hand.getValue(), "Valor inesperat per a la mà " + cards);
        assertEquals(expectedBust, hand.isBust(), "Bust inesperat per a la mà " + cards);
        assertEquals(expectedBlackjack, hand.isBlackjack(), "Blackjack inesperat per a la mà " + cards);
    }
}
