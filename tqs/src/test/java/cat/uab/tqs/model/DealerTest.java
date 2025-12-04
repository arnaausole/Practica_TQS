package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {



    // El dealer roba cartes mentre tingui 16 o menys,
    // quan arriba a 17 o mes ha de parar (segons regles oficials blackjack)
    @Test
    void testPlay() {
    
        // DECISION COVERAGE + CONDITION COVERAGE +  PATH COVERAGE + LOOP TESTING + LIMITS

        // Cas 1: Dealer amb 17 o mes --> no ha de robar cartes: false/true
        Deck deck1 = new Deck();
        Dealer dealer1 = new Dealer();

        dealer1.getHand().addCard(new Card("Hearts", "10"));
        dealer1.getHand().addCard(new Card("Clubs", "7"));
        int initialSize1 = deck1.size();

        dealer1.play(deck1);
        assertEquals(17, dealer1.getHand().getValue());
        assertEquals(initialSize1, deck1.size()); // no ha robat

        // Cas 2: Dealer amb 16 --> ha de robar com a minim una carta: true/true
        Deck deck2 = new Deck();
        Dealer dealer2 = new Dealer();

        dealer2.getHand().addCard(new Card("Hearts", "10"));
        dealer2.getHand().addCard(new Card("Clubs", "6"));

        int sizeBefore2 = deck2.size();
        dealer2.play(deck2);

        assertTrue(dealer2.getHand().getValue() >= 17);
        assertTrue(deck2.size() < sizeBefore2);  // ha robat

        // Cas 3: Loop testing --> dealer té 3 cartes petites

        Deck deck3 = new Deck();
        Dealer dealer3 = new Dealer();

        dealer3.getHand().addCard(new Card("Spades", "2"));
        dealer3.getHand().addCard(new Card("Diamonds", "3"));
        dealer3.getHand().addCard(new Card("Clubs", "4"));  // total = 9

        int sizeBefore3 = deck3.size();
        dealer3.play(deck3);

        assertTrue(dealer3.getHand().getValue() >= 17);
        assertTrue(deck3.size() < sizeBefore3);  // ha robat diverses vegades

        // Asos (particio equivalent + path coverage)
        
        // Cas 4: Dealer té A + 5 --> pot ser 16 --> ha de robar
        Deck deck4 = new Deck();
        Dealer dealer4 = new Dealer();

        dealer4.getHand().addCard(new Card("Hearts", "A"));  // 11
        dealer4.getHand().addCard(new Card("Hearts", "5"));  // 5 --> total = 16

        int sizeBefore4 = deck4.size();
        dealer4.play(deck4);

        assertTrue(dealer4.getHand().getValue() >= 17);
        assertTrue(deck4.size() < sizeBefore4);

        // Cas 5: Dealer té A + 6 --> 17 exacte --> no ha de robar: false/true

        Deck deck5 = new Deck();
        Dealer dealer5 = new Dealer();

        dealer5.getHand().addCard(new Card("Hearts", "A"));  // 11
        dealer5.getHand().addCard(new Card("Hearts", "6"));  // 6 → total = 17

        int sizeBefore5 = deck5.size();
        dealer5.play(deck5);

        assertEquals(17, dealer5.getHand().getValue());
        assertEquals(sizeBefore5, deck5.size());  // no ha robat

        // Cas 6: baralla buida --> no roba res ni canvia res: true/false

        Deck emptyDeck = new Deck();
        Dealer dealer6 = new Dealer();

        // Buidem la baralla
        for (int i = 0; i < 52; i++) {
            emptyDeck.drawCard();
        }

        dealer6.getHand().addCard(new Card("Clubs", "8"));
        dealer6.getHand().addCard(new Card("Diamonds", "7")); // total = 15

        int sizeBefore6 = emptyDeck.size();
        dealer6.play(emptyDeck);

        assertEquals(15, dealer6.getHand().getValue());  // no canvia
        assertEquals(sizeBefore6, emptyDeck.size());     // no roba res


        // false/true ja cobert (cas 1 i 5)

        // Cas 7: baralla buida pero >17 --> no roba res: false/true

        Deck deck7 = new Deck();
        Dealer dealer7 = new Dealer();

        for (int i = 0; i < 52; i++) { 
            deck7.drawCard(); // buidem
        }

        dealer7.getHand().addCard(new Card("Hearts", "7"));
        dealer7.getHand().addCard(new Card("Clubs", "8")); // total = 15

        int sizeBefore7 = deck7.size();
        dealer7.play(deck7); // no pot entrar al while pq !empty = false

        assertEquals(15, dealer7.getHand().getValue());
        assertEquals(sizeBefore7, deck7.size()); // no roba
    }
    
}
