package controller;

import model.Card;
import model.Player;
import model.UnoModel;

import java.util.EventObject;

public class UnoEvent extends EventObject {
    private UnoModel.Status status;
    private Player player;
    private Card card;

    /**
     * The constructor for UnoEvent, establishes parameters based on inputs.
     *
     * @param source The source object for the event.
     * @param status The status of the UNO model.
     * @param player The player associated with the event.
     * @param card The card associated with the event.
     */
    public UnoEvent(Object source, UnoModel.Status status, Player player, Card card) {
        super(source);
        this.status = status;
        this.player = player;
        this.card = card;
    }

    /**
     * Returns the player specified in the event.
     * @return The player in the event.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the card specified in the event.
     * @return The card in the event.
     */
    public Card getCard() {
        return card;
    }
}
