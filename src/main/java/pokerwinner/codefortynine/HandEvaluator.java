package pokerwinner.codefortynine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum HandRank {
    HIGH, // High card
    PAIR, // Two cards with same value
    PAIR2, // Two pairs
    THREE, // Three cards with same value
    STRAIGHT, // Five cards with consecutive value (no matter which suit), for two straights high card applies
    FLUSH, // Five cards with same suit, for two flushes high card applies
    FULL, // Three + pair
    FOUR, // Four cards with same value
    STRAIGHT_FLUSH // Five cards with consecutive value and same suit
}

public class HandEvaluator {
    public HandEvaluator() {
    }

    public Result rankHand(Hand hand) {
        Map<CardValue, List<Card>> valueGroup = hand.cards.stream().collect(Collectors.groupingBy(Card::getValue));
        Map.Entry<CardValue, List<Card>> longestValueGroup = null;
        List<List<Card>> pairs = new ArrayList<>();
        for (Map.Entry<CardValue, List<Card>> entry : valueGroup.entrySet()) {
            if (longestValueGroup == null || entry.getValue().size() > longestValueGroup.getValue().size()) {
                longestValueGroup = entry;
            }
            if (entry.getValue().size() == 2) {
                pairs.add(entry.getValue());
            }
        }
        assert longestValueGroup != null;

        Map<CardSuit, List<Card>> suitGroup = hand.cards.stream().collect(Collectors.groupingBy(Card::getSuit));
        Map.Entry<CardSuit, List<Card>> longestSuitGroup = null;
        for (Map.Entry<CardSuit, List<Card>> entry : suitGroup.entrySet()) {
            if (longestSuitGroup == null || entry.getValue().size() > longestSuitGroup.getValue().size()) {
                longestSuitGroup = entry;
            }
        }
        assert longestSuitGroup != null;

        List<Card> longestConsecutive = this.longestConsecutive(hand);

        if (longestConsecutive.size() == 5) {
            if (suitGroup.size() == 1) {
                return new Result(HandRank.STRAIGHT_FLUSH, longestConsecutive);
            } else {
                return new Result(HandRank.STRAIGHT, longestConsecutive);
            }
        } else if (longestValueGroup.getValue().size() == 4) {
            return new Result(HandRank.FOUR, longestValueGroup.getValue());
        } else if (longestValueGroup.getValue().size() == 3) {
            if (pairs.size() == 1) {
                return new Result(HandRank.FULL, Stream.concat(longestValueGroup.getValue().stream(),
                        pairs.get(0).stream()).collect(Collectors.toList()));
            } else {
                return new Result(HandRank.THREE, longestValueGroup.getValue());
            }
        } else if (longestSuitGroup.getValue().size() == 5) {
            return new Result(HandRank.FLUSH, longestSuitGroup.getValue());
        } else if (pairs.size() == 2) {
            return new Result(HandRank.PAIR2, pairs.stream().flatMap(List::stream).collect(Collectors.toList()));
        } else if (pairs.size() == 1) {
            return new Result(HandRank.PAIR, pairs.get(0));
        } else {
            return new Result(HandRank.HIGH, hand.cards);
        }
    }

    public void evaluateHands(Hand hand1, Hand hand2) {
        Result rank1 = rankHand(hand1);
        Result rank2 = rankHand(hand2);

        if (rank1.compareTo(rank2) > 0) {
            System.out.println("Hand 1 wins");
        } else if (rank1.compareTo(rank2) < 0) {
            System.out.println("Hand 2 wins");
        }
    }

    public List<Card> longestConsecutive(Hand hand) {
        List<List<Card>> aggregated = new ArrayList<>();
        List<Card> currentList = null;
        Card lastCard = null;

        for (Card card: hand.cards) {
            if (lastCard == null) {
                currentList = new ArrayList<>();
                lastCard = card;
            }

            int distance = card.getValue().ordinal() - lastCard.getValue().ordinal();
            if (distance == 1) {
                currentList.add(card);
            } else if (distance > 1) {
                aggregated.add(currentList);
                currentList = new ArrayList<>();
                currentList.add(card);
            }

            lastCard = card;
        }

        aggregated.sort(Comparator.comparing(List::size));

        return aggregated.get(aggregated.size() - 1);
    }
}
