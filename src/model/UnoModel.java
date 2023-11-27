package model;

import controller.UnoEvent;
import view.UnoView;

import java.util.*;

/**
 * This is the main class. It shows the implementation of the game.
 */
public class UnoModel {
    private Deck deck;
    private List<Player> players;
    private Card topCard;

    public Player currentPlayer;
    private boolean isReversed = false;
    private boolean gameRunning;
    private List<UnoView> views;
    private Scanner sc = new Scanner(System.in);
    private int currentPlayerIndex = 0;
    private UnoModel model;

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
        FLIP_CARDS
    }

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final String[] VALID_COLORS = {"RED", "BLUE", "GREEN", "YELLOW"};
    public Status status;
    private Card chosenCard;
    private boolean hasDrawnThisTurn = false;
    private boolean flipped;


    public UnoModel() {
        deck = new Deck();
        players = new ArrayList<>();
        gameRunning = true;
        views = new ArrayList<>();
        runGame();
        status = Status.UNDECIDED;
        flipped = false;
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
        int numberOfPlayers = players.size();
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
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex;
        if (isReversed) {
            nextPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
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


    /**
     * Handles the current player's turn, allowing them to play or draw cards and checks valid card play.
     */
//    public void playTurn(int cardIndex){
//        boolean validCardChoice = false;
//
//        do {
//            System.out.println("Enter card index to play or 0 to draw a card");
//            if(cardIndex >= 0 && cardIndex < currentPlayer.getSize()){
//                Card chosenCard = currentPlayer.getCard(cardIndex);
//                if(isPlayable(chosenCard)){
//                    topCard = chosenCard;
//                    currentPlayer.removeCard(cardIndex);
//                    validCardChoice=true;
//                    System.out.println("Played: " + topCard);
//                    executeSpecialCardAction();
//                    if(currentPlayer.getSize() == 0){
//                        gameRunning = false;
//                        System.out.println(currentPlayer.getName() +  " wins the round");
//                        for (Player player : players) {
//                            for (int i = 0; i < player.getSize(); i++)
//                                currentPlayer.updateScore(player.getCard(i));
//                        }
//                        return;
//                    }
//                } else {
//                    System.out.println("Card cannot be played. Try again");
//                    return;
//                }
//            } else if (cardIndex == -1){
//                Card drawnCard = deck.drawCard();
//                currentPlayer.addCard(drawnCard);
//                System.out.println("You drew a " + drawnCard);
//                validCardChoice=true;
//            } else {
//                System.out.println("Invalid choice. Try again.");
//            }
//        } while (!validCardChoice);
//        nextPlayer();
//    }
    public void playTurn(int cardIndex) {
        if (currentPlayer instanceof AI) {
            ((AI) currentPlayer).AITurn(this);
            nextPlayer();
        } else {
            chosenCard = currentPlayer.getCard(cardIndex);
            if (isPlayable(chosenCard)) {
                topCard = chosenCard;
                currentPlayer.removeCard(cardIndex);
                executeSpecialCardAction(chosenCard);
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
    public void checkWinCondition(){
        if(currentPlayer.getSize() == 0){
            gameRunning = false;
            status = Status.PLAYER_WON;
            notifyViews();
        } else if(currentPlayer.getSize() == 1){
            status = Status.UNO_ANNOUNCED;
            notifyViews();
        }
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     *  Executes a special action on current card, such as reversing the game,
     *  drawing cards, skipping or choosing a color for wild cards
     */
    public void executeSpecialCardAction(Card card) {
        switch (card.getType()) {
            case REVERSE -> isReversed = !isReversed;
            case DRAW_ONE -> {
                nextPlayer();
                currentPlayer.addCard(deck.drawCard());
            }
            case WILD_DRAW_TWO -> {
                promptForWildCardColor();
                nextPlayer();
                currentPlayer.addCard(deck.drawCard());
                currentPlayer.addCard(deck.drawCard());
            }
            case SKIP -> nextPlayer();
            case WILD -> promptForWildCardColor();
            case FLIP -> flipDeck();
            case DRAW_FIVE -> {
                nextPlayer();
                for (int i = 0; i < 4; i++){
                    currentPlayer.addCard(deck.drawCard());
                }
            }
            case SKIP_EVERYONE -> {
                for (int i = 1; i < players.size(); i++){
                    nextPlayer();
                }
            }
            case WILD_FLIP -> promptForFlippedWildCardColor();
            case WILD_DRAW_COLOR -> {
                promptForFlippedWildCardColor();
                nextPlayer();
                drawUntilColor();
            }
            default -> {
            }
        }
        nextPlayer();
    }

    public void drawUntilColor(){
        int i = currentPlayer.getSize()-1;
        while(currentPlayer.getCard(i).getColor() != topCard.getColor()){
            currentPlayer.addCard(deck.drawCard());
            i++;
        }
    }
    private void flipDeck(){
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
//    private void chooseColorForWildCard() {
//        System.out.println("Choose a color (RED, BLUE, GREEN, YELLOW): ");
//        while (true){
//            String chosenColor = sc.nextLine().toUpperCase();
//            if(Arrays.asList(VALID_COLORS).contains(chosenColor)){
//                topCard.setColor(Card.Color.valueOf(chosenColor));
//                break;
//            } else {
//                System.out.println("Invalid color. Please choose again (RED, BLUE, GREEN, YELLOW): ");
//            }
//        }
//    }
    public void setWildCardColor(Card.Color color){
        if (topCard.getType()== Card.Type.WILD || topCard.getType()== Card.Type.WILD_DRAW_TWO
                ||topCard.getType()== Card.Type.WILD_FLIP ||topCard.getType()== Card.Type.WILD_DRAW_COLOR){
            topCard.setColor(color);
            notifyViews();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Card getTopCard(){return topCard; }

    public void addUnoView(UnoView view) {
        this.views.add(view);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    private void notifyViewsCardPlayed(Card card){
        for (UnoView view : views){
            view.updateStatus("Played: " + card);
        }
    }

    public  void promptForWildCardColor(){
        for(UnoView view : views){
            view.promptForColor();
        }
    }
    public  void promptForFlippedWildCardColor(){
        for(UnoView view : views){
            view.promptForFlipColor();
        }
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int index) {
        currentPlayerIndex = index;
    }

    public boolean countScore(Player winningPlayer){
        for (Player player: players){
            if (player != winningPlayer){
                for(int i = 0; i < player.getSize(); i++){
                    player.updateScore(player.getCard(i));
                    winningPlayer.addTotalScore(player.getScore());
                }
            }
        }
        if(winningPlayer.getScore()>500){
            return true;
        } else{
            return false;
        }
    }

    public int getScore(Player player){
        return player.getTotalScore();
    }

}