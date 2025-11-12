package cat.uab.tqs.model;

public class Card 
{
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public Integer getNumericValue() {

        return switch (rank) {
            case "J", "Q", "K" -> 10;
            case "A" -> 11; // For now, Ace = 11
            default -> Integer.parseInt(rank);
        };
    }
}