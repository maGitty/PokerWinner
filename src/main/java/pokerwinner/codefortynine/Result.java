package pokerwinner.codefortynine;

import java.util.Comparator;
import java.util.List;

public class Result implements Comparable<Result> {
    HandRank rank;
    List<Card> cards;
    public Result(HandRank rank, List<Card> cards) {
        this.rank = rank;
        this.cards = cards;
    }

    @Override
    public int compareTo(Result o) {
        if (this.rank.compareTo(o.rank) != 0) return this.rank.compareTo(o.rank);

        return 0;
    }
}
