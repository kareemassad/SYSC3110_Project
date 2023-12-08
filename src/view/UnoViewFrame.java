package view;

import controller.UnoController;
import controller.UnoEvent;
import model.AI;
import model.Card;
import model.Player;
import model.UnoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class UnoViewFrame extends JFrame implements UnoView {
    private UnoModel model;
    private JLabel topCardLabel, playerLabel ,statusLabel;
    private JToolBar buttonPanel;
    private JPanel actionButtons, pCardPanel;
    private JScrollPane scroll;
    private JButton draw, nextPlayer, undo, redo;
    private UnoController uc;
    private JMenuItem save, load;
    private JMenu fileMenu;
    private JMenuBar menuBar;
    private ArrayList<JButton> buttons;

    public UnoViewFrame(UnoController uc, UnoModel model){
        super("UNO Game");
        this.uc = uc;
        this.model = model;
        this.buttons = new ArrayList<JButton>();

        this.setLayout(new BorderLayout());
        topCardLabel = new JLabel();
        playerLabel = new JLabel();

        pCardPanel = new JPanel();
        buttonPanel = new JToolBar();

        actionButtons = new JPanel(new BorderLayout());

        draw = new JButton("Draw Card");
        nextPlayer = new JButton("Next Player");
        undo = new JButton("Undo");
        redo = new JButton("Redo");

        draw.setActionCommand("DRAW");
        draw.addActionListener(uc);
        nextPlayer.setActionCommand("NEXT");
        nextPlayer.addActionListener(uc);
        undo.setActionCommand("UNDO");
        undo.addActionListener(uc);
        redo.setActionCommand("REDO");
        redo.addActionListener(uc);

        this.add(playerLabel, BorderLayout.NORTH);
        this.add(topCardLabel, BorderLayout.CENTER);
        this.add(actionButtons, BorderLayout.SOUTH);

        statusLabel = new JLabel("Welcome to UNO FLIP");
        statusLabel.setBorder(BorderFactory.createTitledBorder("Status"));
        statusLabel.setVisible(true);

        buttonPanel.add(nextPlayer);
        buttonPanel.add(undo);
        buttonPanel.add(redo);

        actionButtons.add(statusLabel, BorderLayout.NORTH);
        actionButtons.add(pCardPanel, BorderLayout.CENTER);
        actionButtons.add(buttonPanel, BorderLayout.EAST);
        actionButtons.add(draw, BorderLayout.WEST);

        buttonPanel.setFloatable(false);
        undo.setEnabled(false);
        redo.setEnabled(false);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        save = new JMenuItem("Save");
        load = new JMenuItem("Load");

        save.addActionListener(uc.getSerializeListener());
        load.addActionListener(uc.getDeserializeListener());

        fileMenu.add(save);
        fileMenu.add(load);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        this.add(nextPlayer, BorderLayout.EAST);

        playerSetup();
        model.dealInitialCards();
        updateTopCardLabel(model.getTopCard());
        displayPlayerCards(model.getPlayers().get(0));

        scroll = new JScrollPane(pCardPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        actionButtons.add(scroll, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addSerializeListener(ActionListener listener){
        this.save.addActionListener(listener);
    }

    public void addDeserializeListener(ActionListener listener){
        this.load.addActionListener(listener);
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
            buttons.add(cardButton);
            cardButton.setActionCommand("PLAY " + i);
            cardButton.addActionListener(uc);

            pCardPanel.add(cardButton);
        }

        pCardPanel.revalidate();
        pCardPanel.repaint();
    }


    private void playerSetup(){
        boolean validSetup = false;

        while(!validSetup) {
            int numberOfPlayers;
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
        controller.linkView(view);
        model.addUnoView(view);
        view.setVisible(true);
    }

@Override
    public void handleUnoStatusUpdate(UnoEvent e) {
        UnoModel.Status status = model.getStatus();
        System.out.println(status);
    switch (status) {
        case PLAYER_TURN_CHANGED -> handlePlayerTurnChanged();
        case PLAYER_WON -> handlePlayerWon(e.getPlayer(), e.getCard());
        case CARD_PLAYED -> handleCardPlayed(e.getPlayer(), e.getCard());
        case DREW_CARD -> handleCardDrawn(e.getPlayer());
        case UNO_ANNOUNCED -> handleUnoAnnounced(e.getPlayer());
        case GAME_OVER -> handleGameOver();
        case INVALID_MOVE -> handleInvalidMove();
        case DECK_EMPTY -> handleDeckEmpty();
        case REVERSE_DIRECTION -> handleReverseDirection();
        case FLIP_CARDS -> handleFlipCards();
        case UNDO -> handleUndo();
        case REDO -> handleRedo();
        default -> setStatus("Unhandled status: " + status);
    }
    }


    private void handlePlayerWon(Player winningPlayer, Card lastCard) {

        if(model.countScore(winningPlayer)){
            setStatus(winningPlayer.getName() + " has surpassed 500 points. They win the game!");
        }else{
            setStatus(winningPlayer.getName() + " has won the round! They earned " + model.getScore(winningPlayer) + " points.");
            updateTopCardLabel(lastCard);
            displayPlayerCards(winningPlayer);
        }
        enableDrawButton(false);
        enableNextPlayerButton(false);

    }

    private void handleCardPlayed(Player player, Card playedCard) {
        if (playedCard.getType() == Card.Type.REVERSE) {
            model.reverseDirection();
            setStatus("Reverse card played by " + player.getName() + ". Direction reversed.");
            for(JButton button: buttons){
                button.setEnabled(false);
            }
            draw.setEnabled(false);
            undo.setEnabled(true);
            redo.setEnabled(false);
        } else {
            updateTopCardLabel(playedCard);
            displayPlayerCards(player);
            setStatus("Played: " + playedCard);
            for(JButton button: buttons){
                button.setEnabled(false);
            }
            draw.setEnabled(false);
            undo.setEnabled(true);
        }
    }
    private void handleCardDrawn(Player player){
        setStatus(player.getName() + " drew a card.");
        draw.setEnabled(false);
        undo.setEnabled(true);
        redo.setEnabled(false);
        displayPlayerCards(player);
    }

    private void handlePlayerTurnChanged() {
        Player currentPlayer = model.getCurrentPlayer();
        setPlayerName(model.getCurrentPlayer().getName());
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus(model.getCurrentPlayer().getName() + "'s turn.");
        for(JButton button: buttons){
            button.setEnabled(true);
        }
        draw.setEnabled(true);
        undo.setEnabled(false);
        if (currentPlayer instanceof AI) {
            ((AI) currentPlayer).AITurn(model);
            model.nextPlayer();
        }

    }

    public void handleFlipCards(){
        setPlayerName(model.getCurrentPlayer().getName());
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus("Flipped cards.");
    }
    @Override
    public void updateStatus(String status) {
        statusLabel.setText(status);
    }

    @Override
    public void promptForColor() {
        Player currentPlayer = model.getCurrentPlayer();
        if (currentPlayer instanceof AI) {
            Card.Color chosenColor = ((AI) currentPlayer).chooseRandomColor();
            model.setWildCardColor(chosenColor);

        } else {
            Card.Color[] colors = Arrays.stream(Card.Color.values())
                    .filter(c -> c != Card.Color.WILD && c != Card.Color.PINK &&
                            c != Card.Color.ORANGE && c != Card.Color.PURPLE && c != Card.Color.TEAL)
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
            if (chosenColor != null && !chosenColor.isEmpty()) {
                model.setWildCardColor(Card.Color.valueOf(chosenColor));
            }
        }
    }
    @Override
    public void promptForFlipColor() {
        Player currentPlayer = model.getCurrentPlayer();
        if (currentPlayer instanceof AI) {
            Card.Color chosenColor = ((AI) currentPlayer).chooseRandomFlipColor();
            model.setWildCardColor(chosenColor);
        } else {
            Card.Color[] colors = Arrays.stream(Card.Color.values())
                    .filter(c -> c != Card.Color.WILD && c != Card.Color.RED &&
                            c != Card.Color.BLUE && c != Card.Color.GREEN && c != Card.Color.YELLOW)
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
            if (chosenColor != null && !chosenColor.isEmpty()) {
                model.setWildCardColor(Card.Color.valueOf(chosenColor));
            }
        }
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
        setStatus("Invalid move! Please try again.");
    }

    private void handleDeckEmpty(){
        setStatus("Deck is empty, reshuffling");
    }

    private void handleReverseDirection(){
        setStatus("Play direction reversed");
    }

    private void handleUndo(){
        undo.setEnabled(false);
        redo.setEnabled(true);
        draw.setEnabled(true);
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus("Undid last move.");
    }
    private void handleRedo(){
        undo.setEnabled(true);
        redo.setEnabled(false);
        draw.setEnabled(false);
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus("Redid last move.");
    }
    public void setPlayerName(String name) {
        playerLabel.setText(name);
    }

}
