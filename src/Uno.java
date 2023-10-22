package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Uno {
    private Deck deck;
    private List<Player> players;
    private Card topCard;

    public Uno() {
        deck = new Deck();
        players = new ArrayList<>();
    }

    public void addPlayers() {
        Scanner sc = new Scanner(System.in);
        int playerCount = 0;

        while (playerCount < 2 || playerCount > 4) {
            System.out.print("Enter the number of players (2-4): ");
            playerCount = sc.nextInt();
            sc.nextLine();

            if (playerCount < 2 || playerCount > 4) {
                System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
                playerCount = sc.nextInt();
            }
        }

        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter name for Player " + i + ": ");
            String name = sc.nextLine();
            players.add(new Player(name));
        }
    }

    public static void main(String[] args) {
        Uno uno = new Uno();
        uno.addPlayers();
    }
}