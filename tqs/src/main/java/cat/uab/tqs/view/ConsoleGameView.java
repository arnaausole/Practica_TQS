package cat.uab.tqs.view;

import cat.uab.tqs.model.*;

public class ConsoleGameView implements GameView {

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void showCard(Player player, Card c) {
        System.out.println("Card: " + c.getRank() + " of " + c.getSuit());
    }

    @Override
    public void updateScores(int playerValue, int dealerValue) {
        System.out.println("Player: " + playerValue + ", Dealer: " + dealerValue);
    }
}
