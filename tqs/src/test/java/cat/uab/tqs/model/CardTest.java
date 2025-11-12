package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CardTest {

    @Test
    void testCreateCardAndGetValues() 
    {
        Card card = new Card("Hearts", "A");
        assertEquals("Hearts", card.getSuit());
        assertEquals("A", card.getRank());
    }

    @Test
    void testNumericValueOfFaceCards() 
    {
        Card jack = new Card("Clubs", "J");
        Card queen = new Card("Diamonds", "Q");
        Card king = new Card("Spades", "K");

        assertEquals(10, jack.getNumericValue());
        assertEquals(10, queen.getNumericValue());
        assertEquals(10, king.getNumericValue());
    }

    @Test
    void testNumericValueOfAce()
    {
        Card ace = new Card("Hearts", "A");
        assertEquals(11, ace.getNumericValue());
    }

    @Test
    void testNumericValueOfNumberCard() 
    {
        Card five = new Card("Hearts", "5");
        assertEquals(5, five.getNumericValue());
    }

    

}