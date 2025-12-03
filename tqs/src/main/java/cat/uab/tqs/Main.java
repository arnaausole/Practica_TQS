package cat.uab.tqs;

import cat.uab.tqs.controller.GameController;
import cat.uab.tqs.model.Deck;
import cat.uab.tqs.view.GUIView;

public class Main {
    public static void main(String[] args) {

        GUIView view = new GUIView();

        Deck deck = new Deck();

        GameController controller = new GameController(view, deck);

        view.setController(controller);

    }
}
