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

        // mides de les mans
        assertEquals(2, controller.getPlayer().getHand().getCards().size());
        assertEquals(2, controller.getDealer().getHand().getCards().size());

        // scores > 0
        assertTrue(view.lastPlayerScore > 0);
        assertTrue(view.lastDealerScore > 0);

        // el dealer ha de quedar amagat al començar la partida
        assertTrue(view.dealerHidden);

        // msg inicial correcte
        assertEquals("New game started. Your turn!", view.lastMessage);
    }

    @Test
    void testPlayerHit() {
        
        controller.startGame();
        int before = controller.getPlayer().getHand().getCards().size();

        // Cas 1: Hit normal
        controller.playerHit();

        assertEquals(before + 1, controller.getPlayer().getHand().getCards().size());
        assertNotNull(view.lastShownCard);

        // dealer ha de tenir 1 carta amagada
        assertTrue(view.dealerHidden);

        // Cas 2: Si player fa blackjack natural, no pot fer hit

        //reset
        MockGameView view = new MockGameView();
        GameController controller = new GameController(view, new Deck());

        // blackjack natural (A + 10)
        controller.getPlayer().getHand().addCard(new Card("Hearts", "A"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "K"));
        int beforeBJ = controller.getPlayer().getHand().getCards().size();  // = 2

        controller.playerHit();

        // no afegir cap carta nova
        assertEquals(beforeBJ, controller.getPlayer().getHand().getCards().size());
        // i no sha de cridar showCard
        assertNull(view.lastShownCard);

    }

    @Test
    void testPlayerStand() {

        controller.startGame();

        // abans de plantarse, la carta del dealer ha destar oculta
        assertTrue(view.dealerHidden);

        // Cas 1: el jugador es planta

        controller.playerStand();
        assertTrue(controller.getPlayer().isStanding());

        // Cas 2: el dealer ha jugat i el seu valor és >= 17
        assertTrue(controller.getDealer().getHand().getValue() >= 17);

        //dps de plantarse la carta del dealer es descobreix
        assertFalse(view.dealerHidden);

        assertTrue(view.lastPlayerScore > 0);
        assertTrue(view.lastDealerScore > 0);
    }

    @Test
    void testDetermineWinner() {

        // Decision + condition coverage:
        //
        //  boolean playerBJ = player.getHand().isBlackjack();
        //  boolean dealerBJ = dealer.getHand().isBlackjack();
        //
        //  if (playerBJ && dealerBJ)           -> "Tie. Both have blackjack."
        //  else if (playerBJ)                  -> "Player wins with blackjack."
        //  else if (dealerBJ)                  -> "Dealer wins with blackjack."
        //  else if (player.isBust())           -> "Dealer wins."
        //  else if (dealer.isBust())           -> "Player wins."
        //  else if (p > d)                     -> "Player wins."
        //  else if (p < d)                     -> "Dealer wins."
        //  else                                -> "Tie."
        //
        // Cobrim tots els camins amb 8 casos

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

        // fem clear de les baralles fetes per startGame, per posarho nosaltres manualment
        controller.getPlayer().reset();
        controller.getDealer().reset();

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

        // fem clear de les baralles fetes per startGame, per posarho nosaltres manualment
        controller.getPlayer().reset();
        controller.getDealer().reset();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "8"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "8")); // 16

        controller.getDealer().getHand().addCard(new Card("Spades", "10"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "9")); // 19

        controller.determineWinner();
        assertEquals("Dealer wins.", view.lastMessage);


    
        // Cas 5: Empat

        controller.startGame();

        // fem clear de les baralles fetes per startGame, per posarho nosaltres manualment
        controller.getPlayer().reset();
        controller.getDealer().reset();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "10"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "9")); // 19

        controller.getDealer().getHand().addCard(new Card("Spades", "10"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "9")); // 19

        controller.determineWinner();
        assertEquals("Tie.", view.lastMessage);


        // casos especials: blackjack natural

        // Cas 6: tots dos tenen blackjack --> empat
        controller.startGame();
        controller.getPlayer().reset();
        controller.getDealer().reset();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "A"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "K"));     // 21, 2 cartes

        controller.getDealer().getHand().addCard(new Card("Spades", "A"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "Q")); // 21, 2 cartes

        controller.determineWinner();
        assertEquals("Tie. Both have blackjack.", view.lastMessage);

        // Cas 7: nomes el player te blackjack --> player guanya amb blackjack
        controller.startGame();
        controller.getPlayer().reset();
        controller.getDealer().reset();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "A"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "K"));   // blackjack

        controller.getDealer().getHand().addCard(new Card("Spades", "9"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "9"));// 18

        controller.determineWinner();
        assertEquals("Player wins with blackjack.", view.lastMessage);

        // Cas 8: nomes el dealer te blackjack --> dealer guanya amb blackjack
        controller.startGame();
        controller.getPlayer().reset();
        controller.getDealer().reset();

        controller.getPlayer().getHand().addCard(new Card("Hearts", "9"));
        controller.getPlayer().getHand().addCard(new Card("Clubs", "9"));   // 18

        controller.getDealer().getHand().addCard(new Card("Spades", "A"));
        controller.getDealer().getHand().addCard(new Card("Diamonds", "K")); // blackjack

        controller.determineWinner();
        assertEquals("Dealer wins with blackjack.", view.lastMessage);
    }

    
}
