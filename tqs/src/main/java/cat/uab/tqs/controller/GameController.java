package cat.uab.tqs.controller;

import cat.uab.tqs.model.*;
import cat.uab.tqs.view.GameView;

public class GameController {

    private GameView view;
    private Deck deck;
    private Player player;
    private Dealer dealer;

    private boolean invariant() {

        return view != null && deck != null && player != null && dealer != null;
    }

    public GameController(GameView view) {

        this(view, new Deck());
    }

    public GameController(GameView view, Deck deck) {

        //precond
        assert view != null;
        assert deck != null;


        this.view = view;
        this.deck = deck;
        this.player = new Player();
        this.dealer = new Dealer();

        assert invariant();
    }

    public Player getPlayer() {

        assert invariant();

        return player;
    }

    public Dealer getDealer() {

        assert invariant();
        return dealer;
    }

    public void startGame() {

        assert invariant(); // important

        player.reset();
        dealer.reset();

        //deck = new Deck();

        //precond: per començar el joc be
        assert player.getHand().getCards().isEmpty();
        assert dealer.getHand().getCards().isEmpty();


        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());

        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        //post cond: per jugar be
        assert player.getHand().getCards().size() == 2;
        assert dealer.getHand().getCards().size() == 2;

        // amaguem la carta del dealer (per evitar manipular el joc)
        view.setDealerHidden(true);
        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());
        view.showMessage("New game started. Your turn!");

        assert invariant();
    }

    public void playerHit() {

        assert invariant();

        if (player.isStanding() || player.getHand().isBlackjack()) {
            assert invariant();
            return;
        }

        Card c = deck.drawCard();
        if (c == null) {
            view.showMessage("No more cards in the deck.");
            assert invariant();
            return;
        }

        int oldSize = player.getHand().getCards().size();

        player.hit(c);
        view.showCard(player, c);
        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());

        //postcond

        assert player.getHand().getCards().size() == oldSize + 1;

        if (player.getHand().isBust()) {

            view.setDealerHidden(false);
            view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());
            view.showMessage("Player busts.");
        }

        assert invariant();
    }

    public void playerStand() {

        assert invariant();

        if (player.isStanding()) {

            assert invariant();
            return;
        }

        player.stand();
        dealer.play(deck);

        //mostrem la carta del dealer
        view.setDealerHidden(false);

        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());
        determineWinner();

        //postcond
        assert player.isStanding();
        assert invariant();

    }

    public void determineWinner() {

        assert invariant();

        // si un te 21 amb 3 cartes i laltre te blackjack (A + 10), guanya el que te blackjack natural

        int p = player.getHand().getValue();
        int d = dealer.getHand().getValue();

        boolean playerBJ = player.getHand().isBlackjack();
        boolean dealerBJ = dealer.getHand().isBlackjack();

        // casos de blackjack natural
        if (playerBJ && dealerBJ) {
            view.showMessage("Tie. Both have blackjack.");
            return;
        } else if (playerBJ) {
            view.showMessage("Player wins with blackjack.");
            return;
        } else if (dealerBJ) {
            view.showMessage("Dealer wins with blackjack.");
            return;
        }

        // cas sense blackjack
        if (player.getHand().isBust()) {
            view.showMessage("Dealer wins.");
        }
        else if (dealer.getHand().isBust()) {
            view.showMessage("Player wins.");
        }
        else if (p > d) {
            view.showMessage("Player wins.");
        }
        else if (p < d) {
            view.showMessage("Dealer wins.");
        }
        else {
            view.showMessage("Tie.");
        }

        assert invariant();
    }
}
