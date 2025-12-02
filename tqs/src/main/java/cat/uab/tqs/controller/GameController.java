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
    }

    public void playerHit() {
    }

    public void playerStand() {
    }

    public void determineWinner() {
    }
}
