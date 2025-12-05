package cat.uab.tqs.model;

// Classe que representa el Dealer del joc
// Reparteix cartes al jugador i juga segons les regles del blackjack
public class Dealer {

    private Hand hand;

    // Invariant per aplicar DByC
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

    // El mètode play defineix el comportament del dealer durant la seva tornada
    public void play(Deck deck) {

         assert invariant();
         //precond
         assert deck != null;

        // El dealer agafa una carta mentre la seva mà tingui un valor menor a 17
        // o fins que el deck estigui buit
        while (hand.getValue() < 17 && !deck.isEmpty()) {

            Card c = deck.drawCard();
            hand.addCard(c);
        }

        //postcond
        assert hand.getValue() >= 17 || deck.isEmpty();
        assert invariant();
        
    }

    // Reseteja la mà del dealer per a una nova partida
    public void reset() {

        assert invariant();


        hand = new Hand();

        assert invariant();

        //postcond
        assert hand.getCards().isEmpty();

    }


}
