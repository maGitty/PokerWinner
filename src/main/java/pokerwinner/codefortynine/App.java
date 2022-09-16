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
                new Card(CardValue.SIX, CardSuit.C)
        );
        List<Card> deck2 = List.of(
                new Card(CardValue.J, CardSuit.C),
                new Card(CardValue.TWO, CardSuit.H),
                new Card(CardValue.J, CardSuit.S)
        );
        Hand hand1 = new Hand(deck1);
        Hand hand2 = new Hand(deck2);

        HandEvaluator evaluator = new HandEvaluator();

        evaluator.evaluateHands(hand1, hand2);
    }
}
