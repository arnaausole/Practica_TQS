package cat.uab.tqs.mocks;

import cat.uab.tqs.model.*;
import cat.uab.tqs.view.GameView;

// aquest mock imita la funcionalitat de ConsoleGameView,
// en contes de mostrar els missatges per consola, sels guarda en variables
// per poder realitzar correctament els testos
public class MockGameView implements GameView {

    public String lastMessage = "";
    public int lastPlayerScore = -1;
    public int lastDealerScore = -1;
    public Card lastShownCard = null;

    @Override
    public void showMessage(String msg) {
        lastMessage = msg;
    }

    @Override
    public void showCard(Player player, Card c) {
        lastShownCard = c;
    }

    @Override
    public void updateScores(int playerValue, int dealerValue) {
        lastPlayerScore = playerValue;
        lastDealerScore = dealerValue;
    }
}
