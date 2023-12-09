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
        this.score += card.getScoreValue();
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
     */
    public void flipCard(int i, boolean flipped){
        if(i>=0 && i<cards.size()){
            Card card = getCard(i);
            card.flipCard(flipped);
        }
    }
    /**
     * This method shows how  many cards a player has.
     */
    public int getSize() {return this.cards.size(); }

    /**
     * This method sets the total game score of the player.
     * @param score
     */
    public void addTotalScore(int score){
        this.totalScore += score;
    }

    public int getTotalScore(){
        return totalScore;
    }
}