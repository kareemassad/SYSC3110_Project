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

        Card topCard = new Card(Card.Color.RED, Card.Type.FIVE);
        unoModel.setTopCard(topCard);

        // Test with a card that should be playable
        Card playableCard = new Card(Card.Color.RED, Card.Type.THREE);
        assertTrue(unoModel.isPlayable(playableCard));

        // Test with a card that should not be playable
        Card nonPlayableCard = new Card(Card.Color.BLUE, Card.Type.THREE);
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

        Card playableCard = new Card(Card.Color.RED, Card.Type.THREE);
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
        Card reverseCard = new Card(Card.Color.RED, Card.Type.REVERSE);
        unoModel.executeSpecialCardAction(reverseCard);
        assertEquals(unoModel.getPlayers().get(1), unoModel.getCurrentPlayer());

        // Test draw two card
        Card drawTwoCard = new Card(Card.Color.RED, Card.Type.WILD_DRAW_TWO);
        unoModel.executeSpecialCardAction(drawTwoCard);
        assertEquals(unoModel.getPlayers().get(1), unoModel.getCurrentPlayer());

        // Test skip card
        Card skipCard = new Card(Card.Color.BLUE, Card.Type.SKIP);
        unoModel.executeSpecialCardAction(skipCard);
        assertEquals(unoModel.getPlayers().get(0), unoModel.getCurrentPlayer());
    }

    @Test
    public void testCheckWinCondition() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();

        // Simulate a player winning the round
        boolean b = unoModel.getPlayers().get(1).getSize() == 0;

        unoModel.checkWinCondition();

        assertEquals(0, unoModel.getPlayers().get(1).getSize());
    }

    @Test
    public void testSetWildCardColor() {
        UnoModel unoModel = new UnoModel();
        unoModel.addPlayers("Player", 2);
        unoModel.dealInitialCards();

        Card wildCard = new Card(Card.Color.WILD, Card.Type.WILD);

        unoModel.setTopCard(wildCard);
        unoModel.setWildCardColor(Card.Color.BLUE);

        assertEquals(Card.Color.BLUE, unoModel.getTopCard().getColor());
    }
}