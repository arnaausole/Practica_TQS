package cat.uab.tqs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cat.uab.tqs.mocks.MockGameView;
import cat.uab.tqs.model.Deck;

public class GameControllerTest {

    @Test
    void testStartGame() {
        MockGameView view = new MockGameView();
        Deck deck = new Deck();
        GameController controller = new GameController(view, deck);

        controller.startGame();

        assertEquals(2, controller.getPlayer().getHand().getCards().size());
        assertEquals(2, controller.getDealer().getHand().getCards().size());
        assertTrue(view.lastPlayerScore > 0);
        assertTrue(view.lastDealerScore > 0);
    }
    
}
