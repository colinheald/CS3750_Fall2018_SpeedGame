import java.util.*;

public class Deck {

    private List<Card> cards = new ArrayList<Card>();

    Deck(){
        for(CardValue cv : CardValue.values()){
            cards.add(new Card(cv.name(),Suit.CLUB,cv.getValue()));
            cards.add(new Card(cv.name(),Suit.HEART,cv.getValue()));
            cards.add(new Card(cv.name(),Suit.SPADE,cv.getValue()));
            cards.add(new Card(cv.name(),Suit.DIAMOND,cv.getValue()));
        }

        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
    }

    public Card getCard(int index){

        return cards.get(index);
    }

}
