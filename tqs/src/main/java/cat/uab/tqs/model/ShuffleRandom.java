package cat.uab.tqs.model;

import java.util.Collections;
import java.util.List;

public class ShuffleRandom implements Shuffler {
    @Override
    public void shuffle(List<Card> cards) {
        Collections.shuffle(cards);
}
