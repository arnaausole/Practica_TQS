package cat.uab.tqs.controller;

import cat.uab.tqs.model.*;
import cat.uab.tqs.view.GameView;

public class GameController {

    private GameView view;
    private Deck deck;
    private Player player;
    private Dealer dealer;

    public GameController(GameView view, Deck deck) {
        this.view = view;
        this.deck = deck;
        this.player = new Player();
        this.dealer = new Dealer();
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void startGame() {

        player.reset();
        dealer.reset();
        deck = new Deck();

        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());

        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());
        view.showMessage("New game started. Your turn!");
    }

    public void playerHit() {

        if (player.isStanding()) {
            return;
        }

        Card c = deck.drawCard();
        if (c == null) {
            view.showMessage("No more cards in the deck.");
            return;
        }

        player.hit(c);
        view.showCard(player, c);
        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());

        if (player.getHand().isBust()) {
            view.showMessage("Player busts.");
        }
    }

    public void playerStand() {

        if (player.isStanding()) {
            return;
        }

        player.stand();
        dealer.play(deck);

        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());
        determineWinner();
    }

    public void determineWinner() {

        int p = player.getHand().getValue();
        int d = dealer.getHand().getValue();

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
    }
}
