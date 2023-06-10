package gui.Windows;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import gui.GameVisual.GameVisualizer;
import serialization.State;
import serialization.AbstractSeriazationInternalFrame;

public class GameWindow extends AbstractSeriazationInternalFrame implements State {
    private final GameVisualizer m_visualizer;

//    private GameWindow(GameVisualizer visualizer){
//        m_visualizer = visualizer;
//    }

    public GameWindow() {
        super("Игровое поле", true, true, true, true, "C:\\Robots_KN-204\\gameWindow.dat");
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }


    public void setSize() {
        setSize(400, 400);
    }
}
