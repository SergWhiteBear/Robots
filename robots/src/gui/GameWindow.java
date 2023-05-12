package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import serialization.SerializationInternalFrame;

public class GameWindow extends SerializationInternalFrame
{

//    private GameWindow(GameVisualizer visualizer){
//        m_visualizer = visualizer;
//    }

    public GameWindow(CoordinateWindow coordinatesWindow)
    {
        super("Игровое поле", true, true, true, true);
        GameVisualizer m_visualizer = new GameVisualizer(this, coordinatesWindow);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(CoordinateWindow coordinatesWindow, int wight, int height) {
        this(coordinatesWindow);
        setSize(wight, height);
    }

}
