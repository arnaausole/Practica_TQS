package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void testCreateDeckCorrectly() 
    {
        Deck deck = new Deck();
        
        for(int i = 0; i < deck.getCards().size(); i++) 
        {
            Card card = deck.getCards().get(i);
            
            for(int j = i + 1; j < deck.getCards().size(); j++) 
            {
                Card otherCard = deck.getCards().get(j);
                
                // Assegurar que no hi ha cartes duplicades
                assertFalse(card.getRank().equals(otherCard.getRank()) && card.getSuit().equals(otherCard.getSuit()));
            }
        }
    }

    // Assegurar que la baralla comença amb 52 cartes
    @Test
    void testDeckStartsWith52Cards() 
    {
        Deck deck = new Deck();
        assertEquals(52, deck.getCards().size());
    }

    // Assegurar que després de robar una carta, la mida de la baralla disminueix en 1
    @Test
    void testDrawCardReducesDeckSize() 
    {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();

        Card drawn = deck.drawCard();

        assertNotNull(drawn);
        assertEquals(initialSize - 1, deck.getCards().size());
    }

}