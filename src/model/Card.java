package model;

/**
 * This is the model.Card Class. model.Card class represents the number and special cards along with their colors using Enumeration.
 */
public class Card {

    //Conversions: Red->Pink, Green->Purple, Blue->Orange, Yellow->Teal
    public enum Color {RED, GREEN, BLUE, YELLOW, WILD,PINK, PURPLE, ORANGE, TEAL}
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
     * toString method to represent the card in readable text format.
     */
    @Override
    public String toString() {
        return color + " " + type;
    }
}