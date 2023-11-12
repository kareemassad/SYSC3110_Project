import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoView extends JFrame{
    private Uno model;
    private JFrame frame;
    private JLabel topCardLabel;
    private JPanel pCardPanel;
    private JButton draw;
    private JButton nextPlayer;

    public UnoView(){
        frame = new JFrame("UNO Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        topCardLabel = new JLabel();
        pCardPanel = new JPanel();
        draw = new JButton("Draw Card");
        nextPlayer = new JButton("Next Player");

        draw.addActionListener(e -> handleDrawB());
        nextPlayer.addActionListener(e -> handleNextPlayerB());

        frame.setLayout(new BorderLayout());
        frame.add(topCardLabel, BorderLayout.NORTH);
        frame.add(pCardPanel, BorderLayout.CENTER);
        frame.add(draw, BorderLayout.EAST);
        frame.add(nextPlayer, BorderLayout.WEST);

        frame.setVisible(true);
    }

    public void updateTopCardLabel(Card topCard) {
        topCardLabel.setText("Top Card: " + topCard.toString());
    }

    public void displayPlayerCards(Player player) {
        pCardPanel.removeAll();

        for (int i = 0; i < player.getSize(); i++) {
            Card card = player.getCard(i);
            JButton cardButton = new JButton(card.toString());
            pCardPanel.add(cardButton);
        }

        pCardPanel.revalidate();
        pCardPanel.repaint();
    }

    private void playerSetup(){
        boolean validSetup = false;

        //Loop until a valid number of players has been entered.
        while(!validSetup) {
            int numberOfPlayers = 0;
            JPanel mainPanel = new JPanel();

            //Set up a JOptionPane that will get user input for number of players.
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
                //Get the name of all of the players and add them to the game.
                addPlayerNames(numberOfPlayers);
                validSetup = true;
            }
        }
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
                result = JOptionPane.showOptionDialog(this, playerAddPanel, "Add additional players", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            }

        }
    }

    public void enableDrawButton(boolean enable) {
        draw.setEnabled(enable);
    }

    public void enableNextPlayerButton(boolean enable) {
        nextPlayer.setEnabled(enable);
    }

    private void handleDrawB() {

    }

    private void handleNextPlayerB() {

    }

    public static void main(String[] args) {
        new UnoView();
    }
}
