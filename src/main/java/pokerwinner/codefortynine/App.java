package pokerwinner.codefortynine;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Card> deck1 = List.of(
                new Card(CardValue.FIVE, CardSuit.C),
                new Card(CardValue.A, CardSuit.D),
                new Card(CardValue.SEVEN, CardSuit.C),
                new Card(CardValue.SEVEN, CardSuit.H),
                new Card(CardValue.SEVEN, CardSuit.D)
        );
        List<Card> deck2 = List.of(
                new Card(CardValue.J, CardSuit.C),
                new Card(CardValue.J, CardSuit.H),
                new Card(CardValue.J, CardSuit.S),
                new Card(CardValue.A, CardSuit.H),
                new Card(CardValue.A, CardSuit.C)
        );
        Hand hand1 = new Hand(deck1);
        Hand hand2 = new Hand(deck2);

        HandEvaluator evaluator = new HandEvaluator();

        evaluator.evaluateHands(hand1, hand2);
    }
}
