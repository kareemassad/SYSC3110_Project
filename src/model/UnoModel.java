package model;

import controller.UnoEvent;
import view.UnoView;

import javax.swing.*;
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

    private void notifyViews() {
        UnoEvent event = new UnoEvent(this, getStatus(), currentPlayer, chosenCard);
        for (UnoView view : views) {
            view.handleUnoStatusUpdate(event);
        }
    }

    public List<UnoView> getViews() {
        return this.views;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Adds players to the Uno Game.
     */
    public void addPlayers(String playerName) {
        players.add(new Player(playerName));
        notifyViews();
    }

    public void addAIPlayer(String playerName) {
        AI newAIPlayer = new AI(playerName);
        players.add(newAIPlayer);
        notifyViews();
    }

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
        System.out.println("Starting Card: " + topCard);
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

    public Card drawCard() {
        return deck.drawCard();
    }

    public void reverseDirection() {
        isReversed = !isReversed;
    }

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

    public void saveState(Card savedCard){
        savedTop = savedCard;
        savedPlayers = players;
    }
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

    public Player getNextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentPlayerIndex + 1) % players.size();
        if (isReversed) {
            nextIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
        return players.get(nextIndex);
    }

    public void drawUntilColor(Player player) {
        int i = player.getSize() - 1;
        while (player.getCard(i).getColor() != topCard.getColor()) {
            player.addCard(deck.drawCard());
            i++;
        }
    }

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

    public List<Player> getPlayers() {
        return players;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void addUnoView(UnoView view) {
        this.views.add(view);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public void promptForWildCardColor() {
        for (UnoView view : views) {
            view.promptForColor();
        }
    }

    public void promptForFlippedWildCardColor() {
        for (UnoView view : views) {
            view.promptForFlipColor();
        }
    }

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

    public int getScore(Player player) {
        return player.getTotalScore();
    }

    public void saveGame(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            System.out.println("Game saved successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    public void loadGame(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            UnoModel loadedModel = (UnoModel) inputStream.readObject();
            if (loadedModel != null) {
                copyDataFrom(loadedModel);
                JOptionPane.showMessageDialog(null, "Game loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to load the game. Check the file or try again.");
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading the game: " + ex.getMessage());
        }
    }

    private void copyDataFrom(UnoModel loadedModel) {
        this.deck = loadedModel.deck;
        this.players = loadedModel.players;
        this.topCard = loadedModel.topCard;
        this.currentPlayer = loadedModel.currentPlayer;
        this.isReversed = loadedModel.isReversed;
        this.gameRunning = loadedModel.gameRunning;
        this.views = loadedModel.views;
        this.currentPlayerIndex = loadedModel.currentPlayerIndex;
        this.status = loadedModel.status;
        this.chosenCard = loadedModel.chosenCard;
        this.hasDrawnThisTurn = loadedModel.hasDrawnThisTurn;
        this.flipped = loadedModel.flipped;
    }

}