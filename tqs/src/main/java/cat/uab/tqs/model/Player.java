package cat.uab.tqs.model;

public class Player {

    private Hand hand; // baralla
    private boolean standing; // es planta (no agafa mes cartes)

    private boolean invariant() {
        return hand != null;
    }

    public Player() {
        hand = new Hand();
        standing = false;

        assert invariant();
        assert !standing;
    }

    public Hand getHand() {
        assert invariant();
        return hand;
    }

    public boolean isStanding() {
        assert invariant();
        return standing;
    }

    public void hit(Card c) {
        assert invariant();
        //precond
        assert c != null;

        if (!standing) {
            hand.addCard(c);
        }

        assert invariant();
    }

    public void stand() {

        assert invariant();

        standing = true;

        assert invariant();
    }

    public void reset() {

        assert invariant();
        
        hand = new Hand();
        standing = false;

        assert invariant();

        //postcond
        assert hand.getCards().isEmpty();
        assert !standing;

    }
}
