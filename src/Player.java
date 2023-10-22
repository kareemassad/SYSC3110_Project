package src;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> cards;
    private int score;

    public Player(String name) {
        this.name = name;
        cards = new ArrayList<>();
        score = 0;
    }

    public String getName() { return name; }

    public int getScore() { return score; }

    public void updateScore(Card card) {
        if (card.getType() == Card.Type.ONE || card.getType() == Card.Type.TWO || card.getType() == Card.Type.THREE || card.getType() == Card.Type.FOUR || card.getType() == Card.Type.FIVE || card.getType() == Card.Type.SIX || card.getType() == Card.Type.SEVEN || card.getType() == Card.Type.EIGHT || card.getType() == Card.Type.NINE) {
            this.score += (card.getType().ordinal() + 1);
        } else if (card.getType() == Card.Type.DRAW_ONE) {
            this.score += 10;
        } else if (card.getType() == Card.Type.REVERSE || card.getType() == Card.Type.SKIP) {
            this.score += 20;
        } else if (card.getType() == Card.Type.WILD) {
            this.score += 40;
        } else if (card.getType() == Card.Type.WILD_DRAW_TWO) {
            this.score += 50;
        }
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void addCards(Card c) {
        cards.add(c);
    }

    public void removeCards(int i) {
        cards.remove(i);
    }

    public int getSize() {return this.cards.size(); }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Your Score: " + getScore() + "\nYour cards:\n");
        int count = 1;

        for (Card c : cards) {
            if (count == cards.size()) {
                s.append(count).append(". ").append(c);
            } else {
                s.append(count).append(". ").append(c).append("\n");
                count++;
            }
        }

        return s.toString();
    }
}