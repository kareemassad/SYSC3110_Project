package controller;

import model.UnoModel;
import model.Card;
import view.UnoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoController implements ActionListener{
    private UnoModel model;

    public UnoController(UnoModel model) {
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("DRAW")){
            Card drawnCard = model.drawCard();
            model.getCurrentPlayer().addCard(drawnCard);
            SwingUtilities.invokeLater(() -> {
                for(UnoView view: model.getViews()){
                view.displayPlayerCards(model.getCurrentPlayer());
                view.updateStatus("Drew a Card");
                }
            });
        } else if (e.getActionCommand().contains("PLAY")){
            String cmd =  e.getActionCommand();
            int cardIndex = Integer.parseInt(cmd.split(" ")[1]);
            model.playTurn(cardIndex);
        } else if(e.getActionCommand().equals("NEXT")){
            model.nextPlayer();

            for(UnoView view: model.getViews()){
                view.displayPlayerCards(model.getCurrentPlayer());
            }
        }
    }
}