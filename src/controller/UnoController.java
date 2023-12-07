package controller;

import model.AI;
import model.Player;
import model.UnoModel;
import model.Card;
import view.UnoView;
import view.UnoViewFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.Serializable;

public class UnoController implements ActionListener, Serializable {
    private UnoModel model;
    private UnoModel loadedModel;
    private UnoViewFrame view;

    public UnoController(UnoModel model) {
        this.model = model;
    }

    public void linkView(UnoViewFrame view) {
        this.view = view;
        this.view.addSerializeListener(new SerializeGameListener());
        this.view.addDeserializeListener(new DeserializeGameListener());
    }


    private class SerializeGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog(view, "Enter filename for serialization:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                model.saveGame(fileName + ".ser");
                JOptionPane.showMessageDialog(view, "Game saved successfully!");
            } else {
                JOptionPane.showMessageDialog(view, "Invalid filename or no filename entered.");
            }
        }
    }

    private class DeserializeGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog(view, "Enter filename for deserialization:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                if (!fileName.toLowerCase().endsWith(".ser")) {
                    fileName += ".ser";
                }
                loadedModel.loadGame(fileName);
                if (loadedModel != null) {
                    model = loadedModel;
                    JOptionPane.showMessageDialog(view, "Game loaded successfully!");
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to load the game. Check the file or try again.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please provide a valid file name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public ActionListener getSerializeListener() {
        return new SerializeGameListener();
    }

    public ActionListener getDeserializeListener() {
        return new DeserializeGameListener();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("DRAW")){
            model.drawCardForPlayer();
            }
        else if (e.getActionCommand().contains("PLAY")){
            String cmd =  e.getActionCommand();
            int cardIndex = Integer.parseInt(cmd.split(" ")[1]);
            model.playTurn(cardIndex);
        } else if(e.getActionCommand().equals("NEXT")){
            model.nextPlayer();
            /**
            Player currentPlayer = model.getCurrentPlayer();
            for(UnoView view: model.getViews()){
                view.setPlayerName(currentPlayer.getName());
                view.displayPlayerCards(model.getCurrentPlayer());
           }
            if (currentPlayer instanceof AI) {
                AI aiPlayer = (AI) currentPlayer;
                Card chosenCard = aiPlayer.AICard(model);
                if (chosenCard != null) {
                    int cardIndex = currentPlayer.findCardIndex(chosenCard);
                    model.playTurn(cardIndex);
                } else {
                    model.drawCardForPlayer();
                }
            }
             */
        }
    }


}