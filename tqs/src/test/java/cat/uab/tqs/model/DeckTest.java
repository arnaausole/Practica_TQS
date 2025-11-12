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

}