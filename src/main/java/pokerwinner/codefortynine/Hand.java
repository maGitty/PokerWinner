package pokerwinner.codefortynine;

import java.util.*;
import java.util.stream.Collectors;

public class Hand {
    List<Card> cards;

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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
