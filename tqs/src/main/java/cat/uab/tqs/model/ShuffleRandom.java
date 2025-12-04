package cat.uab.tqs.model;

import java.util.Collections;
import java.util.List;

// Classe que utilitza el mètode de barrejament aleatori
public class ShuffleRandom implements Shuffler {
    @Override
    public void shuffle(List<Card> cards) {
        Collections.shuffle(cards);
    }
}
