import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Uno {
    private Deck deck;
    private List<Player> players;
    private Card topCard;
    private Player currentPlayer;
    private boolean isReversed = false;
    private boolean gameRunning;
    private Scanner sc = new Scanner(System.in);

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

        while (playerCount < 2 || playerCount > 4) {
            System.out.print("Enter the number of players (2-4): ");
            try {
                playerCount = sc.nextInt();
                sc.nextLine();

                if (playerCount < 2 || playerCount > 4) {
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
    }

    private void dealInitialCards(){
        for (Player player : players){
            for(int i =0; i<7; i++){
                player.addCards(deck.drawCard());
            }
        }
        topCard = deck.drawCard();
        System.out.println("Starting Card: " + topCard);
    }

    private void playGame(){
        deck.shuffle();

        currentPlayer=players.get(0);
        while (gameRunning){
            playTurn();
        }

    }

    private boolean isPlayable(Card card){
        return card.getColor() == topCard.getColor() || card.getType() == topCard.getType();
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
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println("Your Cards: \n");

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
                    currentPlayer.removeCards(cardIndex);
                    validCardChoice=true;
                    if(currentPlayer.getSize() == 0){
                        gameRunning = false;
                        System.out.println(currentPlayer.getName() +  " wins!");
                        return;
                    }
                } else {
                    System.out.println("Card cannot be played. Try again");
                }
            } else if (cardIndex == -1){
                Card drawnCard = deck.drawCard();
                currentPlayer.addCards(drawnCard);
                System.out.println("You drew a " + drawnCard);
                validCardChoice=true;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        } while (!validCardChoice);
        nextPlayer();
    }

    public static void main(String[] args) {
        new Uno();
    }
}