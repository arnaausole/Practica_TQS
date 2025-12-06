package cat.uab.tqs.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.uab.tqs.mocks.MockGameView;
import cat.uab.tqs.mocks.MockDeck;
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

        // startGame amb MockDeck --> cartes deterministes
        MockGameView view2 = new MockGameView();
        MockDeck mockDeck = new MockDeck();

        mockDeck.setCards(
            // ordre: jugador1, dealer1, jugador2, dealer2
            new Card("Hearts",   "A"),  // P1
            new Card("Clubs",    "K"),  // P2
            new Card("Spades",   "9"),  // D1
            new Card("Diamonds", "8")   // D2
        );

        GameController controller2 = new GameController(view2, mockDeck);
        controller2.startGame();

        // comprovem que les cartes inicials venen del MockDeck
        assertEquals("A", controller2.getPlayer().getHand().getCards().get(0).getRank());
        assertEquals("Hearts", controller2.getPlayer().getHand().getCards().get(0).getSuit());

        assertEquals("9", controller2.getDealer().getHand().getCards().get(0).getRank());
        assertEquals("Spades", controller2.getDealer().getHand().getCards().get(0).getSuit());

        // el dealer segueix amagat i la vista sactualitza
        assertTrue(view2.dealerHidden);
        assertTrue(view2.lastPlayerScore > 0);
        assertTrue(view2.lastDealerScore > 0);
    }

    @Test
    void testPlayerHit() {
        
        controller.startGame();
        int before = controller.getPlayer().getHand().getCards().size();

        assertTrue(view.dealerHidden);

        // Cas 1: Hit normal
        controller.playerHit();

        assertEquals(before + 1, controller.getPlayer().getHand().getCards().size());
        assertNotNull(view.lastShownCard);

        
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

        // Cas 3: Baralla buida

        MockDeck mockDeck = new MockDeck();
        
        GameController controllerEmpty = new GameController(view, mockDeck);

        // donem cartes al jugador manualment pq tingui punts i pugui demanar
        controllerEmpty.getPlayer().getHand().addCard(new Card("Hearts", "2"));
        controllerEmpty.getPlayer().getHand().addCard(new Card("Clubs", "3"));

        controllerEmpty.playerHit();

        // verifica que salta el missatge derror i la ma no canvia de mida
        assertEquals("No more cards in the deck.", view.lastMessage);
        assertEquals(2, controllerEmpty.getPlayer().getHand().getCards().size());

        assertNull(view.lastShownCard);

        // Cas 4: Hit amb MockDeck determinista
        MockGameView view2 = new MockGameView();
        MockDeck mockDeck2 = new MockDeck();
        mockDeck2.setCards(
            new Card("Hearts", "5")
        );

        GameController controller2 = new GameController(view2, mockDeck2);

        controller2.getPlayer().getHand().addCard(new Card("Clubs",  "2"));
        controller2.getPlayer().getHand().addCard(new Card("Spades", "3"));

        int beforeMock = controller2.getPlayer().getHand().getCards().size();
        controller2.playerHit();

        // ha dhaber afegir nomes 1 carta
        assertEquals(beforeMock + 1, controller2.getPlayer().getHand().getCards().size());
        // la carta mostrada a la vista ha de ser la del MockDeck
        assertNotNull(view2.lastShownCard);
        assertEquals("Hearts", view2.lastShownCard.getSuit());
        assertEquals("5",      view2.lastShownCard.getRank());
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

        // Decision + condition coverage + path coverage:
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
