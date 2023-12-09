package test;
import model.Card;
import model.Player;
import model.UnoModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

public class UnoModelTest {
    UnoModel unoModel;

    @Before
    public void setUp() {
        unoModel = new UnoModel();
    }

    @Test
    public void testAddPlayers() {
        unoModel.addPlayers("Player1");
        assertEquals(1, unoModel.getPlayers().size());
        assertEquals("Player1", unoModel.getPlayers().get(0).getName());
    }

    @Test
    public void testDrawCardForPlayer() {
        unoModel.addPlayers("Player1");
        unoModel.dealInitialCards();

        int initialSize = unoModel.getCurrentPlayer().getSize();
        unoModel.drawCardForPlayer();
        assertEquals(initialSize + 1, unoModel.getCurrentPlayer().getSize());
    }

    @Test
    public void testIsPlayable() {
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.FIVE));

        Card playableCard = new Card(Card.Color.RED, Card.Type.THREE);
        Card nonPlayableCard = new Card(Card.Color.BLUE, Card.Type.THREE);

        assertTrue(unoModel.isPlayable(playableCard));
        assertFalse(unoModel.isPlayable(nonPlayableCard));
    }

    @Test
    public void testNextPlayer() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.addPlayers("Player3");

        Player initialPlayer = unoModel.getCurrentPlayer();
        unoModel.nextPlayer();
        assertNotEquals(initialPlayer, unoModel.getCurrentPlayer());
    }

    @Test
    public void testPlayTurn() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.FIVE));

        int initialSize = unoModel.getCurrentPlayer().getSize();
        unoModel.playTurn(0);
        assertEquals(initialSize - 1, unoModel.getCurrentPlayer().getSize());
    }

    @Test
    public void testExecuteSpecialCardAction() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.WILD));

        unoModel.executeSpecialCardAction(new Card(Card.Color.RED, Card.Type.WILD));
        assertNotNull(unoModel.getTopCard().getColor());
    }

    @Test
    public void testDrawUntilColor() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.FIVE));

        unoModel.getCurrentPlayer().addCard(new Card(Card.Color.BLUE, Card.Type.THREE));
        unoModel.drawUntilColor(unoModel.getNextPlayer());
        assertEquals(Card.Color.RED, unoModel.getTopCard().getColor());
    }

    @Test
    public void testCheckWinCondition() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();

        unoModel.getCurrentPlayer().removeAllCards();
        unoModel.checkWinCondition();
        assertEquals(UnoModel.Status.PLAYER_WON, unoModel.getStatus());
    }

    @Test
    public void testExecuteSpecialCardAction_DrawFive() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.DRAW_FIVE));

        unoModel.executeSpecialCardAction(new Card(Card.Color.RED, Card.Type.DRAW_FIVE));
        int nextPlayerCardCount = unoModel.getNextPlayer().getSize();
        assertEquals(5, nextPlayerCardCount);
    }

    @Test
    public void testExecuteSpecialCardAction_SkipEveryone() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.addPlayers("Player3");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.SKIP_EVERYONE));

        unoModel.executeSpecialCardAction(new Card(Card.Color.RED, Card.Type.SKIP_EVERYONE));
        assertEquals(unoModel.getPlayers().get(0), unoModel.getCurrentPlayer());
    }

    @Test
    public void testSetWildCardColor() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.WILD, Card.Type.WILD));

        unoModel.setWildCardColor(Card.Color.BLUE);
        assertEquals(Card.Color.BLUE, unoModel.getTopCard().getColor());
    }

    @Test
    public void testCountScore() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.FIVE));

        Player winningPlayer = unoModel.getPlayers().get(0);
        unoModel.countScore(winningPlayer);

        assertEquals(0, winningPlayer.getScore());
        assertEquals(0, winningPlayer.getTotalScore());
    }

    @Test
    public void testGetScore() {
        unoModel.addPlayers("Player1");
        unoModel.addPlayers("Player2");
        unoModel.dealInitialCards();
        unoModel.setTopCard(new Card(Card.Color.RED, Card.Type.FIVE));

        Player player = unoModel.getPlayers().get(0);
        unoModel.countScore(player);

        assertEquals(0, unoModel.getScore(player));
    }

    @Test
    public void testSaveGame() {
        // Assuming you have a suitable file name for testing
        String fileName = "testSaveGame.ser";

        unoModel.saveGame(fileName);

        // Assertions to verify that the saveGame method works correctly
        File savedFile = new File(fileName);
        assertTrue(savedFile.exists());
        // Add more assertions based on the expected behavior of saveGame()
    }

    @Test
    public void testLoadGame() {
        // Assuming you have a suitable file name for testing
        String fileName = "testSaveGame.ser";

        // Save the game first to have something to load
        unoModel.saveGame(fileName);

        // Load the game and compare states or check if the loaded game is not null
        UnoModel loadedGame = new UnoModel();
        loadedGame.loadGame(fileName);

        // Assertions to verify that the loadGame method works correctly
        assertNotNull(loadedGame);
        assertEquals(unoModel.getStatus(), loadedGame.getStatus());
        assertEquals(unoModel.getCurrentPlayer().getSize(), loadedGame.getCurrentPlayer().getSize());
        // Add more assertions based on the expected behavior of loadGame()
    }

}
