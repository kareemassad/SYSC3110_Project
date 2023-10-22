package src;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    private Deck deck;
    private Card dCard;

    @Before
    public void setUp(){
        deck = new Deck();
        Card dCard = deck.drawCard();
    }

    @Test
    public void testDeckDraw(){
        dCard = deck.drawCard();
        assertNotNull(dCard);
    }

    @Test
    public void testNewDeck() {
        Deck newDeck = new Deck();
        int expectedCount = 104;
        assertEquals(expectedCount, newDeck.getDeckSize());
    }

}