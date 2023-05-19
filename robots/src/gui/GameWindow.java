package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import serialization.State;
import serialization.AbstractSeriazationInternalFrame;

public class GameWindow extends AbstractSeriazationInternalFrame implements State
{
    private final GameVisualizer m_visualizer;

//    private GameWindow(GameVisualizer visualizer){
//        m_visualizer = visualizer;
//    }

    public GameWindow()
    {
        super("Игровое поле", true, true, true, true, "C:\\OOP\\robots\\src\\serialization\\gameWindow.dat");
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }


    public void setSize(){
        setSize(400,400);
    }
}
