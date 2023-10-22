package src;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    Player player;
    Card c1, c2, number, drawTwo, reverse, wild ;

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

    @Test
    public void testPlayerDetails(){
        assertEquals("Mehedi",player.getName());
        assert player.getScore() == 0;
    }

    @Test
    public void testDrawCards(){
        player.addCards(c1);
        player.addCards(c2);

        assertEquals(2,player.getSize());
        assertEquals(c1,player.getCard(0));
        assertEquals(c2,player.getCard(1));
    }

    @Test
    public void testDropCards(){
        player.addCards(c1);
        player.addCards(c2);

        player.removeCards(0);

        assertEquals(1,player.getSize());
        assertEquals(c2,player.getCard(0));
    }

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