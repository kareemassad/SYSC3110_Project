import java.util.*;

/**
 * This is the main class. It shows the implementation of the game.
 */
public class UnoModel {
    private Deck deck;

    private List<Player> players;
    private Card topCard;
    private Player currentPlayer;
    private boolean isReversed = false;
    private boolean gameRunning;
    private Scanner sc = new Scanner(System.in);
    private boolean flip = false;
    public List<UnoView> views;

    public enum Status {
        UNDECIDED,
        GAME_STARTED,
        PLAYER_TURN_CHANGED,
        PLAYER_WON,
        CARD_PLAYED,
        DRAW_PENALTY,
        WILD_CARD_CHOSEN,
        UNO_ANNOUNCED,
        GAME_OVER,
        INVALID_MOVE,
        DECK_EMPTY,
        REVERSE_DIRECTION
    }

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final String[] VALID_COLORS = {"RED", "BLUE", "GREEN", "YELLOW"};
    public Status status;


    public UnoModel() {
        deck = new Deck();
        players = new ArrayList<>();
        gameRunning = true;
        views = new ArrayList<>();
        runGame();
        status = Status.UNDECIDED;
    }

    /**
     * Runs the Uno Game.
     */
    private void runGame() {
        notifyViews();
    }

    private void notifyViews() {
        UnoEvent event = new UnoEvent(this, getStatus());
        for (UnoView view : views) {
            view.handleUnoStatusUpdate(event);
        }
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Adds players to the Uno Game.
     */
    public void addPlayers(String name) {
        for (int i = 1; i <= 4; i++) {
            players.add(new Player(name));
        }
    }

    /**
     * Distributes initial cards to players and sets the Starting Card for the game.
     */
    private void dealInitialCards(){
        for (Player player : players){
            for(int i =0; i<7; i++){
                player.addCard(deck.drawCard());
            }
        }
        topCard = deck.drawCard();
        if(topCard.getType() == Card.Type.WILD || topCard.getType() == Card.Type.WILD_DRAW_TWO){
            System.out.println("Top card is wild. " + players.get(0).getName() + ", choose a color: ");
            chooseColorForWildCard();
        }
        System.out.println("Starting Card: " + topCard);
    }

    /**
     * Starts the Uno game and handles the turn of each player.
     */
    private void playGame(){
        deck.shuffle();
        boolean winner = false;
        Player winningPlayer = null;

        currentPlayer=players.get(0);
        while(!winner) {
            while (gameRunning) {
                playTurn();
            }
            System.out.println("\nCurrent scores: ");
            for (Player player: players){
                System.out.println(player.getName() + "'s score: " + player.getScore());
                if (player.getScore() >= 500) {
                    winningPlayer = player;
                    winner = true;
                    break;
                }
            }

            dealInitialCards();
            gameRunning = true;
        }

        System.out.println("The winner is " + winningPlayer);
        notifyViews();

    }

    /**
     * Checks if a card can be played on the current card.
     */
    private boolean isPlayable(Card card){
        return card.getType() == Card.Type.WILD || card.getType() == Card.Type.WILD_DRAW_TWO || card.getType() == topCard.getType() || card.getColor() == topCard.getColor();
    }

    /**
     * Moves to the next player's turn.
     */
    public void nextPlayer(){
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex;
        if(isReversed){
            nextPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        currentPlayer = players.get(nextPlayerIndex);
    }

    public Card drawCard() {
        return deck.drawCard();
    }


    /**
     * Handles the current player's turn, allowing them to play or draw cards and checks valid card play.
     */
    private void playTurn(){
        System.out.println("-------------------------");
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println("Current side: Light");
        System.out.println("Your Cards: ");

        for (int i =0; i < currentPlayer.getSize(); i++){
            System.out.println((i + 1) + ". " + currentPlayer.getCard(i));
        }
        System.out.println("Top Card: " + topCard);

        boolean validCardChoice = false;

        do {
            System.out.println("Enter card index to play or 0 to draw a card");
            int cardIndex = sc.nextInt() - 1;
            sc.nextLine();
            if(cardIndex >= 0 && cardIndex < currentPlayer.getSize()){
                Card chosenCard = currentPlayer.getCard(cardIndex);
                if(isPlayable(chosenCard)){
                    topCard = chosenCard;
                    currentPlayer.removeCard(cardIndex);
                    validCardChoice=true;
                    System.out.println("Played: " + topCard);
                    executeSpecialCardAction();
                    if(currentPlayer.getSize() == 0){
                        gameRunning = false;
                        System.out.println(currentPlayer.getName() +  " wins the round");
                        for (Player player : players) {
                            for (int i = 0; i < player.getSize(); i++)
                                currentPlayer.updateScore(player.getCard(i));
                        }
                        return;
                    }
                } else {
                    System.out.println("Card cannot be played. Try again");
                }
            } else if (cardIndex == -1){
                Card drawnCard = deck.drawCard();
                currentPlayer.addCard(drawnCard);
                System.out.println("You drew a " + drawnCard);
                validCardChoice=true;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        } while (!validCardChoice);
        nextPlayer();
    }

    /**
     *  Executes a special action on current card, such as reversing the game,
     *  drawing cards, skipping or choosing a color for wild cards
     */
    private void executeSpecialCardAction() {
        switch (topCard.getType()) {
            case REVERSE -> isReversed = !isReversed;
            case DRAW_ONE -> {
                nextPlayer();
                currentPlayer.addCard(deck.drawCard());
            }
            case WILD_DRAW_TWO -> {
                chooseColorForWildCard();
                nextPlayer();
                currentPlayer.addCard(deck.drawCard());
                currentPlayer.addCard(deck.drawCard());
            }
            case SKIP -> nextPlayer();
            case WILD -> chooseColorForWildCard();
            default -> {
            }
        }
    }

    /**
     * Allows the player to choose the color for Wild Card.
     */
    private void chooseColorForWildCard() {
        System.out.println("Choose a color (RED, BLUE, GREEN, YELLOW): ");
        while (true){
            String chosenColor = sc.nextLine().toUpperCase();
            if(Arrays.asList(VALID_COLORS).contains(chosenColor)){
                topCard.setColor(Card.Color.valueOf(chosenColor));
                break;
            } else {
                System.out.println("Invalid color. Please choose again (RED, BLUE, GREEN, YELLOW): ");
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addUnoView(UnoView view) {
        this.views.add(view);
    }

    public static void main(String[] args) {
        new UnoModel();
    }

}