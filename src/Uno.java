package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Uno {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int playerCount = 0;

        while (playerCount < 2 || playerCount > 4) {
            System.out.print("Enter the number of players (2-4): ");
            playerCount = sc.nextInt();

            if (playerCount < 2 || playerCount > 4) {
                System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
            }
        }

        Deck deck = new Deck();
        List<Card> gameDeck = deck.getDeck();
        Collections.shuffle(gameDeck);

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i));
        }

        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                Card card = gameDeck.remove(0);
                player.addCards(card);
            }
        }
    }
}