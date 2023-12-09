package controller;

import model.UnoModel;
import view.UnoViewFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class UnoController implements ActionListener, Serializable {
    private UnoModel model;
    private UnoViewFrame view;

    /**
     * Default constructor for UnoController, takes
     * as input the model to be acted upon.
     *
     * @param model The model associated with the UnoController.
     */
    public UnoController(UnoModel model) {
        this.model = model;
    }

    /**
     * Associates a view component that can act upon
     * this UnoController.
     * @param view The view associated with the UnoController.
     */
    public void linkView(UnoViewFrame view) {
        this.view = view;
    }

    /**
     * Determines the action being performed within the action event
     * triggered in the view and processes the information to
     * the UnoModel so it can react accordingly.
     *
     * @param e The event to be processed
     */
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