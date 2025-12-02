package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {



    // El dealer roba cartes mentre tingui 16 o menys,
    // quan arriba a 17 o mes ha de parar (segons regles oficials blackjack)
    @Test
    void testPlay() {
    
        // CAS 1: Dealer amb 17 o més --> no ha de robar cartes
        Deck deck1 = new Deck();
        Dealer dealer1 = new Dealer();

        dealer1.getHand().addCard(new Card("Hearts", "10"));
        dealer1.getHand().addCard(new Card("Clubs", "7"));
        int initialSize1 = deck1.size();

        dealer1.play(deck1);
        assertEquals(17, dealer1.getHand().getValue());
        assertEquals(initialSize1, deck1.size()); // no ha robat

        // CAS 2: Dealer amb 16 --> ha de robar com a minim una carta
        Deck deck2 = new Deck();
        Dealer dealer2 = new Dealer();

        dealer2.getHand().addCard(new Card("Hearts", "10"));
        dealer2.getHand().addCard(new Card("Clubs", "6"));

        int sizeBefore2 = deck2.size();
        dealer2.play(deck2);

        assertTrue(dealer2.getHand().getValue() >= 17);
        assertTrue(deck2.size() < sizeBefore2);  // ha robat

        // CAS 3: Loop testing --> dealer té 3 cartes petites

        Deck deck3 = new Deck();
        Dealer dealer3 = new Dealer();

        dealer3.getHand().addCard(new Card("Spades", "2"));
        dealer3.getHand().addCard(new Card("Diamonds", "3"));
        dealer3.getHand().addCard(new Card("Clubs", "4"));  // total = 9

        int sizeBefore3 = deck3.size();
        dealer3.play(deck3);

        assertTrue(dealer3.getHand().getValue() >= 17);
        assertTrue(deck3.size() < sizeBefore3);  // ha robat diverses vegades

        // CAS 4: Asos (path coverage)
        
        // Dealer té A + 5 → pot ser 16 → ha de robar
        Deck deck4 = new Deck();
        Dealer dealer4 = new Dealer();

        dealer4.getHand().addCard(new Card("Hearts", "A"));  // 11
        dealer4.getHand().addCard(new Card("Hearts", "5"));  // 5 --> total = 16

        int sizeBefore4 = deck4.size();
        dealer4.play(deck4);

        assertTrue(dealer4.getHand().getValue() >= 17);
        assertTrue(deck4.size() < sizeBefore4);

        // Dealer té A + 6 --> 17 exacte → no ha de robar

        Deck deck5 = new Deck();
        Dealer dealer5 = new Dealer();

        dealer5.getHand().addCard(new Card("Hearts", "A"));  // 11
        dealer5.getHand().addCard(new Card("Hearts", "6"));  // 6 → total = 17

        int sizeBefore5 = deck5.size();
        dealer5.play(deck5);

        assertEquals(17, dealer5.getHand().getValue());
        assertEquals(sizeBefore5, deck5.size());  // no ha robat
    }
}
