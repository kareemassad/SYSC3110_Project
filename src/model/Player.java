package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    /**
     This is the model.Player class.

     These are the players that get cards from the deck and play the game.
     @attribute name is the name of the model.Player
     @attribute  card is the list of cards a player has on hand.
     @attribute score is the score of the player.
     */
    private String name;
    private List<Card> cards;

    private int totalScore;
    private int score;

/**
 * Default constructor for model.Player
 */
    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.score = 0;
    }

    /**
     * Default getter for name
     */
    public String getName() { return name; }

    /**
     * Default getter for score
     */
    public int getScore() { return score; }

    /**
     * Method to update the score of the player depending on the type of the card
     */
    public void updateScore(Card card) {
        if (card.getType() == Card.Type.ONE || card.getType() == Card.Type.TWO || card.getType() == Card.Type.THREE || card.getType() == Card.Type.FOUR || card.getType() == Card.Type.FIVE || card.getType() == Card.Type.SIX || card.getType() == Card.Type.SEVEN || card.getType() == Card.Type.EIGHT || card.getType() == Card.Type.NINE) {
            score += (card.getType().ordinal() + 1);
        } else if (card.getType() == Card.Type.DRAW_ONE) {
            score += 10;
        } else if (card.getType() == Card.Type.REVERSE || card.getType() == Card.Type.SKIP || card.getType() == Card.Type.DRAW_FIVE || card.getType() == Card.Type.FLIP || card.getType() == Card.Type.WILD_FLIP) {
            score += 20;
        } else if (card.getType() == Card.Type.SKIP_EVERYONE) {
            score += 30;
        } else if (card.getType() == Card.Type.WILD) {
            score += 40;
        } else if (card.getType() == Card.Type.WILD_DRAW_TWO) {
            score += 50;
        } else if (card.getType() == Card.Type.WILD_DRAW_COLOR) {
            score += 60;
        }
    }

    /**
     * This method retrieves card from the player for any given index.
     *
     * @param index the index of the card to be retrieved
     * @return the card at the specified index
     */
    public Card getCard(int index) {
        if(index >= 0 && index < cards.size()){
            return cards.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: ");
        }
    }

    /**
     * This method adds card to the player when card is being drawn.
     */
    public void addCard(Card c) {
        cards.add(c);
    }

    /**
     * This method removes card from the player when card is being played.
     */
    public void removeCard(int i) {
        if(i >=0 && i<cards.size()){
            cards.remove(i);
        } else {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + cards.size());
        }
    }

    public void removeAllCards(){
        cards.clear();
    }

    /**
     * This method flips the given card in hand.
     * @param flipped The state of the card being flipped.
     * @param i The cardIndex of the card to be flipped.
     */
    public void flipCard(int i, boolean flipped){
        if(i>=0 && i<cards.size()){
            Card card = getCard(i);
            card.flipCard(flipped);
        }
    }
    /**
     * This method shows how  many cards a player has.
     * @return The size of the player hand.
     */
    public int getSize() {return this.cards.size(); }

    /**
     * This method sets the total game score of the player.
     * @param score
     */
    public void addTotalScore(int score){
        this.totalScore += score;
    }

    /**
     * This method gets the total game score.
     * @return totalScore The score of the game.
     */
    public int getTotalScore(){
        return totalScore;
    }
}