package cat.uab.tqs.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.uab.tqs.mocks.MockGameView;
import cat.uab.tqs.model.*;

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

        // Cas 1: el jugador es planta

        controller.playerStand();
        assertTrue(controller.getPlayer().isStanding());

        // Cas 2: el dealer ha jugat i el seu valor és >= 17
        assertTrue(controller.getDealer().getHand().getValue() >= 17);

    
        assertTrue(view.lastPlayerScore > 0);
        assertTrue(view.lastDealerScore > 0);
    }

    @Test
    void testDetermineWinner() {

        // valors limit/frontera

        // Cas 1: Player busts --> Dealer guanya

        controller.startGame();
        controller.getPlayer().getHand().addCard(new Card("Hearts", "K"));
        controller.getPlayer().getHand().addCard(new Card("Hearts", "Q"));
        controller.getPlayer().getHand().addCard(new Card("Hearts", "J")); // segur que passa de 21

        controller.determineWinner();
        assertEquals("Dealer wins.", view.lastMessage);


        // Cas 2: Dealer busts --> Player guanya
        controller.startGame();
        controller.getDealer().getHand().addCard(new Card("Hearts", "K"));
        controller.getDealer().getHand().addCard(new Card("Hearts", "Q"));
        controller.getDealer().getHand().addCard(new Card("Hearts", "J")); // passa de 21

        controller.determineWinner();
        assertEquals("Player wins.", view.lastMessage);


        // Cas 3: Player > Dealer --> Player guanya

        controller.startGame();

        // li donem al player una ma forta hardcodeado
        controller.getPlayer().getHand().addCard(new Card("Hearts", "10"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "9"));

        // al dealer una pitjor tambe hardcodeado
        controller.getDealer().getHand().addCard(new Card("Spades", "8"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "8"));

        controller.determineWinner();
        assertEquals("Player wins.", view.lastMessage);


        // Cas 4: Dealer > Player --> Dealer guanya

        controller.startGame();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "8"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "8")); // 16

        controller.getDealer().getHand().addCard(new Card("Spades", "10"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "9")); // 19

        controller.determineWinner();
        assertEquals("Dealer wins.", view.lastMessage);


    
        // Cas 5: Empat
        
        controller.startGame();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "10"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "9")); // 19

        controller.getDealer().getHand().addCard(new Card("Spades", "10"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "9")); // 19

        controller.determineWinner();
        assertEquals("Tie.", view.lastMessage);
    }




    
}
