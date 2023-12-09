package view;

import controller.UnoController;
import controller.UnoEvent;
import model.AI;
import model.Card;
import model.Player;
import model.UnoModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UnoViewFrame extends JFrame implements UnoView {
    private UnoModel model;
    private JLabel topCardLabel, playerLabel ,statusLabel;
    private JPanel pCardPanel;
    private JButton draw;
    private JButton nextPlayer;
    private JButton undo;
    private JButton redo;
    private UnoController uc;
    private ArrayList<JButton> buttons;

    /**
     * Establishes a UNO GUI displaying all necessary components
     * to play UNO Flip.
     *
     * @param uc The controller that reacts to GUI inputs.
     * @param model The model that simulates the UNO game.
     */
    public UnoViewFrame(UnoController uc, UnoModel model){
        super("UNO Game");
        this.uc = uc;
        this.model = model;
        this.buttons = new ArrayList<>();

        this.setLayout(new BorderLayout());
        topCardLabel = new JLabel();
        playerLabel = new JLabel();

        pCardPanel = new JPanel();
        JToolBar buttonPanel = new JToolBar();

        JPanel actionButtons = new JPanel(new BorderLayout());

        draw = new JButton("Draw Card");
        nextPlayer = new JButton("Next Player");
        undo = new JButton("Undo");
        redo = new JButton("Redo");
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");

        draw.setActionCommand("DRAW");
        draw.addActionListener(uc);
        nextPlayer.setActionCommand("NEXT");
        nextPlayer.addActionListener(uc);
        undo.setActionCommand("UNDO");
        undo.addActionListener(uc);
        redo.setActionCommand("REDO");
        redo.addActionListener(uc);
        save.setActionCommand("SAVE");
        save.addActionListener(uc);
        load.setActionCommand("LOAD");
        load.addActionListener(uc);

        this.add(playerLabel, BorderLayout.NORTH);
        this.add(topCardLabel, BorderLayout.CENTER);
        this.add(actionButtons, BorderLayout.SOUTH);

        statusLabel = new JLabel("Welcome to UNO FLIP");
        statusLabel.setBorder(BorderFactory.createTitledBorder("Status"));
        statusLabel.setVisible(true);

        buttonPanel.add(nextPlayer);
        buttonPanel.add(undo);
        buttonPanel.add(redo);
        buttonPanel.add(save);
        buttonPanel.add(load);

        actionButtons.add(statusLabel, BorderLayout.NORTH);
        actionButtons.add(pCardPanel, BorderLayout.CENTER);
        actionButtons.add(buttonPanel, BorderLayout.EAST);
        actionButtons.add(draw, BorderLayout.WEST);

        buttonPanel.setFloatable(false);
        undo.setEnabled(false);
        redo.setEnabled(false);

        this.add(nextPlayer, BorderLayout.EAST);

        playerSetup();
        model.dealInitialCards();
        updateTopCardLabel(model.getTopCard());
        displayPlayerCards(model.getPlayers().get(0));

        JScrollPane scroll = new JScrollPane(pCardPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        actionButtons.add(scroll, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Retrieves the image associated with an UNO card
     * and displays it on the topCardLabel.
     *
     * @param topCard The card to be displayed.
     */
    public void updateTopCardLabel(Card topCard) {
        String imgPath = "src/images/" + topCard.getColor().toString().toLowerCase() + "_" + topCard.getType().toString().toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(imgPath);
        Image image = icon.getImage();
        Image resized = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resized);
        topCardLabel.setIcon(icon);
        topCardLabel.setText(topCard.getColor().toString() + " " + topCard.getType().toString());
    }

    /**
     * Retrieves the image associate with each card in the
     * hand of the given player and displays them on the GUI.
     *
     * @param player The player whose hand will be displayed.
     */
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

    /**
     * Prompts the player to establish the number
     * of players who will join the UNO game.
     */
    private void playerSetup() {
        boolean validSetup = false;

        while (!validSetup) {
            String[] options = {"2", "3", "4"};
            Object selectedOption = JOptionPane.showInputDialog(this, "Select number of players:", "Setup",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (selectedOption != null) {
                String selectedOptionString = (String) selectedOption;
                int numberOfPlayers = Integer.parseInt(selectedOptionString.split(" ")[0]);

                addPlayerNames(numberOfPlayers);
            }
            validSetup = true;
        }
    }

    /**
     * Writes the status to be displayed on the GUI.
     *
     * @param status The status to be displayed.
     */
    public void setStatus(String status){
        statusLabel.setText(status);
    }

    /**
     * Prompts the user to name each of the players given
     * by the parameter before establishing them as players
     * in the UNO model.
     *
     * @param numberOfPlayers The number of players given.
     */
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

    /**\
     * Toggles the status of the draw button
     * on the GUI depending on the input given.
     *
     * @param enable The state the button should be changed to.
     */
    public void enableDrawButton(boolean enable) {
        draw.setEnabled(enable);
    }

    /**
     * Toggles the status of the nextPlayer button
     * on the GUI depending on the input given.
     *
     * @param enabled The state the button should be changed to.
     */
    public void enableNextPlayerButton(boolean enabled) {nextPlayer.setEnabled(enabled);
    }

    /**
     * Main executable that establishes the mode, controller,
     * and view before linking them together.
     *
     * @param args Default parameter of main().
     */
    public static void main(String[] args) {
        UnoModel model = new UnoModel();
        UnoController controller = new UnoController(model);
        UnoViewFrame view = new UnoViewFrame(controller, model);
        controller.linkView(view);
        model.addUnoView(view);
        view.setVisible(true);
    }

    /**
     * Given an event that occurs, processes the view
     * based on the status of the event.
     *
     * @param e The UnoEvent to be processed.
     */
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

    /**
     * The Player_Won status was triggered, checks the
     * total score of the player before disabling
     * buttons on the GUI.
     *
     * @param winningPlayer The player who has won.
     * @param lastCard The last card that was played.
     */
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

    /**
     * The Card_Player status was triggered, checks if
     * the model should be reversed before updating the
     * cards and status on the GUI. Enables/disables certain
     * buttons to avoid unwanted player behaviour.
     *
     * @param player The player who played the card.
     * @param playedCard The card that was played.
     */
    private void handleCardPlayed(Player player, Card playedCard) {
        if (playedCard.getType() == Card.Type.REVERSE) {
            model.reverseDirection();
            setStatus("Reverse card played by " + player.getName() + ". Direction reversed.");
        } else {
            updateTopCardLabel(playedCard);
            displayPlayerCards(player);
            setStatus("Played: " + playedCard);
        }
        for(JButton button: buttons){
            button.setEnabled(false);
        }
        draw.setEnabled(false);
        undo.setEnabled(true);
        redo.setEnabled(false);
        nextPlayer.setEnabled(true);
    }

    /**
     * The Drew_Card status was triggered, updates the
     * GUI status and disables further drawing of cards
     * and allowing player to undo.
     *
     * @param player The player who drew a card.
     */
    private void handleCardDrawn(Player player){
        setStatus(player.getName() + " drew a card.");
        draw.setEnabled(false);
        undo.setEnabled(true);
        redo.setEnabled(false);
        nextPlayer.setEnabled(true);
        displayPlayerCards(player);
    }

    /**
     * The Player_Turn_Changed status was triggered, gets the
     * information of the new player and displays it on the GUI
     * while enabling/disabling certain buttons to avoid
     * unwanted player behaviour.
     */
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
        redo.setEnabled(false);
        nextPlayer.setEnabled(false);
        if (currentPlayer instanceof AI) {
            ((AI) currentPlayer).AITurn(model);
            model.nextPlayer();
        }

    }

    /**
     * The Flip_Cards status was triggered, refreshes
     * the information on the GUI to reflect the current
     * state of the UnoModel.
     */
    public void handleFlipCards(){
        setPlayerName(model.getCurrentPlayer().getName());
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus("Flipped cards.");
    }

    /**
     * Updates the status label within the GUI with
     * the current status of UnoModel.
     *
     * @param status The status to be displayed.
     */
    @Override
    public void updateStatus(String status) {
        statusLabel.setText(status);
    }

    /**
     * Opens a prompt allowing the player to choose from
     * the possible colours of the game, before setting the
     * card to that colour. That wild card can then be
     * displayed as the topCard.
     */
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
            colorOptions(colors);
        }
    }

    private void colorOptions(Card.Color[] colors) {
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

    /**
     * Opens a prompts asking the user to choose from
     * the possible flipped colour and proceeds to set
     * the played wild card to that colour.
     */
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
            colorOptions(colors);
        }
    }

    /**
     * The Uno_Announced status was triggered, updates
     * the status on the GUI to reflect the state.
     *
     */
    private void handleUnoAnnounced(Player player){
        setStatus(player.getName() + "announced UNO!");
    }

    /**
     * The Game_Over status was triggered, updates the
     * status and disables certain buttons on the GUI
     * to reflect a finished game.
     */
    private void handleGameOver(){
        setStatus("Game Over.");
        enableDrawButton(false);
        enableNextPlayerButton(false);
    }

    /**
     * The Invalid_Move status was triggered, sets the
     * status of the GUI to inform a player that the
     * current move is invalid.
     */
    private void handleInvalidMove(){
        setStatus("Invalid move! Please try again.");
    }

    /**
     * The Deck_Empty status was triggered, sets the status
     * of the GUI to inform players that the current deck
     * has been reshuffled.
     */
    private void handleDeckEmpty(){
        setStatus("Deck is empty, reshuffling");
    }

    /**
     * The Reverse_Direction status was triggered, sets
     * the status of the GUI to inform players that the
     * current game direction is now reversed.
     */
    private void handleReverseDirection(){
        setStatus("Play direction reversed");
    }

    /**
     * The Undo status was triggered, alters the state
     * of several buttons to avoid unwanted results
     * and refreshes the GUI to reflect the current
     * topCard/hand of the player.
     */
    private void handleUndo(){
        undo.setEnabled(false);
        redo.setEnabled(true);
        draw.setEnabled(true);
        nextPlayer.setEnabled(false);
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus("Undid last move.");
    }

    /**
     * The Redo status was triggered, alters the state
     * of several buttons to avoid unwanted player inputs
     * and refreshes the GUI to reflect the current
     * topCard/hand of the player.
     */
    private void handleRedo(){
        undo.setEnabled(true);
        redo.setEnabled(false);
        draw.setEnabled(false);
        nextPlayer.setEnabled(true);
        pCardPanel.removeAll();
        displayPlayerCards(model.getCurrentPlayer());
        updateTopCardLabel(model.getTopCard());
        setStatus("Redid last move.");
    }

    /**
     * Set the player label on the GUI to
     * the given string parameter.
     * @param name The name to be set.
     */
    public void setPlayerName(String name) {
        playerLabel.setText(name);
    }

    /**
     * Allows a user to save the current state of the
     * game with a specified filename, and checks to
     * ensure that the filename is valid.
     */
    public void saveGame() {
        String fileName = JOptionPane.showInputDialog(this, "Enter filename to save:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            model.saveGame(fileName + ".ser");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid filename or no filename entered.");
        }
    }

    /**
     * Allows a user to load a previously saved state
     * of the game by entering a specified filename,
     * and prompts the model to load the filename if
     * it is detected to be valid.
     */
    public void loadGame() {
        String fileName = JOptionPane.showInputDialog("Enter filename for deserialization:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            if (!fileName.toLowerCase().endsWith(".ser")) {
                fileName += ".ser";
            }

            model.loadGame(fileName);
        } else {
            JOptionPane.showMessageDialog(this, "Please provide a valid file name.");
        }
    }

}
