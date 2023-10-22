package src;
import java.util.ArrayList;
import java.util.Random;

import static java.sql.Types.NULL;

public class Deck {

    private ArrayList<Card> deck;

    public Deck(){
        this.deck = new ArrayList<Card>();
        newDeck();
    }

    private Card drawCard(){
        Random card = new Random();
        Card given = null;
        //If deck being drawn from has no cards, shuffle a new deck.
        if (deck.isEmpty()){
            newDeck();
        }
        int givenCard = card.nextInt(deck.size());
        if (givenCard != NULL) {
            given = deck.get(givenCard);
            deck.remove(givenCard);
        }
        return given;
    }
    private void newDeck(){
        //Adds cards of each colour for numbers 0-9
        Card.Type[] cardNum = Card.Type.values();
        for (int i = 0; i < 10; i++){
            deck.add(new Card(Card.Color.RED, cardNum[i]));
            deck.add(new Card(Card.Color.RED, cardNum[i]));
            deck.add(new Card(Card.Color.GREEN, cardNum[i]));
            deck.add(new Card(Card.Color.GREEN, cardNum[i]));
            deck.add(new Card(Card.Color.BLUE, cardNum[i]));
            deck.add(new Card(Card.Color.BLUE, cardNum[i]));
            deck.add(new Card(Card.Color.YELLOW, cardNum[i]));
            deck.add(new Card(Card.Color.YELLOW, cardNum[i]));
        }
        //Adds special cards (draw one, reverse, skip) for each colour
        for (int i = 0; i < 2; i++){
            deck.add(new Card(Card.Color.RED, Card.Type.DRAW_ONE));
            deck.add(new Card(Card.Color.GREEN, Card.Type.DRAW_ONE));
            deck.add(new Card(Card.Color.BLUE, Card.Type.DRAW_ONE));
            deck.add(new Card(Card.Color.YELLOW, Card.Type.DRAW_ONE));
            deck.add(new Card(Card.Color.RED, Card.Type.REVERSE));
            deck.add(new Card(Card.Color.GREEN, Card.Type.REVERSE));
            deck.add(new Card(Card.Color.BLUE, Card.Type.REVERSE));
            deck.add(new Card(Card.Color.YELLOW, Card.Type.REVERSE));
            deck.add(new Card(Card.Color.RED, Card.Type.SKIP));
            deck.add(new Card(Card.Color.GREEN, Card.Type.SKIP));
            deck.add(new Card(Card.Color.BLUE, Card.Type.SKIP));
            deck.add(new Card(Card.Color.YELLOW, Card.Type.SKIP));
        }
        //Adds wild cards (Wild card and Wild Draw Two)
        for (int i = 0; i < 4; i++){
            deck.add(new Card(Card.Color.WILD, Card.Type.WILD));
            deck.add(new Card(Card.Color.WILD, Card.Type.WILD_DRAW_TWO));
        }
    }
}
