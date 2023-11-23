package model;

public class AI extends Player {

    public AI(String name) {
        super(name);
    }

    public void AITurn(UnoModel aiGame) {
        Card topCard = aiGame.getTopCard();
        for (int i = 0; i < getSize(); i++) {
            Card card = getCard(i);
            if (card.getColor() == topCard.getColor() || card.getType() == topCard.getType()) {
                aiGame.playTurn(i);
                return;
            }
        }
        aiGame.drawCardForPlayer();
    }

    public Card AICard(UnoModel aiGame) {
        Card topCard = aiGame.getTopCard();
        for (int i = 0; i < getSize(); i++) {
            Card card = getCard(i);
            if (card.getColor() == topCard.getColor() || card.getType() == topCard.getType()) {
                return card;
            }
        }
        return null;
    }
}