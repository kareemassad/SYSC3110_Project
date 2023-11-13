import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoController {
    private Uno model;

    public UnoController(Uno model) {
        this.model = model;
    }

    public ActionListener createDrawButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDrawB();
            }
        };
    }

    public ActionListener createNextPlayerButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextPlayerB();
            }
        };
    }

    public void handleDrawB() {
        model.drawCard();
    }

    public void handleNextPlayerB() {
        model.nextPlayer();
    }
}