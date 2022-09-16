package pokerwinner.codefortynine;

import java.util.Comparator;
import java.util.List;

public class Result implements Comparable<Result> {
    private HandRank rank;

    private List<Card> cards;
    public Result(HandRank rank, List<Card> cards) {
        this.rank = rank;
        this.cards = cards;
    }

    @Override
    public int compareTo(Result o) {
        if (this.rank.compareTo(o.rank) != 0) return this.rank.compareTo(o.rank);

        return 0;
    }

    public HandRank getRank() {
        return rank;
    }

    public void setRank(HandRank rank) {
        this.rank = rank;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
