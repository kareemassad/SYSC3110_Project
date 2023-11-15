package view;

import controller.UnoController;
import controller.UnoEvent;
import model.Card;
import model.Player;
import model.UnoModel;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class UnoViewFrame extends JFrame implements UnoView {
    private UnoModel model;
    private JLabel topCardLabel, playerLabel;
    private JPanel pCardPanel;
    private JButton draw, nextPlayer;
    private UnoController uc;
    private JLabel statusLabel;

    public UnoViewFrame(UnoController uc, UnoModel model){
        super("UNO Game");
        this.uc = uc;
        this.model = model;

        this.setLayout(new BorderLayout());

        topCardLabel = new JLabel();
        playerLabel = new JLabel("Player 1");

        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createTitledBorder("Status"));
        statusLabel.setVisible(true);
        this.add(statusLabel, BorderLayout.SOUTH);

        pCardPanel = new JPanel(new FlowLayout());

        draw = new JButton("Draw Card");
        nextPlayer = new JButton("Next Player");

        draw.setActionCommand("DRAW");
        draw.addActionListener(uc);
        nextPlayer.setActionCommand("NEXT");
        nextPlayer.addActionListener(uc);

        this.add(playerLabel, BorderLayout.NORTH);
        this.add(topCardLabel, BorderLayout.CENTER);
        this.add(pCardPanel, BorderLayout.SOUTH);
        this.add(draw, BorderLayout.WEST);
        this.add(nextPlayer, BorderLayout.EAST);

        // Call the setup methods
        playerSetup();
        model.dealInitialCards();
        updateTopCardLabel(model.getTopCard());
        displayPlayerCards(model.getPlayers().get(0));

        // Adjusts the window to fit the preferred sizes of its subcomponents
        this.pack();
        // Center the window
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void updateTopCardLabel(Card topCard) {
        String imgPath = "src/images/" + topCard.getColor().toString().toLowerCase() + topCard.getType().toString().toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(imgPath);
        Image image = icon.getImage();
        Image resized = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resized);
        topCardLabel.setIcon(icon);
        topCardLabel.setText(topCard.getColor().toString() + " " + topCard.getType().toString());
    }

    @Override
    public void displayPlayerCards(Player player) {
        pCardPanel.removeAll();

        for (int i = 0; i < player.getSize(); i++) {
            Card card = player.getCard(i);
            String imgPath = "src/images/" + card.getColor().toString().toLowerCase() + card.getType().toString().toLowerCase() + ".png";
            ImageIcon icon = new ImageIcon(imgPath);
            Image image = icon.getImage();
            Image resized = image.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resized);

            JButton cardButton = new JButton(icon);
            cardButton.setActionCommand("PLAY " + i);
            cardButton.addActionListener(uc);

            pCardPanel.add(cardButton);;
        }

        pCardPanel.revalidate();
        pCardPanel.repaint();
    }

    private void playerSetup(){
        boolean validSetup = false;

        while(!validSetup) {
            int numberOfPlayers = 0;
            JPanel mainPanel = new JPanel();

            JTextField enterNumOfPlayers = new JTextField("Enter number of players (2-4):");
            enterNumOfPlayers.setEditable(false);
            mainPanel.add(enterNumOfPlayers);

            JTextField getPlayers = new JTextField(10);
            mainPanel.add(getPlayers);
            JOptionPane.showOptionDialog(this, mainPanel, "Setup", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            try {
                numberOfPlayers = Integer.parseInt(getPlayers.getText());
            }catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number of players, must be between 2 to 4 players");
                continue;
            }

            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                JOptionPane.showMessageDialog(this, "Invalid number of players, must be between 2 to 4 players");
            } else {
                addPlayerNames(numberOfPlayers);
                validSetup = true;
            }
        }
    }

    public void setStatus(String status){
        statusLabel.setText(status);
    }

    public void addPlayerNames(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            JPanel playerAddPanel = new JPanel();

            JTextField enterPlayersName = new JTextField("Enter player name");
            enterPlayersName.setEditable(false);
            playerAddPanel.add(enterPlayersName);

            JTextField getName = new JTextField(10);
            playerAddPanel.add(getName);

            int result;
            if (i == 0) {
                result = JOptionPane.showOptionDialog(this, playerAddPanel, "Add first player", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            } else {
                result = JOptionPane.showOptionDialog(this, playerAddPanel, "Add additional players", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            }

            if (result == JOptionPane.OK_OPTION) {
                String playerName = getName.getText();
                model.addPlayers(playerName);
            }

        }
    }

    public void enableDrawButton(boolean enable) {
        draw.setEnabled(enable);
    }

    public static void main(String[] args) {
        UnoModel model = new UnoModel();
        UnoController controller = new UnoController(model);
        UnoViewFrame view = new UnoViewFrame(controller, model);
        model.addUnoView(view);
        view.setVisible(true);
    }

    @Override
    public void handleUnoStatusUpdate(UnoEvent e) {
        Object source = e.getSource();
        UnoModel.Status status = model.getStatus();

        /**
        if (status == Uno.Status.UNDECIDED) {
            handleUndecided();
        }
         */
        if (status == UnoModel.Status.GAME_STARTED) {
            handleGameStarted();
        }
        else if (status == UnoModel.Status.PLAYER_TURN_CHANGED) {
            handlePlayerTurnChanged(e.getPlayer());
        }
        else if (status == UnoModel.Status.PLAYER_WON) {
            handlePlayerWon(e.getPlayer());
        }
        else if (status == UnoModel.Status.CARD_PLAYED) {
            handleCardPlayed(e.getPlayer(), e.getCard());
        }
        else if (status == UnoModel.Status.DRAW_PENALTY) {
            handleDrawPenalty(e.getPlayer(), e.getPenaltyCards());
        }

        else if (status == UnoModel.Status.WILD_CARD_CHOSEN) {
            handleWildCardChosen(e.getPlayer(), e.getChosenColor());
        }

        else if (status == UnoModel.Status.UNO_ANNOUNCED) {
            handleUnoAnnounced(e.getPlayer());
        }
        else if (status == UnoModel.Status.GAME_OVER) {
            handleGameOver();
        }
        else if (status == UnoModel.Status.INVALID_MOVE) {
            handleInvalidMove(e.getPlayer());
        }
        else if (status == UnoModel.Status.DECK_EMPTY) {
            handleDeckEmpty(e.getPlayer());
        }
        else if (status == UnoModel.Status.REVERSE_DIRECTION) {
            handleReverseDirection();
        }

    }

    private void handleGameStarted() {
        enableDrawButton(true);
    }

    private void handlePlayerTurnChanged(Player newPlayer) {
        displayPlayerCards(newPlayer);
        enableDrawButton(true);
    }

    private void handlePlayerWon(Player winningPlayer) {
        JOptionPane.showMessageDialog(this, winningPlayer.getName() + " wins!");
    }

    private void handleCardPlayed(Player player, Card playedCard) {
        updateTopCardLabel(playedCard);
        displayPlayerCards(player);
    }

    @Override
    public void updateStatus(String status) {
        statusLabel.setText(status);
    }

    @Override
    public void promptForColor(){
        Card.Color[] colors = Arrays.stream(Card.Color.values())
                                    .filter(c -> c != Card.Color.WILD)
                                    .toArray(Card.Color[]::new);
        String[] colorOptions = Arrays.stream(colors)
                                    .map(Enum::name)
                                    .toArray(String[]::new);
        String chosenColor = (String) JOptionPane.showInputDialog(
                this,
                "Choose a color for the wild card:",
                "Wild Card Color Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                colorOptions,
                colorOptions[0] //default
        );
        if(chosenColor != null && !chosenColor.isEmpty()){
            model.setWildCardColor(Card.Color.valueOf(chosenColor));

        }
    }

    private void handleDrawPenalty(Player player, int penaltyCards) {
        JOptionPane.showMessageDialog(this, player.getName() + " drew " + penaltyCards + " penalty cards!");
        displayPlayerCards(player);
    }

    private void handleCardClick(Card clickedCard) {
        JOptionPane.showMessageDialog(this, "Clicked on card: " + clickedCard.getColor() + " " + clickedCard.getType());
    }

    private void handleWildCardChosen(Player player, Card chosenColor){
        updateTopCardLabel(chosenColor);
        JOptionPane.showMessageDialog(this, "The color " + chosenColor.getColor() + " was selected.");
        displayPlayerCards(player);
    }

    private void handleUnoAnnounced(Player player){
        JOptionPane.showMessageDialog(this, player.getName() + " has an UNO!");
    }

    private void handleGameOver(){
        enableDrawButton(false);
    }

    private void handleInvalidMove(Player player){
        JOptionPane.showMessageDialog(this, "This move is invalid, please try again.");
        displayPlayerCards(player);
    }

    private void handleDeckEmpty(Player player){
        JOptionPane.showMessageDialog(this, "The deck is empty, reshuffling cards.");
        displayPlayerCards(player);
    }

    private void handleReverseDirection(){
        JOptionPane.showMessageDialog(this, "The direction of play is now reversed!");
    }
}
