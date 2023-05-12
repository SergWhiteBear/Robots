package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

public class GameModel extends JPanel {

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private int count;
    private static final double duration = 10;
    private static final double maxVelocity = 0.2;;
    private static final double maxAngularVelocity = 0.001;

    private final GameWindow gameWindow;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public GameModel(GameWindow gameWindow, CoordinateWindow coordinateWindow) {
        this.gameWindow = gameWindow;
        support.addPropertyChangeListener(coordinateWindow);
    }

    protected int getTargetPositionX(){
        return m_targetPositionX;
    }
    protected int getTargetPositionY(){
        return m_targetPositionY;
    }
    protected double getRobotPositionX(){
        return m_robotPositionX;
    }
    protected double getRobotPositionY(){
        return m_robotPositionY;
    }
    protected double getRobotDirection(){
        return m_robotDirection;
    }
    protected void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }


    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }


    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected void onModelUpdateEvent()
    {
        if (distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY) < 0.5)
        {
            m_robotPositionX = m_targetPositionX;
            m_robotPositionY = m_targetPositionY;
            return;
        }
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection)
        {
            angularVelocity = maxAngularVelocity;
        }
        else if (angleToTarget < m_robotDirection)
        {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(angularVelocity);
    }
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private void moveRobot(double angularVelocity) {

        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX;
        double newY;
        if (angularVelocity != 0) {
            newX = m_robotPositionX + maxVelocity / angularVelocity *
                    (Math.sin(m_robotDirection + angularVelocity * duration) -
                            Math.sin(m_robotDirection));
            newY = m_robotPositionY - maxVelocity / angularVelocity *
                    (Math.cos(m_robotDirection + angularVelocity * duration) -
                            Math.cos(m_robotDirection));
        } else {
            newX = m_robotPositionX + maxVelocity * duration * Math.cos(m_robotDirection);
            newY = m_robotPositionY + maxVelocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newRobotDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);

        int width = gameWindow.getWidth() * 2 - 20;
        if (newX < 0) {
            newX += width;
        } else if (newX > width) {
            newX -= width;
        }

        int height = gameWindow.getHeight() * 2 - 40;
        if (newY < 0) {
            newY += height;
        } else if (newY > height) {
            newY -= height;
        }

        if ((int) m_robotPositionX != (int) newX || (int) m_robotPositionY != (int) newY) {
            count++;
            if (count == 8) {
                support.firePropertyChange("coordinates",
                        new Point((int) m_robotPositionX, (int) m_robotPositionY), new Point((int) newX, (int) newY)
                );
                count = 0;
            }
        }

            m_robotPositionX = newX;
            m_robotPositionY = newY;
            m_robotDirection = newRobotDirection;
        }


        private static double asNormalizedRadians(double angle)
        {
            while (angle < 0) {
                angle += 2 * Math.PI;
            }
            while (angle >= 2 * Math.PI) {
                angle -= 2 * Math.PI;
            }
            return angle;
        }
    }
