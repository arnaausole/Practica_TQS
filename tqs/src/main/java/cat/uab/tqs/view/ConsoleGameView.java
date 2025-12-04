package cat.uab.tqs.view;

import cat.uab.tqs.model.*;

public class ConsoleGameView implements GameView {

    private boolean dealerHidden = false;

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void showCard(Player player, Card c) {
        
        System.out.println("Card drawn: " + c.getRank() + " of " + c.getSuit());
    }

    @Override
    public void updateScores(int playerValue, int dealerValue) {

        if (dealerHidden) {
            // si esta ocult no revelem la puntuacio del dealer
            System.out.println("Player: " + playerValue + ", Dealer: [hidden]");
        } else {
            System.out.println("Player: " + playerValue + ", Dealer: " + dealerValue);
        }
    }

    @Override
    public void setDealerHidden(boolean hidden) {
        this.dealerHidden = hidden;
    }
}
