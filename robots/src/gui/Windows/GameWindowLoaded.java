package gui.Windows;

import gui.GameVisual.GameVisualizer;

import javax.swing.*;
import java.awt.*;

public class GameWindowLoaded extends GameWindow {

    public final GameVisualizer m_visualizer;

    public GameWindowLoaded() {
        super();
        setPath("C:\\Robots_KN-204\\gameWindow.dat");
        loadState();
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void SetSize() {
        setSize(getDimension());
    }

}
