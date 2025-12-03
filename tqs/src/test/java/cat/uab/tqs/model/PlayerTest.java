package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testConstructor() {
        Player p = new Player();

        // Cas 1: la mà es crea correctament
        assertNotNull(p.getHand());

        // Cas 2: al principi no té cartes
        assertEquals(0, p.getHand().getCards().size());

        // Cas 3: al principi no està en stand
        assertFalse(p.isStanding());
    }

    @Test
    void testHit() {
        Player p = new Player();

        // Cas 1: afegir una carta quan no està en stand
        p.hit(new Card("Hearts", "10"));
        assertEquals(1, p.getHand().getCards().size());
        assertEquals("10", p.getHand().getCards().get(0).getRank());

        // Cas 2: afegir diverses cartes
        p.hit(new Card("Clubs", "5"));
        p.hit(new Card("Spades", "2"));
        assertEquals(3, p.getHand().getCards().size());

        // Cas 3: dps de plantarse no ha dafegir més cartes
        p.stand();
        p.hit(new Card("Diamonds", "9"));
        assertEquals(3, p.getHand().getCards().size());
    }

    @Test
    void testStand() {
        Player p = new Player();

        // Cas 1: es planta correctament
        p.stand();
        assertTrue(p.isStanding());

        // Cas 2: cridar stand diverses vegades no ha de canviar res
        p.stand();
        assertTrue(p.isStanding());

        // Cas 3: un cop plantat no pot afegir cartes
        p.hit(new Card("Spades", "9"));
        assertEquals(0, p.getHand().getCards().size());
    }

    @Test
    void testReset() {
        Player p = new Player();

        // preprem estat amb cartes i en stand
        p.hit(new Card("Clubs", "3"));
        p.hit(new Card("Hearts", "7"));
        p.stand();

        // Cas 1: reset esborra totes les cartes i treu el stand
        p.reset();
        assertEquals(0, p.getHand().getCards().size());
        assertFalse(p.isStanding());

        // Cas 2: dps de reset pot tornar a robar cartes
        p.hit(new Card("Diamonds", "4"));
        assertEquals(1, p.getHand().getCards().size());
        assertFalse(p.isStanding());
    }
}
