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
        this.random = new Random();
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
        deck.clear(); // Clear existing deck
        Card.Color[] setColor = Card.Color.values();
        for (int i = 0; i < 4; i++) {
            Card.Color color = setColor[i];
            if (color != Card.Color.WILD) {
                // Add one 0 card for each color
                deck.add(new Card(color, Card.Type.ONE));

                // Add two of each number card for each color
                for (Card.Type type : Card.Type.values()) {
                    if (type != Card.Type.ONE && type.ordinal() <= Card.Type.NINE.ordinal()) {
                        deck.add(new Card(color, type));
                        deck.add(new Card(color, type));
                    }
                }
                // Add two of each special card for each color
                deck.add(new Card(color, Card.Type.SKIP));
                deck.add(new Card(color, Card.Type.SKIP));
                deck.add(new Card(color, Card.Type.REVERSE));
                deck.add(new Card(color, Card.Type.REVERSE));
                deck.add(new Card(color, Card.Type.DRAW_ONE));
                deck.add(new Card(color, Card.Type.DRAW_ONE));
            }
        }
        // Add Wild cards (4 of each)
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(Card.Color.WILD, Card.Type.WILD));
            deck.add(new Card(Card.Color.WILD, Card.Type.WILD_DRAW_TWO));
        }
        shuffle();
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle(){
        Collections.shuffle(deck, random);
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

    public void flipDeck(boolean flipped){
        for (Card card: deck){
            if(card.getColor().equals(Card.Color.RED)){
                card.setColor(Card.Color.PINK);
            }
        }
    }
}
