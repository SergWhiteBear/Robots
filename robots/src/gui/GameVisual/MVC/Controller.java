package gui.GameVisual.MVC;

import javax.swing.*;

public class Controller implements InteractionWithModel {

    private JPanel currWorkPanel;

    public JPanel getCurrentWorkPanel() {
        return currWorkPanel;
    }

    public Controller(final JPanel panel) {
        this.currWorkPanel = panel;
    }
}
