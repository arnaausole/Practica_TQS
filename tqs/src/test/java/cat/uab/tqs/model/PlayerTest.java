package cat.uab.tqs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testConstructor() {
        Player p = new Player();

        // Caixa Negra: Partició Equivalent (mà inicial creada correctament)
        assertNotNull(p.getHand());

        // Caixa Negra: Partició Equivalent (mà inicial buida)
        assertEquals(0, p.getHand().getCards().size());

        // Caixa Negra: Partició Equivalent (estat inicial no és standing)
        assertFalse(p.isStanding());
    }

    @Test
    void testHit() {
        Player p = new Player();

        // Caixa Negra: Partició Equivalent (afegir carta quan no està en stand)
        p.hit(new Card("Hearts", "10"));
        assertEquals(1, p.getHand().getCards().size());
        assertEquals("10", p.getHand().getCards().get(0).getRank());

        // Caixa Negra: Partició Equivalent (afegir diverses cartes consecutives)
        p.hit(new Card("Clubs", "5"));
        p.hit(new Card("Spades", "2"));
        assertEquals(3, p.getHand().getCards().size());

        // Caixa Negra: Valor Límit/Frontera (no pot afegir cartes després de stand)
        p.stand();
        p.hit(new Card("Diamonds", "9"));
        assertEquals(3, p.getHand().getCards().size());

        // Caixa Negra: Partició No Vàlida (no permet cartes null)
        assertThrows(AssertionError.class, () -> p.hit(null));
    }

    @Test
    void testStand() {
        Player p = new Player();

        // Caixa Negra: Partició Equivalent (stand marca l'estat)
        p.stand();
        assertTrue(p.isStanding());

        // Caixa Negra: Valor Límit (stand idempotent, diverses crides)
        p.stand();
        assertTrue(p.isStanding());

        // Caixa Negra: Valor Límit/Frontera (després de stand no es poden afegir cartes)
        p.hit(new Card("Spades", "9"));
        assertEquals(0, p.getHand().getCards().size());
    }

    @Test
    void testReset() {
        Player p = new Player();

        // Setup: preparem estat amb cartes i en stand
        p.hit(new Card("Clubs", "3"));
        p.hit(new Card("Hearts", "7"));
        p.stand();

        // Caixa Negra: Partició Equivalent (reset esborra cartes i estat stand)
        p.reset();
        assertEquals(0, p.getHand().getCards().size());
        assertFalse(p.isStanding());

        // Caixa Negra: Valor Límit/Frontera (després de reset pot tornar a robar)
        p.hit(new Card("Diamonds", "4"));
        assertEquals(1, p.getHand().getCards().size());
        assertFalse(p.isStanding());

        // Caixa Negra: Valor Límit (reset idempotent; múltiples crides seguides)
        p.reset();
        p.reset();
        assertEquals(0, p.getHand().getCards().size());
        assertFalse(p.isStanding());
    }
}
