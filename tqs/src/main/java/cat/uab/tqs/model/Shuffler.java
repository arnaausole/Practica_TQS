package cat.uab.tqs.model;

import java.util.List;

// Interfície per a diferents estratègies de barrejament de cartes
public interface Shuffler {
    public void shuffle(List<Card> cards);

}
