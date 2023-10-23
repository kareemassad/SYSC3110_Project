/**
 * This is the Card Class. Card class represents the number and special cards along with their colors using Enumeration.
 */
public class Card {

    public enum Color {RED, GREEN, BLUE, YELLOW, WILD}
    public enum Type {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_ONE, WILD, WILD_DRAW_TWO}
    private Color color;
    private Type type;

    /**
     * Default constructor for Card
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