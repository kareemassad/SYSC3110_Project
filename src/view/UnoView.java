package view;

import controller.UnoEvent;
import model.Player;

public interface UnoView {
    void handleUnoStatusUpdate(UnoEvent e);
    void updateStatus(String status);

    void promptForColor();
    void displayPlayerCards(Player player);
}
