package src;
public class Card {

    enum Color {
        RED, GREEN, BLUE, YELLOW, WILD
    }
    enum Value {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO, WILD
    }
    private final Color color;
    private final Value value;

    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }
    public Color getColor() {
        return color;
    }
    public Value getValue() {
        return value;
    }
    public String toString() {
        return color + " " + value;
    }
}