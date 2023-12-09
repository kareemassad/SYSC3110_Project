package test;

import model.AI;
import model.Card;
import model.Player;
import model.UnoModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AITest {
    UnoModel game;
    AI aiPlayer;

    @Before
    public void setUp() {
        game = new UnoModel();
        aiPlayer = new AI("AIPlayer");
        Player humanPlayer = new Player("HumanPlayer");

        game.addPlayers(humanPlayer.getName());
        game.addAIPlayer(aiPlayer.getName());
        game.dealInitialCards();
    }

    @Test
    public void testAiPlaysValidCard() {
        Card topCard = new Card(Card.Color.RED, Card.Type.ONE);
        game.setTopCard(topCard);

        Card playableCard = new Card(Card.Color.RED, Card.Type.TWO);
        aiPlayer.addCard(playableCard);

        aiPlayer.AITurn(game);

        assertEquals(playableCard, game.getTopCard());
    }

    @Test
    public void testAiDrawsCardWhenNoValidCard() {
        Card topCard = new Card(Card.Color.RED, Card.Type.ONE);
        game.setTopCard(topCard);

        aiPlayer.removeAllCards();

        int initialHandSize = aiPlayer.getSize();
        aiPlayer.AITurn(game);

        // Assert that AI's hand size has increased
        assertEquals(initialHandSize, aiPlayer.getSize());
    }

    @Test
    public void testAIHandlesWildCardsCorrectly() {
        Card wildCard = new Card(Card.Color.WILD, Card.Type.WILD);
        aiPlayer.addCard(wildCard);

        aiPlayer.AITurn(game);

        Card topCard = game.getTopCard();
        assertTrue(topCard.getType() == Card.Type.WILD && topCard.getColor() != Card.Color.WILD);
    }
}