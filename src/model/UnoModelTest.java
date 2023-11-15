package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnoModelTest {
    @Test
    public void testAddPlayers() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 3);
        assertEquals(3, unoModel.getPlayers().size());
    }

    @Test
    public void testDrawCardForPlayer() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();
        unoModel.setCurrentPlayer(unoModel.getPlayers().get(0));

        int initialCardCount = unoModel.getCurrentPlayer().getSize();
        unoModel.drawCardForPlayer();

        assertEquals(initialCardCount + 1, unoModel.getCurrentPlayer().getSize());
    }

    @Test
    public void testDealInitialCards() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();

        // Assuming 7 cards are dealt to each player
        for (Player player : unoModel.getPlayers()) {
            assertEquals(7, player.getSize());
        }

        assertNotNull(unoModel.getTopCard());
    }

    @Test
    public void testIsPlayable() {
        UnoModel unoModel = new UnoModel();
        unoModel.dealInitialCards();
        unoModel.setCurrentPlayer(unoModel.getPlayers().get(0));

        Card topCard = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        unoModel.setTopCard(topCard);

        // Test with a card that should be playable
        Card playableCard = new Card(Card.Type.NUMBER, Card.Color.RED, 3);
        assertTrue(unoModel.isPlayable(playableCard));

        // Test with a card that should not be playable
        Card nonPlayableCard = new Card(Card.Type.NUMBER, Card.Color.BLUE, 3);
        assertFalse(unoModel.isPlayable(nonPlayableCard));
    }

    @Test
    public void testNextPlayer() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 3);
        unoModel.setCurrentPlayer(unoModel.getPlayers().get(0));

        unoModel.nextPlayer();

        // Ensure that the next player is the correct one
        assertEquals(unoModel.getPlayers().get(1), unoModel.getCurrentPlayer());
    }

    @Test
    public void testPlayTurn() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();
        unoModel.setCurrentPlayer(unoModel.getPlayers().get(0));

        Card playableCard = new Card(Card.Type.NUMBER, Card.Color.RED, 3);
        unoModel.getCurrentPlayer().addCard(playableCard);

        unoModel.playTurn(0);

        // Ensure that the top card has been updated and the player's card has been removed
        assertEquals(playableCard, unoModel.getTopCard());
        assertEquals(0, unoModel.getCurrentPlayer().getSize());
    }

    @Test
    public void testExecuteSpecialCardAction() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();
        unoModel.setCurrentPlayer(unoModel.getPlayers().get(0));

        // Test reverse card
        Card reverseCard = new Card(Card.Type.REVERSE, Card.Color.RED, 0);
        unoModel.executeSpecialCardAction(reverseCard);
        assertTrue(unoModel.isReversed());

        // Test draw two card
        Card drawTwoCard = new Card(Card.Type.WILD_DRAW_TWO, Card.Color.RED, 0);
        unoModel.executeSpecialCardAction(drawTwoCard);
        assertEquals(unoModel.getPlayers().get(1), unoModel.getCurrentPlayer());

        // Test skip card
        Card skipCard = new Card(Card.Type.SKIP, Card.Color.BLUE, 0);
        unoModel.executeSpecialCardAction(skipCard);
        assertEquals(unoModel.getPlayers().get(0), unoModel.getCurrentPlayer());
    }

    @Test
    public void testCheckWinCondition() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();

        // Simulate a player winning the round
        unoModel.getPlayers().get(1).removeAllCards();

        unoModel.checkWinCondition();

        assertFalse(unoModel.isGameRunning());
        // Add assertions for expected view updates
    }

    @Test
    public void testSetWildCardColor() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();

        Card wildCard = new Card(Card.Type.WILD, null, 0);

        unoModel.setTopCard(wildCard);
        unoModel.setWildCardColor(Card.Color.BLUE);

        assertEquals(Card.Color.BLUE, unoModel.getTopCard().getColor());
        // Add assertions for expected view updates
    }
}