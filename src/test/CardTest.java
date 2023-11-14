package test;

import model.Card;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is the test class for testing all the methods in the model.Card Class
 */
public class CardTest {
    Card card1, card2, card3, card4, card5;

    /**
     * Initial setup for the testing environment.
     */
    @Before
    public void setUp() {
        card1 = new Card(Card.Color.RED, Card.Type.ONE);
        card2 = new Card(Card.Color.GREEN, Card.Type.SIX);
        card3 = new Card(Card.Color.BLUE, Card.Type.SKIP);
        card4 = new Card(Card.Color.YELLOW, Card.Type.DRAW_ONE);
        card5 = new Card(Card.Color.WILD, Card.Type.WILD_DRAW_TWO);
    }

    /**
     * This method tests if each card is retrieving the right color.
     */
    @Test
    public void testColor() {
        assertEquals(Card.Color.RED, card1.getColor());
        assertEquals(Card.Color.GREEN, card2.getColor());
        assertEquals(Card.Color.BLUE, card3.getColor());
        assertEquals(Card.Color.YELLOW, card4.getColor());
        assertEquals(Card.Color.WILD, card5.getColor());
    }

    /**
     * This method tests if each card is retrieving the right type.
     */
    @Test
    public void testType() {
        assertEquals(Card.Type.ONE, card1.getType());
        assertEquals(Card.Type.SIX, card2.getType());
        assertEquals(Card.Type.SKIP, card3.getType());
        assertEquals(Card.Type.DRAW_ONE, card4.getType());
        assertEquals(Card.Type.WILD_DRAW_TWO, card5.getType());
    }
}