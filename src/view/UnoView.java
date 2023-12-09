package view;

import controller.UnoEvent;
import model.Player;

public interface UnoView {
    void handleUnoStatusUpdate(UnoEvent e);
    void updateStatus(String status);
    void promptForColor();
    void promptForFlipColor();
    void displayPlayerCards(Player player);
    void setPlayerName(String name);

}
