package controller;

import model.Card;
import model.Player;
import model.UnoModel;

import java.util.EventObject;

public class UnoEvent extends EventObject {
    private UnoModel.Status status;
    private Player player;
    private Card card;
    private int penaltyCards;
    private Card chosenColor;

    public UnoEvent(Object source, UnoModel.Status status, Player player, Card card) {
        super(source);
        this.status = status;
        this.player = player;
        this.card = card;
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

    public Card getChosenColor(){ return chosenColor;}

}
