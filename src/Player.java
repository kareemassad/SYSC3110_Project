package src;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> playerHand;

    public Player(String name) {
        this.name = name;
        playerHand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void drawCard(Card c) {
        playerHand.add(c);
    }

    public void playCard(Card c) {
        playerHand.remove(c);
    }
}