package cat.uab.tqs.mocks;

import cat.uab.tqs.model.*;
import java.util.List;

public class MockShuffleRandom implements Shuffler {

    List<Card> predefinedOrder = List.of(
        new Card("Hearts", "A"),
        new Card("Diamonds", "K"),
        new Card("Clubs", "Q"),
        new Card("Spades", "J")
        // Add more cards as needed for testing
    );

    @Override
    public void shuffle(List<Card> cards) {
        cards.clear();
        cards.addAll(predefinedOrder);
    }
}

