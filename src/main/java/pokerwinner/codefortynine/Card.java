package pokerwinner.codefortynine;

enum CardSuit {
    C,
    D,
    H,
    S
}

enum CardValue {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    J,
    Q,
    K,
    A
}

public class Card {
    private CardValue value;
    private CardSuit suit;

    public Card(CardValue value, CardSuit suit) {
        this.setValue(value);
        this.setSuit(suit);
    }

    public int getOrdinal() {
        return this.getValue().ordinal();
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }
}
