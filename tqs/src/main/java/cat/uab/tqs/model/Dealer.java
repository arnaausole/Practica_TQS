package cat.uab.tqs.model;

public class Dealer {

    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void play(Deck deck) {

        while (hand.getValue() < 17 && !deck.isEmpty()) {

            Card c = deck.drawCard();
            hand.addCard(c);
        }
        
    }

    public void reset() {
        hand = new Hand();
    }


}
