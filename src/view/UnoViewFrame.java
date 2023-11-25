package view;

import controller.UnoController;
import controller.UnoEvent;
import model.AI;
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
        playerLabel = new JLabel();


        pCardPanel = new JPanel(new FlowLayout());

        draw = new JButton("Draw Card");
        nextPlayer = new JButton("Next Player");

        draw.setActionCommand("DRAW");
        draw.addActionListener(uc);
        nextPlayer.setActionCommand("NEXT");


        this.add(playerLabel, BorderLayout.NORTH);
        this.add(topCardLabel, BorderLayout.CENTER);
        this.add(pCardPanel, BorderLayout.SOUTH);

        statusLabel = new JLabel("Welcome to UNO FLIP");
        statusLabel.setBorder(BorderFactory.createTitledBorder("Status"));
        statusLabel.setVisible(true);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(statusLabel, BorderLayout.NORTH);
        southPanel.add(pCardPanel, BorderLayout.CENTER);

        this.add(southPanel, BorderLayout.SOUTH);
        this.add(draw, BorderLayout.WEST);
        this.add(nextPlayer, BorderLayout.EAST);

        playerSetup();
        model.dealInitialCards();
        updateTopCardLabel(model.getTopCard());
        displayPlayerCards(model.getPlayers().get(0));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        nextPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player currentPlayer = model.getCurrentPlayer();
                int currentPlayerIndex = model.getCurrentPlayerIndex();
                int nextPlayerIndex = (currentPlayerIndex + 1) % model.getPlayers().size();

                model.setCurrentPlayerIndex(nextPlayerIndex);

                Player nextPlayer = model.getPlayers().get(nextPlayerIndex);
                handlePlayerTurnChanged(nextPlayer);
            }
        });
    }

    public void updateTopCardLabel(Card topCard) {
        String imgPath = "src/images/" + topCard.getColor().toString().toLowerCase() + "_" + topCard.getType().toString().toLowerCase() + ".png";
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
            String imgPath = "src/images/" + card.getColor().toString().toLowerCase() + "_" + card.getType().toString().toLowerCase() + ".png";
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

            JCheckBox aiPlayerCheckbox = new JCheckBox("AI Player");
            playerAddPanel.add(aiPlayerCheckbox);

            int result;
            String dialogTitle = (i == 0) ? "Add first player" : "Add additional players";
            result = JOptionPane.showOptionDialog(this, playerAddPanel, dialogTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (result == JOptionPane.OK_OPTION) {
                String playerName = getName.getText();
                boolean isAIPlayer = aiPlayerCheckbox.isSelected();

                if (isAIPlayer) {
                    model.addAIPlayer(playerName);
                } else {
                    model.addPlayers(playerName);
                }
            }
        }
    }


    public void enableDrawButton(boolean enable) {
        draw.setEnabled(enable);
    }
    public void enableNextPlayerButton(boolean enabled) {nextPlayer.setEnabled(enabled);
    }

    public static void main(String[] args) {
        UnoModel model = new UnoModel();
        UnoController controller = new UnoController(model);
        UnoViewFrame view = new UnoViewFrame(controller, model);
        model.addUnoView(view);
        view.setVisible(true);
    }

//    @Override
//    public void handleUnoStatusUpdate(UnoEvent e) {
//        Object source = e.getSource();
//        UnoModel.Status status = model.getStatus();
//
//        /**
//        if (status == Uno.Status.UNDECIDED) {
//            handleUndecided();
//        }
//         */
//        if (status == UnoModel.Status.GAME_STARTED) {
//            handleGameStarted();
//        }
//        else if (status == UnoModel.Status.PLAYER_TURN_CHANGED) {
//            handlePlayerTurnChanged(e.getPlayer());
//        }
//        else if (status == UnoModel.Status.PLAYER_WON) {
//            handlePlayerWon(e.getPlayer());
//        }
//        else if (status == UnoModel.Status.CARD_PLAYED) {
//            handleCardPlayed(e.getPlayer(), e.getCard());
//        }
//        else if (status == UnoModel.Status.DRAW_PENALTY) {
//            handleDrawPenalty(e.getPlayer(), e.getPenaltyCards());
//        }
//
//        else if (status == UnoModel.Status.WILD_CARD_CHOSEN) {
//            handleWildCardChosen(e.getPlayer(), e.getChosenColor());
//        }
//
//        else if (status == UnoModel.Status.UNO_ANNOUNCED) {
//            handleUnoAnnounced(e.getPlayer());
//        }
//        else if (status == UnoModel.Status.GAME_OVER) {
//            handleGameOver();
//        }
//        else if (status == UnoModel.Status.INVALID_MOVE) {
//            handleInvalidMove(e.getPlayer());
//        }
//        else if (status == UnoModel.Status.DECK_EMPTY) {
//            handleDeckEmpty(e.getPlayer());
//        }
//        else if (status == UnoModel.Status.REVERSE_DIRECTION) {
//            handleReverseDirection();
//        }
//
//    }
@Override
    public void handleUnoStatusUpdate(UnoEvent e) {
        UnoModel.Status status = model.getStatus();
   //     UnoModel.Status status = model.status;
        System.out.println(status);
        switch (status) {
            case GAME_STARTED:
                handleGameStarted();
                break;
            case PLAYER_TURN_CHANGED:
                handlePlayerTurnChanged(e.getPlayer());
                break;
            case PLAYER_WON:
                handlePlayerWon(e.getPlayer());
                break;
            case CARD_PLAYED:
                handleCardPlayed(e.getPlayer(), e.getCard());
                break;
            case DRAW_PENALTY:
                handleDrawPenalty(e.getPlayer(), e.getPenaltyCards());
                break;
            case WILD_CARD_CHOSEN:
                handleWildCardChosen(e.getChosenColor());
                break;
            case UNO_ANNOUNCED:
                handleUnoAnnounced(e.getPlayer());
                break;
            case GAME_OVER:
                handleGameOver();
                break;
            case INVALID_MOVE:
                handleInvalidMove();
                break;
            case DECK_EMPTY:
                handleDeckEmpty();
                break;
            case REVERSE_DIRECTION:
                handleReverseDirection();
                break;
            default:
                setStatus("Unhandled status: " + status);
                break;
        }
    }

    private void handleGameStarted() {
        setStatus("The game has started");
        enableDrawButton(true);
        enableNextPlayerButton(true);
    }

    private void handlePlayerWon(Player winningPlayer) {
        setStatus(winningPlayer.getName() + " wins!");
    }

    private void handleCardPlayed(Player player, Card playedCard) {
        if (playedCard.getType() == Card.Type.REVERSE) {
            model.reverseDirection();
            setStatus("Reverse card played by " + player.getName() + ". Direction reversed.");
        } else {
            updateTopCardLabel(playedCard);
            displayPlayerCards(player);
            setStatus("Played: " + playedCard);
        }
    }

    private void handlePlayerTurnChanged(Player newPlayer) {
        setPlayerName(newPlayer.getName());
        pCardPanel.removeAll();
        displayPlayerCards(newPlayer);
        updateTopCardLabel(model.getTopCard());
        setStatus(newPlayer.getName() + "'s turn.");

        if (newPlayer instanceof AI) {
            AI aiPlayer = (AI) newPlayer;
            aiPlayer.AITurn(model);
        }

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
        setStatus(player.getName() + " drew " + penaltyCards + " penalty cards!");
        displayPlayerCards(player);
    }

    private void handleCardClick(Card clickedCard) {
        JOptionPane.showMessageDialog(this, "Clicked on card: " + clickedCard.getColor() + " " + clickedCard.getType());
    }

    private void handleWildCardChosen(Card chosenColor){
        updateTopCardLabel(chosenColor);
        setStatus("Wild card color chosen: " + chosenColor.getColor());
    }

    private void handleUnoAnnounced(Player player){
        setStatus(player.getName() + "announced UNO!");
    }

    private void handleGameOver(){
        setStatus("Game Over.");
        enableDrawButton(false);
        enableNextPlayerButton(false);
    }

    private void handleInvalidMove(){
        setStatus("Invalid move!");
    }

    private void handleDeckEmpty(){
        setStatus("Deck is empty, reshuffling");
    }

    private void handleReverseDirection(){
        setStatus("Play direction reversed");
    }

    public void setPlayerName(String name) {
        playerLabel.setText(name);
    }

}
