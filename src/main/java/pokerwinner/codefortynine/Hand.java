package pokerwinner.codefortynine;

import java.util.*;
import java.util.stream.Collectors;

public class Hand {
    private List<Card> cards;

    public Hand() {
    }

    public Hand(List<Card> cards) {
        cards = new ArrayList<>(cards);
        cards.sort(Comparator.comparing(Card::getValue));
        this.setCards(cards);
    }

    public void addCard(Card card) {
        this.cards.add(card);
        cards.sort(Comparator.comparing(Card::getValue));
    }

    public int highest() {
        return this.cards.get(this.cards.size() - 1).getValue().ordinal();
    }

    public int sum() {
        int sum = 0;
        for (Card card : this.cards) {
            sum += card.getValue().ordinal();
        }
        return sum;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
