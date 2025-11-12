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

    

}