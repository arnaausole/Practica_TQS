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

        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());

        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());

    }

    public void playerHit() {

        Card c = deck.drawCard();
        player.hit(c);
        view.showCard(player, c);

        if (player.getHand().isBust()) {
            view.showMessage("Player busts.");
        }

    }

    public void playerStand() {

        player.stand();
        dealer.play(deck);
        view.updateScores(player.getHand().getValue(), dealer.getHand().getValue());

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
