package model;

import controller.UnoEvent;
import view.UnoView;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * This is the main class. It shows the implementation of the game.
 */
public class UnoModel implements Serializable {
    private Deck deck;
    private List<Player> players;
    private Card topCard;

    public Player currentPlayer;
    private boolean gameRunning, isReversed;
    private List<UnoView> views;
    private int currentPlayerIndex = 0;
    private Component view;

    public enum Status {
        UNDECIDED,
        PLAYER_TURN_CHANGED,
        DREW_CARD,
        PLAYER_WON,
        CARD_PLAYED,
        UNO_ANNOUNCED,
        GAME_OVER,
        INVALID_MOVE,
        DECK_EMPTY,
        REVERSE_DIRECTION,
        FLIP_CARDS,
        UNDO,
        REDO
    }

    public Status status;
    private Card chosenCard;
    private boolean hasDrawnThisTurn = false;
    private boolean flipped, skipped;

    private Card savedTop, savedDrawn;
    private List<Player> savedPlayers;

    /**
     * Establishes the UnoModel with all default parameters
     * and notifies all associated views.
     */
    public UnoModel() {
        deck = new Deck();
        players = new ArrayList<>();
        gameRunning = true;
        views = new ArrayList<>();
        runGame();
        status = Status.UNDECIDED;
        flipped = false;
        isReversed = false;
        skipped = false;
    }

    /**
     * Runs the Uno Game.
     */
    private void runGame() {
        notifyViews();
    }

    /**
     * Notifies all views currently associated with this
     * Uno Model based on the current event.
     */
    private void notifyViews() {
        UnoEvent event = new UnoEvent(this, getStatus(), currentPlayer, chosenCard);
        for (UnoView view : views) {
            view.handleUnoStatusUpdate(event);
        }
    }

    /**
     * Retrieves the current status of the UNO model.
     * @return The status of the model
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Adds players to the UNO Game.
     * @param playerName The player to be added.
     */
    public void addPlayers(String playerName) {
        players.add(new Player(playerName));
        notifyViews();
    }

    /**
     * Adds AI players to the UNO game.
     * @param playerName The AI player to be added.
     */
    public void addAIPlayer(String playerName) {
        AI newAIPlayer = new AI(playerName);
        players.add(newAIPlayer);
        notifyViews();
    }

    /**
     * Draws a card for the current player, first checking
     * if the current player has pressed redo to give the
     * same drawn card. It then draws a card for the players
     * so long as they have not drawn one this turn, and
     * notifies all associated views.
     */
    public void drawCardForPlayer() {
        if (!hasDrawnThisTurn) {
            if(savedDrawn != null){
                currentPlayer.addCard(savedDrawn);
                status = Status.DREW_CARD;
                notifyViews();
                return;
            }
            Card drawnCard = drawCard();
            currentPlayer.addCard(drawnCard);
            hasDrawnThisTurn = true;

            for (UnoView view : views) {
                status = Status.DREW_CARD;
                notifyViews();
            }
        } else {
            for (UnoView view : views) {
                view.updateStatus("Cannot draw more than one card per turn.");
            }
        }
    }

    /**
     * Distributes initial cards to players and sets the Starting model.Card for the game.
     */
    public void dealInitialCards() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }
        topCard = deck.drawCard();
        if (topCard.getType() == Card.Type.WILD || topCard.getType() == Card.Type.WILD_DRAW_TWO) {
            promptForWildCardColor();
        }
        currentPlayer = players.get(0);
        notifyViews();
    }

    /**
     * Checks if a card can be played on the current card.
     */
    public boolean isPlayable(Card card) {
        return card.getType() == Card.Type.WILD || card.getType() == Card.Type.WILD_DRAW_TWO || card.getType() == Card.Type.WILD_FLIP ||
                card.getType() == Card.Type.WILD_DRAW_COLOR || card.getType() == topCard.getType() || card.getColor() == topCard.getColor();
    }

    /**
     * Moves to the next player's turn.
     */
    public void nextPlayer() {
        if(!hasDrawnThisTurn){
            executeSpecialCardAction(topCard);
        }
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex;
        if (isReversed) {
            nextPlayerIndex = (currentPlayerIndex - 1) % players.size();
        } else {
            nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        currentPlayer = players.get(nextPlayerIndex);
        hasDrawnThisTurn = false;
        status = Status.PLAYER_TURN_CHANGED;
        notifyViews();
    }

    /**
     * Draws a card from the deck.
     * @return The card that has been drawn.
     */
    public Card drawCard() {
        return deck.drawCard();
    }

    /**
     * Reverses the direction of the game.
     */
    public void reverseDirection() {
        isReversed = !isReversed;
    }

    /**
     * Plays a turn based on the current player
     * and the cardIndex they have played. Checks if
     * the player is AI to implement a simulated turn.
     * Saves the card for undo and updates the current
     * player hand before notifying all views.
     *
     * @param cardIndex The index of the player card.
     */
    public void playTurn(int cardIndex) {
        if (currentPlayer instanceof AI) {
            ((AI) currentPlayer).AITurn(this);
            nextPlayer();
        } else {
            chosenCard = currentPlayer.getCard(cardIndex);
            if (isPlayable(chosenCard)) {
                saveState(topCard);
                topCard = chosenCard;
                currentPlayer.removeCard(cardIndex);
                checkWinCondition();
                if (!gameRunning) {
                    return;
                }
                hasDrawnThisTurn = false;
                status = Status.CARD_PLAYED;
                notifyViews();
            } else {
                status = Status.INVALID_MOVE;
                notifyViews();
            }
        }
    }

    /**
     * Undoes the previous action made by the player
     * by checking if they played a card or drew one,
     * then either giving that card back or taking it
     * away accordingly.
     */
    public void undo(){
        if(!hasDrawnThisTurn){
            Card temp = savedTop;
            savedTop = topCard;
            topCard = temp;
            currentPlayer.addCard(savedTop);
        }else{
            deck.addCard(currentPlayer.getCard(currentPlayer.getSize()-1));
            savedDrawn = currentPlayer.getCard(currentPlayer.getSize()-1);
            currentPlayer.removeCard(currentPlayer.getSize()-1);
            hasDrawnThisTurn = false;
        }
        status = Status.UNDO;
        notifyViews();
    }

    /**
     * Can only be called after undo(), reverses the
     * actions performs by that function. If a card had
     * previously been drawn, the same one shall be given
     * to the player. Otherwise, the card previously played
     * shall be removed from their hand and placed on top.
     */
    public void redo(){
        if(savedDrawn != null){
            currentPlayer.addCard(savedDrawn);
            savedDrawn = null;
            hasDrawnThisTurn = true;
        }else{
            Card temp = savedTop;
            savedTop = topCard;
            topCard = temp;
            currentPlayer.removeCard(currentPlayer.getSize()-1);
        }
        status = Status.REDO;
        notifyViews();
    }

    /**
     * Saves the current state of the game for undo/redo.
     * @param savedCard The top Card to be saved.
     */
    public void saveState(Card savedCard){
        savedTop = savedCard;
        savedPlayers = players;
    }

    /**
     * Checks the size of the current player hand
     * to ensure they have not won (empty). If they
     * have one card, then announce an UNO.
     */
    public void checkWinCondition() {
        if (currentPlayer.getSize() == 0) {
            gameRunning = false;
            status = Status.PLAYER_WON;
            notifyViews();
        } else if (currentPlayer.getSize() == 1) {
            status = Status.UNO_ANNOUNCED;
            notifyViews();
        }
    }

    /**
     * Retrieves the current player of the UNO Model.
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Executes a special action on current card, such as reversing the game,
     * drawing cards, skipping or choosing a color for wild cards
     */
    public void executeSpecialCardAction(Card card) {
        switch (card.getType()) {
            case REVERSE -> isReversed = !isReversed;
            case DRAW_ONE -> {
                Player next = getNextPlayer();
                next.addCard(deck.drawCard());
                currentPlayer = next;
            }
            case WILD_DRAW_TWO -> {
                promptForWildCardColor();
                Player next = getNextPlayer();
                next.addCard(deck.drawCard());
                next.addCard(deck.drawCard());
                currentPlayer = getNextPlayer();
            }
            case SKIP -> currentPlayer = getNextPlayer();
            case WILD -> promptForWildCardColor();
            case FLIP -> flipDeck();
            case DRAW_FIVE -> {
                Player next = getNextPlayer();
                for (int i = 0; i < 5; i++) {
                    next.addCard(deck.drawCard());
                }
                currentPlayer = next;
            }
            case SKIP_EVERYONE -> {
                for (int i = 1; i < players.size(); i++) {
                    currentPlayer = getNextPlayer();
                }
            }
            case WILD_FLIP -> promptForFlippedWildCardColor();
            case WILD_DRAW_COLOR -> {
                promptForFlippedWildCardColor();
                Player next = getNextPlayer();
                drawUntilColor(next);
                currentPlayer = next;
            }
            default -> {
            }
        }
    }

    /**
     * Retrieves the next player of the model
     * depending on whether the game is reversed.
     * @return The next player in the game
     */
    public Player getNextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentPlayerIndex + 1) % players.size();
        if (isReversed) {
            nextIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
        return players.get(nextIndex);
    }

    /**
     * Implements the functionality for the Draw_Color
     * card by having the player draw until the card drawn
     * matches the top card colour.
     *
     * @param player The player who must draw the cards.
     */
    public void drawUntilColor(Player player) {
        int i = player.getSize() - 1;
        while (player.getCard(i).getColor() != topCard.getColor()) {
            player.addCard(deck.drawCard());
            i++;
        }
    }

    /**
     * Flips the current deck and each player hand by sorting
     * through each one and calling separate functions.
     */
    private void flipDeck() {
        for (Player player : players) {
            for (int i = 0; i < player.getSize(); i++) {
                player.flipCard(i, flipped);
            }
        }
        deck.flipDeck(flipped);
        topCard.flipCard(flipped);
        flipped = !flipped;
        status = Status.FLIP_CARDS;
        notifyViews();
    }

    /**
     * Allows the player to choose the color for Wild model.Card.
     */
    public void setWildCardColor(Card.Color color) {
        if (topCard.getType() == Card.Type.WILD || topCard.getType() == Card.Type.WILD_DRAW_TWO
                || topCard.getType() == Card.Type.WILD_FLIP || topCard.getType() == Card.Type.WILD_DRAW_COLOR) {
            topCard.setColor(color);
            notifyViews();
        }
    }

    /**
     * Retrieves a list of all players in the current game.
     * @return A list of all players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves the current top card of the game.
     * @return The top card of the model.
     */
    public Card getTopCard() {
        return topCard;
    }

    /**
     * Adds a view component to this model.
     * @param view The view to be added.
     */
    public void addUnoView(UnoView view) {
        this.views.add(view);
    }

    /**
     * Sets the top Card of the model to the given card.
     * @param topCard The card to be set.
     */
    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    /**
     * Sends an update to each view component to
     * prompt for a wildCardColor.
     */
    public void promptForWildCardColor() {
        for (UnoView view : views) {
            view.promptForColor();
        }
    }

    /**
     * Sends an update to each view component to
     * prompt for a flipped wildCardColor.
     */
    public void promptForFlippedWildCardColor() {
        for (UnoView view : views) {
            view.promptForFlipColor();
        }
    }

    /**
     * Counts the score of the given player by
     * UNO rules, sorting through the hand of each
     * opponent and adding the points associated with
     * their cards.
     *
     * @param winningPlayer The player whose score is counted.
     * @return true if winnerScore is >500, false otherwise
     */
    public boolean countScore(Player winningPlayer) {
        for (Player player : players) {
            if (player != winningPlayer) {
                for (int i = 0; i < player.getSize(); i++) {
                    player.updateScore(player.getCard(i));
                    winningPlayer.addTotalScore(player.getScore());
                }
            }
        }
        return winningPlayer.getScore() > 500;
    }

    /**
     * Retrieves the score of the givenPlayer
     * @param player The player whose score is retrieved.
     * @return The retrieved score of the given player.
     */
    public int getScore(Player player) {
        return player.getTotalScore();
    }

    /**
     * Utilizes Java Serialization to save the current
     * state of the model.
     *
     * @param fileName The filename provided by the player.
     */
    public void saveGame(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a previously saved status of the model
     * saved to a given filename. The current components of
     * the model will be overwritten by the components within
     * the save file.
     *
     * @param fileName The file to be loaded.
     */
    public void loadGame(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            UnoModel loadedGame = (UnoModel) ois.readObject();
            copyState(loadedGame);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all parameters from a save file to the
     * current model, overwriting them.
     * @param loadedGame The model to be loaded.
     */
    private void copyState(UnoModel loadedGame) {
        this.deck = loadedGame.deck;
        this.players = loadedGame.players;
        this.topCard = loadedGame.topCard;
        this.currentPlayer = loadedGame.currentPlayer;
        this.gameRunning = loadedGame.gameRunning;
        this.isReversed = loadedGame.isReversed;
        this.views = loadedGame.views;
        this.currentPlayerIndex = loadedGame.currentPlayerIndex;
        this.view = loadedGame.view;
        this.status = loadedGame.status;
        this.chosenCard = loadedGame.chosenCard;
        this.hasDrawnThisTurn = loadedGame.hasDrawnThisTurn;
        this.flipped = loadedGame.flipped;
        this.skipped = loadedGame.skipped;
        this.savedTop = loadedGame.savedTop;
        this.savedDrawn = loadedGame.savedDrawn;
        this.savedPlayers = loadedGame.savedPlayers;
    }

}