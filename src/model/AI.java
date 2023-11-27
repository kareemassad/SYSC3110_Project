package model;

import java.util.Random;

public class AI extends Player {

    public AI(String name) {
        super(name);
    }

    public void AITurn(UnoModel aiGame) {
        Card topCard = aiGame.getTopCard();
        boolean played = false;

        for (int i = 0; i < getSize(); i++) {
            Card card = getCard(i);
            if (aiGame.isPlayable(card)) {
                handleWildCard(card);
                aiGame.setTopCard(card);
                removeCard(i);
                aiGame.executeSpecialCardAction(card);
                played=true;
                break;
            }
        }
        if (!played) {
            aiGame.drawCardForPlayer();
        }
    }

    public void handleWildCard(Card card){
        if (card.getType() == Card.Type.WILD || card.getType() == Card.Type.WILD_DRAW_TWO ||
                card.getType() == Card.Type.WILD_FLIP || card.getType() == Card.Type.WILD_DRAW_COLOR) {
            Card.Color[] colors = {Card.Color.RED, Card.Color.BLUE, Card.Color.GREEN, Card.Color.YELLOW};
            Card.Color chosenColor = colors[new Random().nextInt(colors.length)];
            card.setColor(chosenColor);
        }
    }

    public Card AICard(UnoModel aiGame) {
        Card topCard = aiGame.getTopCard();
        for (int i = 0; i < getSize(); i++) {
            Card card = getCard(i);
            if (aiGame.isPlayable(card)) {
                return card;
            }
        }
        return null;
    }

    public Card.Color chooseRandomFlipColor() {
        Card.Color[] flipColors = {Card.Color.PINK, Card.Color.ORANGE, Card.Color.PURPLE, Card.Color.TEAL};
        return flipColors[new Random().nextInt(flipColors.length)];
    }

    public Card.Color chooseRandomColor() {
        Card.Color[] colors = {Card.Color.RED, Card.Color.BLUE, Card.Color.GREEN, Card.Color.YELLOW};
        return colors[new Random().nextInt(colors.length)];
    }
}