import java.util.EventObject;

public class UnoEvent extends EventObject {
    private UnoModel.Status status;
    private Player player;
    private Card card;
    private int penaltyCards;
    private Card chosenColor;

    public UnoEvent(Object source, UnoModel.Status status) {
        super(source);
        this.status = status;
    }

    public UnoEvent(Object source, UnoModel.Status status, Player player) {
        this(source, status);
        this.player = player;
    }

    public UnoEvent(Object source, UnoModel.Status status, Player player, Card card) {
        this(source, status, player);
        this.card = card;
    }

    public UnoEvent(Object source, UnoModel.Status status, Player player, int penaltyCards) {
        this(source, status, player);
        this.penaltyCards = penaltyCards;
    }

    public UnoEvent(Object source, UnoModel.Status status, Player player, Card card, Card chosenColor) {
        this(source, status, player, card);
        this.chosenColor = chosenColor;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public int getPenaltyCards() {
        return penaltyCards;
    }

}
