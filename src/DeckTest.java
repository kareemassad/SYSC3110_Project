

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is the test class for testing all the methods in the Deck Class
 */
public class DeckTest {

    private Deck deck;
    private Card dCard;

    /**
     * Initial setup for the testing environment.
     */
    @Before
    public void setUp(){
        deck = new Deck();
        dCard = deck.drawCard();
    }

    /**
     * This method is used to check that the deck provides a non-null card.
     */
    @Test
    public void testDeckDraw(){
        dCard = deck.drawCard();
        assertNotNull(dCard);
    }

    /**
     * This method checks that there are always 104 cards when the UNO new Deck is initiated.
     */
    @Test
    public void testNewDeck() {
        Deck newDeck = new Deck();
        int expectedCount = 104;
        assertEquals(expectedCount, newDeck.getDeckSize());
    }

    /**
     * This method tests for the presence of cards of all types(numerical, special and wild) and colors(RED,YELLOW,
     * GREEN,BLUE) in the Deck.
     */
    @Test
    public void testCardsColorAndTypes() {
        assertTrue(deck.contains(Card.Color.RED, Card.Type.ONE));
        assertTrue(deck.contains(Card.Color.GREEN, Card.Type.DRAW_ONE));
        assertTrue(deck.contains(Card.Color.BLUE, Card.Type.REVERSE));
        assertTrue(deck.contains(Card.Color.YELLOW, Card.Type.SKIP));
        assertTrue(deck.contains(Card.Color.WILD, Card.Type.WILD));
        assertTrue(deck.contains(Card.Color.WILD, Card.Type.WILD_DRAW_TWO));
    }

}