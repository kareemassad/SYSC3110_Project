package model;

import java.io.Serializable;

/**
 * This is the model.Card Class. model.Card class represents the number and special cards along with their colors using Enumeration.
 */
public class Card implements Serializable {

    //Conversions: Red->Pink, Green->Purple, Blue->Orange, Yellow->Teal
    public enum Color {RED, GREEN, BLUE, YELLOW, PINK, PURPLE, ORANGE, TEAL, WILD}
    public enum Type {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, SKIP_EVERYONE, FLIP, REVERSE, DRAW_ONE, DRAW_FIVE, WILD, WILD_FLIP, WILD_DRAW_TWO, WILD_DRAW_COLOR}
    private Color color;
    private Type type;

    /**
     * Default constructor for model.Card
     */
    public Card(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Default getter for card's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Default getter for card's type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Default setter for card's color.
     */
    public void setColor(Color color) { this.color = color; }

    /**
     * Default setter for card's type.
     */
    public void setType(Type type) {this.type = type;}

    public void flipCard(Boolean flipped){
        if(flipped){
            switch(type){
                case SKIP_EVERYONE -> setType(Card.Type.SKIP);
                case DRAW_FIVE -> setType(Card.Type.DRAW_ONE);
                case WILD_FLIP -> setType(Card.Type.WILD);
                case WILD_DRAW_COLOR -> setType(Card.Type.WILD_DRAW_TWO);
            }
            switch(color){
                case PINK -> setColor(Card.Color.RED);
                case ORANGE -> setColor(Card.Color.BLUE);
                case PURPLE -> setColor(Card.Color.GREEN);
                case TEAL -> setColor(Card.Color.YELLOW);
            }
        }else{
            switch(type){
                case SKIP -> setType(Card.Type.SKIP_EVERYONE);
                case DRAW_ONE -> setType(Card.Type.DRAW_FIVE);
                case WILD -> setType(Card.Type.WILD_FLIP);
                case WILD_DRAW_TWO -> setType(Card.Type.WILD_DRAW_COLOR);
            }
            switch(color) {
                case RED -> setColor(Card.Color.PINK);
                case BLUE -> setColor(Card.Color.ORANGE);
                case GREEN -> setColor(Card.Color.PURPLE);
                case YELLOW -> setColor(Card.Color.TEAL);
            }
        }
    }
    /**
     * toString method to represent the card in readable text format.
     */
    @Override
    public String toString() {
        return color + " " + type;
    }
}