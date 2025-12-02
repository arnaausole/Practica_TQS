package cat.uab.tqs.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.uab.tqs.mocks.MockGameView;
import cat.uab.tqs.model.Deck;

public class GameControllerTest {

    MockGameView view;
    Deck deck;
    GameController controller;

    @BeforeEach
    void setUp()
    {
        view = new MockGameView();
        deck = new Deck();
        controller = new GameController(view, deck);
    }

    @Test
    void testStartGame() {

        controller.startGame();

        assertEquals(2, controller.getPlayer().getHand().getCards().size());
        assertEquals(2, controller.getDealer().getHand().getCards().size());
        assertTrue(view.lastPlayerScore > 0);
        assertTrue(view.lastDealerScore > 0);
    }

    @Test
    void testPlayerHit() {
        
        controller.startGame();
        int before = controller.getPlayer().getHand().getCards().size();

        controller.playerHit();

        assertEquals(before + 1, controller.getPlayer().getHand().getCards().size());
        assertNotNull(view.lastShownCard);
    }

    @Test
    void testPlayerStand() {

        controller.startGame();

        controller.playerStand();

        // el jugador ha destar en mode stand
        assertTrue(controller.getPlayer().isStanding());

        // el dealer ha dhaver jugat (>=17)
        assertTrue(controller.getDealer().getHand().getValue() >= 17);

    
        assertTrue(view.lastPlayerScore > 0);
        assertTrue(view.lastDealerScore > 0);
    }


    
}
