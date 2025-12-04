package cat.uab.tqs.view;

import cat.uab.tqs.model.*;

public interface GameView {
    void showMessage(String msg);
    void showCard(Player player, Card c);
    void updateScores(int playerValue, int dealerValue);

    // veure si la carta del dealer sha de mostrar o no ( segons regles de blackjack oficials)
    void setDealerHidden(boolean hidden);
}
