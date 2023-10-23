import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is the test class for testing all the methods in the Player Class
 */
public class PlayerTest {

    Player player;
    Card c1, c2, number, drawTwo, reverse, wild ;

    /**
     * Initial setup for the testing environment.
     */
    @Before
    public void setUp() {
        player = new Player("Mehedi");
        c1 = new Card(Card.Color.RED, Card.Type.ONE);
        c2 = new Card(Card.Color.GREEN, Card.Type.SKIP);
        number = new Card(Card.Color.RED, Card.Type.SIX);
        drawTwo = new Card(Card.Color.GREEN, Card.Type.WILD_DRAW_TWO);
        reverse = new Card(Card.Color.BLUE, Card.Type.REVERSE);
        wild = new Card(Card.Color.WILD, Card.Type.WILD);
    }

    /**
     * Method for testing the cards that are drawn by a player and checking the remaining cards on a player hands.
     */
    @Test
    public void testDrawCards(){
        player.addCard(c1);
        player.addCard(c2);

        assertEquals(2, player.getSize());
        assertEquals(c1,player.getCard(0));
        assertEquals(c2,player.getCard(1));
    }

    /**
     * Method for testing the cards that are played by a player and checking the remaining cards on a player hands.
     */
    @Test
    public void testDropCards(){
        player.addCard(c1);
        player.addCard(c2);

        player.removeCard(0);

        assertEquals(1, player.getSize());
        assertEquals(c2,player.getCard(0));
    }

    /**
     * Method for testing the score of the player after a specific type of card is being played.
     */
    @Test
    public void testPlayerScore() {
        player.updateScore(number);
        assertEquals(6, player.getScore());

        player.updateScore(drawTwo);
        assertEquals(56, player.getScore());

        player.updateScore(reverse);
        assertEquals(76, player.getScore());

        player.updateScore(wild);
        assertEquals(116, player.getScore());
    }
}