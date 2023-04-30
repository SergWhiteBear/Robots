package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import serialization.SerializationInternalFrame;

public class GameWindow extends SerializationInternalFrame
{
    private final GameVisualizer m_visualizer;

//    private GameWindow(GameVisualizer visualizer){
//        m_visualizer = visualizer;
//    }

    public GameWindow()
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

}
