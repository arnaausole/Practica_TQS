package cat.uab.tqs.model;



public class Card {
    private String suit;
    private String rank;

    private boolean invariant() {

        return suit != null && rank != null;
    }

    public Card(String suit, String rank) {

        //precondicions

        assert suit != null;
        assert rank != null;

        this.suit = suit;
        this.rank = rank;

        assert invariant();

    }

    public String getSuit() {
        assert invariant();

        return suit;
    }

    public String getRank() {

        assert invariant();

        return rank;
    }

    public Integer getNumericValue() {

        assert invariant();

        Integer value = switch (rank) {
            case "J", "Q", "K" -> 10;
            case "A" -> 11;
            default -> Integer.parseInt(rank);
        };

        //postcondicio
        assert value != null;
        assert invariant();
        return value;

    }
}