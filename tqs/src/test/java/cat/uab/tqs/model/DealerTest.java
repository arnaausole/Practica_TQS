package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import cat.uab.tqs.mocks.MockDeck;


class DealerTest {

    // Caixa Negra: Regla de negoci (dealer roba fins a 16, planta a 17 o més)
    @Test
    void testPlay() {
    
        // Caixa Blanca: Decision/Condition/Path Coverage + Loop testing + valors límit

        // Caixa Negra: Valor Límit/Frontera (17 o més) - Dealer no roba: false/true
        Deck deck1 = new Deck();
        Dealer dealer1 = new Dealer();

        dealer1.getHand().addCard(new Card("Hearts", "10"));
        dealer1.getHand().addCard(new Card("Clubs", "7"));
        int initialSize1 = deck1.size();

        dealer1.play(deck1);
        assertEquals(17, dealer1.getHand().getValue());
        assertEquals(initialSize1, deck1.size()); // no ha robat

        // Caixa Negra: Valor Límit/Frontera (16) - Dealer ha de robar com a mínim una carta: true/true
        Deck deck2 = new Deck();
        Dealer dealer2 = new Dealer();

        dealer2.getHand().addCard(new Card("Hearts", "10"));
        dealer2.getHand().addCard(new Card("Clubs", "6"));

        int sizeBefore2 = deck2.size();
        dealer2.play(deck2);

        assertTrue(dealer2.getHand().getValue() >= 17);
        assertTrue(deck2.size() < sizeBefore2);  // ha robat

        // Caixa Negra: Loop Testing (valors baixos obliguen múltiples robades)

        Deck deck3 = new Deck();
        Dealer dealer3 = new Dealer();

        dealer3.getHand().addCard(new Card("Spades", "2"));
        dealer3.getHand().addCard(new Card("Diamonds", "3"));
        dealer3.getHand().addCard(new Card("Clubs", "4"));  // total = 9

        int sizeBefore3 = deck3.size();
        dealer3.play(deck3);

        assertTrue(dealer3.getHand().getValue() >= 17);
        assertTrue(deck3.size() < sizeBefore3);  // ha robat diverses vegades

        // Caixa Negra: Partició Equivalent amb Asos (també cobreix camins amb valors flexibles)
        
        // Caixa Negra: Valor Límit/Frontera (A + 5 = 16) - Ha de robar
        Deck deck4 = new Deck();
        Dealer dealer4 = new Dealer();

        dealer4.getHand().addCard(new Card("Hearts", "A"));  // 11
        dealer4.getHand().addCard(new Card("Hearts", "5"));  // 5 --> total = 16

        int sizeBefore4 = deck4.size();
        dealer4.play(deck4);

        assertTrue(dealer4.getHand().getValue() >= 17);
        assertTrue(deck4.size() < sizeBefore4);

        // Caixa Negra: Valor Límit/Frontera (Boundary Value) - Dealer planta amb 17 exacte: false/true

        Deck deck5 = new Deck();
        Dealer dealer5 = new Dealer();

        dealer5.getHand().addCard(new Card("Hearts", "A"));  // 11
        dealer5.getHand().addCard(new Card("Hearts", "6"));  // 6 → total = 17

        int sizeBefore5 = deck5.size();
        dealer5.play(deck5);

        assertEquals(17, dealer5.getHand().getValue());
        assertEquals(sizeBefore5, deck5.size());  // no ha robat

        // Caixa Negra: Cas Extrems (baralla buida) - no roba res ni canvia res: true/false

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

        // Caixa Negra: Cas Extrems (baralla buida + mà > 17) - no roba res: false/true

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


        // Caixa Negra: Mock object per tests deterministes

        // Caixa Negra: Partició Equivalent (16) amb MockDeck - garanteix 1 robada
        MockDeck mockDeck8 = new MockDeck();
        mockDeck8.setCards(
            new Card("Spades", "2"),  // 1ra carta que ha de robar --> 10 + 6 = 18
            new Card("Clubs",  "10")  // 2na carta (no ha de robar)
        );

        Dealer dealer8 = new Dealer();
        dealer8.getHand().addCard(new Card("Hearts", "10"));
        dealer8.getHand().addCard(new Card("Clubs",  "6"));  // total = 16

        int sizeBefore8 = mockDeck8.size();
        dealer8.play(mockDeck8);

        assertEquals(18, dealer8.getHand().getValue());           // 16 + 2
        assertEquals(sizeBefore8 - 1, mockDeck8.size());          // ha robat una carta

        // Caixa Negra: Loop Testing amb MockDeck (diverses robades fins assolir 17 i parar)
        MockDeck mockDeck9 = new MockDeck();
        mockDeck9.setCards(
            new Card("Spades",   "4"),  // 1ra --> 9 + 4 = 13
            new Card("Diamonds", "4"),  // 2na --> 13 + 4 = 17
            new Card("Clubs",    "4")   // 3ra (no hauria de robar)
        );

        Dealer dealer9 = new Dealer();
        dealer9.getHand().addCard(new Card("Hearts", "9"));  // 9

        int sizeBefore9 = mockDeck9.size();
        dealer9.play(mockDeck9);

        assertEquals(17, dealer9.getHand().getValue()); // 9 + 4 + 4 = 17
        assertEquals(sizeBefore9 - 2, mockDeck9.size()); 

    }
}
