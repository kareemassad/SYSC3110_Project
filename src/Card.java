package src;

public class Card {

    public enum Color {RED, GREEN, BLUE, YELLOW, WILD}
    public enum Type {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_ONE, WILD, WILD_DRAW_TWO}
    private Color color;
    private Type type;
    private boolean used = false;

    public Card(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public boolean getUsed() { return used; }

    public void setColor(Color color) { this.color = color; }

    public void setType(Type type) { this.type = type; }

    public void setUsed() { this.used = true; }

    @Override
    public String toString() {
        return color + " " + type;
    }
}