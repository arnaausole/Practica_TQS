package cat.uab.tqs.mocks;

import cat.uab.tqs.model.*;
import java.util.List;

public class MockShuffleRandom implements Shuffler {

    private final List<Card> predefinedOrder = List.of(
        new Card("Hearts", "A"),
        new Card("Diamonds", "K"),
        new Card("Clubs", "Q"),
        new Card("Spades", "J")
        // afegirem mes per testejar si cal
    );

    @Override
    public void shuffle(List<Card> cards) {
        cards.clear();
        cards.addAll(predefinedOrder);
    }
}

