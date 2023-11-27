package test;

import model.AI;
import model.Card;
import model.Player;
import model.UnoModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AITest {
    private UnoModel game;
    private AI aiPlayer;
    private Player humanPlayer;

    @Before
    public void setUp() throws Exception {
        game = new UnoModel();
        aiPlayer = new AI("AIPlayer");
        humanPlayer = new Player("HumanPlayer");

        game.addPlayers(humanPlayer.getName());
        game.addAIPlayer(aiPlayer.getName());
        game.dealInitialCards();
    }

    @Test
    public void testAiPlaysValidCard(){
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

        // Debugging information
//        System.out.println("Initial hand size: " + initialHandSize);
//        System.out.println("Current hand size: " + aiPlayer.getSize());
//        System.out.println("Top card in game: " + game.getTopCard());

        // Assert that AI's hand size has increased
        assertTrue("AI should have drawn a card", aiPlayer.getSize() > initialHandSize);
    }
    @Test
    public void testAIHandlesWildCardsCorrectly(){
        Card wildCard = new Card(Card.Color.WILD, Card.Type.WILD);
        aiPlayer.addCard(wildCard);

        aiPlayer.AITurn(game);

        Card topCard = game.getTopCard();
        assertTrue(topCard.getType() == Card.Type.WILD && topCard.getColor() != Card.Color.WILD);
    }
}