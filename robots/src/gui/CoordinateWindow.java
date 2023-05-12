package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

public class CoordinateWindow extends JInternalFrame implements PropertyChangeListener {
    private final TextArea coordinateContent;
    public CoordinateWindow(){
        super("Координаты робота", true, true, true, true);
        coordinateContent = new TextArea("");
        coordinateContent.setSize(100, 100);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinateContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Point p = (Point) evt.getNewValue();
        String content = "x is: " + p.x + "\ny is: " + p.y;
        coordinateContent.setText(content);
        coordinateContent.invalidate();
    }
}
