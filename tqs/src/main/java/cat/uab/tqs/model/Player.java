package cat.uab.tqs.model;

public class Player {

    private Hand hand; // baralla
    private boolean standing; // es planta (no agafa mes cartes)

    public Player() {
        hand = new Hand();
        standing = false;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean isStanding() {
        return standing;
    }

    public void hit(Card c) {
        if (!standing) {
            hand.addCard(c);
        }
    }

    public void stand() {
        standing = true;
    }

    public void reset() {
        hand = new Hand();
        standing = false;
    }
}
