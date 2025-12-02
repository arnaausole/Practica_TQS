package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testConstructor() {
        Player p = new Player();

        // Cas 1: La baralla es crea correctrament i no es planta (stand)
        assertNotNull(p.getHand());
        assertFalse(p.isStanding()); // ha de robar cartes

        // Cas 2: no té cartes al iniciar
        assertEquals(0, p.getHand().getCards().size());
        assertNotEquals(true, p.isStanding());
    }

    @Test
    void testHit() {
        Player p = new Player();

        // Cas 1: afegir carta
        p.hit(new Card("Hearts", "10"));
        assertEquals(1, p.getHand().getCards().size());

        // Cas 2: no afegeix si està en stand (es planta)
        p.stand();
        p.hit(new Card("Clubs", "5"));
        assertEquals(1, p.getHand().getCards().size());
    }

    @Test
    void testStand() {
        Player p = new Player();

        // Cas 1: Comprovem si planta correctament
        p.stand();
        assertTrue(p.isStanding());

        // Cas 2:
        p.hit(new Card("Spades", "9")); // no ha dafegir
        assertEquals(0, p.getHand().getCards().size());
    }

    @Test
    void testReset() {
        
        Player p = new Player();

        p.hit(new Card("Clubs", "3"));
        p.stand();

        p.reset();

        assertEquals(0, p.getHand().getCards().size());
        assertFalse(p.isStanding());
    }
}
