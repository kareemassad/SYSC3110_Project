package src;

public class Card {

    public enum Color {RED, GREEN, BLUE, YELLOW, WILD}
    public enum Type {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_ONE, WILD_DRAW_TWO}
    private Color color;
    private Type type;
    private boolean used = false;

    public Card(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return this.color;
    }

    public Type getType() {
        return this.type;
    }

    public boolean getUsed() { return this.used; }

    public void setColor(Color color) { this.color = color; }

    public void setType(Type type) { this.type = type; }

    public void setUsed() { this.used = true; }

    @Override
    public String toString() {
        return color + " " + type;
    }
}