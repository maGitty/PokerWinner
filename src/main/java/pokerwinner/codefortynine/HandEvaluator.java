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

class PrintWinner {
    static void print(int winner) {
        if (winner == 0) {
            System.out.println("Split pot!");
        }
        System.out.printf("Hand %d wins!%n", winner);
    }
}

public class HandEvaluator {
    public HandEvaluator() {
    }

    public Result rankHand(Hand hand) {
        Map<CardValue, List<Card>> valueGroup = hand.getCards().stream().collect(Collectors.groupingBy(Card::getValue));
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

        Map<CardSuit, List<Card>> suitGroup = hand.getCards().stream().collect(Collectors.groupingBy(Card::getSuit));
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
                return new Result(HandRank.FULL, longestValueGroup.getValue());
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
            return new Result(HandRank.HIGH, hand.getCards());
        }
    }

    public void evaluateHands(Hand hand1, Hand hand2) {
        Result rank1 = rankHand(hand1);
        Result rank2 = rankHand(hand2);

        if (rank1.compareTo(rank2) > 0) { // Hand 1 has higher rank
            PrintWinner.print(1);
        } else if (rank1.compareTo(rank2) < 0) { // Hand 2 has higher rank
            PrintWinner.print(2);
        } else { // Both hands have same rank
            // For hands with straight, just use the highest card for comparison
            if (rank1.getRank() == HandRank.STRAIGHT_FLUSH || rank1.getRank() == HandRank.STRAIGHT) {
                if (hand1.highest() > hand2.highest()) {
                    PrintWinner.print(1);
                } else if (hand1.highest() < hand2.highest()) {
                    PrintWinner.print(2);
                } else {
                    PrintWinner.print(0);
                }
            // For hands with quadruple or full house,
            } else if (rank1.getRank() == HandRank.FOUR ||
                    rank1.getRank() == HandRank.FULL ||
                    rank1.getRank() == HandRank.THREE) {
                if (hand1.sum() > hand2.sum()) {
                    PrintWinner.print(1);
                } else if (hand1.sum() < hand2.sum()) {
                    PrintWinner.print(2);
                } else {
                    PrintWinner.print(0);
                }
            // For hands with pairs
            } else if (rank1.getRank() == HandRank.PAIR || rank1.getRank() == HandRank.PAIR2) {
                List<Card> notPair1 = new ArrayList<>(hand1.getCards());
                for (Card card : rank1.getCards()) notPair1.remove(card);
                int sum1 = notPair1.stream().mapToInt(Card::getOrdinal).sum();

                List<Card> notPair2 = new ArrayList<>(hand2.getCards());
                for (Card card : rank2.getCards()) notPair2.remove(card);
                int sum2 = notPair2.stream().mapToInt(Card::getOrdinal).sum();

                if (sum1 > sum2) {
                    PrintWinner.print(1);
                } else if (sum1 < sum2) {
                    PrintWinner.print(2);
                } else {
                    PrintWinner.print(0);
                }
            // For hands with flush or high card
            } else {
                for (int i = hand1.getCards().size() - 1; i >= 0; i--) {
                    if (hand1.getCards().get(i).getOrdinal() > hand2.getCards().get(i).getOrdinal()) {
                        PrintWinner.print(1);
                    } else if (hand1.getCards().get(i).getOrdinal() < hand2.getCards().get(i).getOrdinal()) {
                        PrintWinner.print(2);
                    }
                }
                PrintWinner.print(0);
            }
        }
    }

    public List<Card> longestConsecutive(Hand hand) {
        List<List<Card>> aggregated = new ArrayList<>();
        List<Card> currentList = null;
        Card lastCard = null;

        for (Card card: hand.getCards()) {
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
