package controller;

import model.AI;
import model.Player;
import model.UnoModel;
import model.Card;
import view.UnoView;

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
            model.drawCardForPlayer();
            }
        else if (e.getActionCommand().contains("PLAY")){
            String cmd =  e.getActionCommand();
            int cardIndex = Integer.parseInt(cmd.split(" ")[1]);
            model.playTurn(cardIndex);
        } else if(e.getActionCommand().equals("NEXT")){
            model.nextPlayer();
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
        }
    }
}