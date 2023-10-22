
/**
 * This is the Card Class. Card class represents the number and special cards along with their colors using Enumeration.
 */
public class Card {

    public enum Color {RED, GREEN, BLUE, YELLOW, WILD}
    public enum Type {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_ONE, WILD, WILD_DRAW_TWO}
    private Color color;
    private Type type;
    private boolean used = false;

    /**
     * Default constructor for Card
     */
    public Card(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public boolean isDrawOne() {
        return type == Type.DRAW_ONE;
    }

    public boolean isDrawTwo() {
        return type == Type.WILD_DRAW_TWO;
    }

    public boolean isReverse() {
        return type == Type.REVERSE;
    }

    public boolean isSkip() {
        return type == Type.SKIP;
    }

    public boolean isWild() {
        return type == Type.WILD;
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
     * It checks if a declared card is used or no.
     * @return
     */
    public boolean isUsed() { return used; }

    /**
     * Default setter for card's color.
     */
    public void setColor(Color color) { this.color = color; }

    /**
     *Default setter for card's type.
     */
    public void setType(Type type) { this.type = type; }

    /**
     * It declares if a card is used or not.
     */
    public void markUsed() { this.used = true; }

    /**
     * toString method to represent the card in readable text format.
     */
    @Override
    public String toString() {
        return color + " " + type;
    }
}