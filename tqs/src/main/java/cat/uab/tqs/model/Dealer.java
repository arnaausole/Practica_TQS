package cat.uab.tqs.model;

public class Dealer {

    private Hand hand;

    private boolean invariant() {
        return hand != null;
    }


    public Dealer() {
        hand = new Hand();

        assert invariant();
    }

    public Hand getHand() {
        assert invariant();
        return hand;
    }

    public void play(Deck deck) {

         assert invariant();
         //precond
         assert deck != null;


        while (hand.getValue() < 17 && !deck.isEmpty()) {

            Card c = deck.drawCard();
            hand.addCard(c);
        }

        //postcond
        assert hand.getValue() >= 17 || deck.isEmpty();
        assert invariant();
        
    }

    public void reset() {

        assert invariant();


        hand = new Hand();

        assert invariant();

        //postcond
        assert hand.getCards().isEmpty();

    }


}
