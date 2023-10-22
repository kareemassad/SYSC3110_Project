import java.lang.reflect.Array;
import java.util.*;

public class Uno {
    private Deck deck;
    private List<Player> players;
    private Card topCard;
    private Player currentPlayer;
    private boolean isReversed = false;
    private boolean gameRunning;
    private Scanner sc = new Scanner(System.in);

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final String[] VALID_COLORS = {"RED", "BLUE", "GREEN", "YELLOW"};


    public Uno() {
        deck = new Deck();
        players = new ArrayList<>();
        gameRunning=true;
        runGame();
    }

    private void runGame() {
        addPlayers();
        dealInitialCards();
        playGame();
    }

    private void addPlayers() {
        int playerCount = 0;

        while (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS) {
            System.out.print("Enter the number of players (2-4): ");
            try {
                playerCount = sc.nextInt();
                sc.nextLine();

                if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS) {
                    System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
                }

            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number");

            }

        }

        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter name for Player " + i + ": ");
            String name = sc.nextLine();
            players.add(new Player(name));
        }
        System.out.println("-------------------------");
    }

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
        System.out.println("Top Card: " + topCard);
    }

    private void playGame(){
        deck.shuffle();

        currentPlayer=players.get(0);
        while (gameRunning){
            playTurn();
        }

    }

    private boolean isPlayable(Card card){
        return card.getType()==Card.Type.WILD || card.getType()==Card.Type.WILD_DRAW_TWO || card.getType()== topCard.getType() || card.getColor() == topCard.getColor();
    }

    private void nextPlayer(){
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex;
        if(isReversed){
            nextPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        currentPlayer = players.get(nextPlayerIndex);
    }

    private void playTurn(){
        System.out.println("-------------------------");
        System.out.println(currentPlayer.getName() + "'s Turn");
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
                        System.out.println(currentPlayer.getName() +  " wins!");
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

    private void executeSpecialCardAction() {
        switch (topCard.getType()){
            case REVERSE :
                isReversed = !isReversed;
                break;
            case DRAW_ONE:
                nextPlayer();
                currentPlayer.addCard(deck.drawCard());
                break;
            case WILD_DRAW_TWO:
                chooseColorForWildCard();
                nextPlayer();
                currentPlayer.addCard(deck.drawCard());
                currentPlayer.addCard(deck.drawCard());
                break;
            case SKIP:
                nextPlayer();
                break;
            case WILD:
                chooseColorForWildCard();
                break;
            default:
                break;
        }
    }

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

    public static void main(String[] args) {
        new Uno();
    }
}