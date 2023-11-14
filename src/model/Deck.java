package model;

import model.Card;

import java.util.*;

/**
 *This is the model.Deck Class. This builds a deck with the required cards and draws them.
 */
public class Deck {

    private List<Card> deck;
    private Random random = new Random();
    public Deck(){
        deck = new ArrayList<>();
        newDeck();
    }

    /**
     * This method shows how a deck functions when cards are drawn or cards are over. It shuffles a new deck and gives
     * out a random card.
     */
    public Card drawCard(){
        //If deck being drawn from has no cards, shuffle a new deck.
        if (deck.isEmpty()){
            newDeck();
            shuffle();
        }
        //Get random card from within deck, check to ensure not null.
        int givenCardIndex = random.nextInt(deck.size());
        return deck.remove(givenCardIndex);
    }

    /**
     * Method for making a deck with cards of every color and type(numbers, special and wild).
     */
    private void newDeck(){
        //Adds cards of each colour for numbers 0-9
        Card.Type[] cardNum = Card.Type.values();
        for (int i = 0; i < 9; i++){
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

    /**
     * Shuffles the deck.
     */
    public void shuffle(){
        Collections.shuffle(deck);
    }

    /**
     * toString method to rearrange text data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Card card : deck){
            sb.append(card).append("\n");
        }
        return sb.toString();
    }

    /**
     * This method returns the size of the deck.
     */
    public int getDeckSize() {
        return deck.size();
    }

    /**
     *Checks if a deck contains a certain card with specific type and color
     */
    public boolean contains(Card.Color color, Card.Type type){
        for (Card c : deck){
            if (c.getColor() == color && c.getType() == type){
                return true;
            }
        }
        return false;
    }
}