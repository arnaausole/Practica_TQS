package cat.uab.tqs.view;

import cat.uab.tqs.model.*;

public interface GameView {
    void showMessage(String msg);
    void showCard(Player player, Card c);
    void updateScores(int playerValue, int dealerValue);
}
