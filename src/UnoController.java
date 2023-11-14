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
            model.drawCard();
        } else if (e.getActionCommand().contains("PLAY")){
            String temp =  e.getActionCommand();
            model.playTurn(Integer.parseInt(temp.replaceAll("[\\D]", "")));
        } else if(e.getActionCommand().equals("NEXT")){
            model.nextPlayer();
        }
    }
}