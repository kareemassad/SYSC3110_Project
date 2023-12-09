package controller;

import model.UnoModel;
import view.UnoViewFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class UnoController implements ActionListener, Serializable {
    private UnoModel model;
    private UnoViewFrame view;

    public UnoController(UnoModel model) {
        this.model = model;
    }

    public void linkView(UnoViewFrame view) {
        this.view = view;
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
        } else if(e.getActionCommand().equals("NEXT")) {
            model.nextPlayer();
        } else if(e.getActionCommand().equals("UNDO")) {
            model.undo();
        } else if(e.getActionCommand().equals("REDO")){
            model.redo();
        } else if (e.getActionCommand().equals("SAVE")) {
            view.saveGame();
        } else if (e.getActionCommand().equals("LOAD")) {
            view.loadGame();
        }
    }


}