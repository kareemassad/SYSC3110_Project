package src;
public class Card {
    private final String color;
    private final String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }
    public String getColor() {
        return color;
    }
    public String getValue() {
        return value;
    }
    public String toString() {
        return color + " " + value;
    }
}