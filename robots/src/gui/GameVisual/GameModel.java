package gui.GameVisual;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/*
Класс отвечающий за игровую модель
 */

public class GameModel {

    private final Robot robot = new Robot();

    private final GameGeometry gameGeometry = new GameGeometry();

    public Robot getRobot() {
        return robot;
    }

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameModel() {
        Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    protected void setTargetPosition(Point p) {
        robot.targetPos.targetX = p.x;
        robot.targetPos.targetY = p.y;
    }


    protected void onModelUpdateEvent() {

        double distance = GameGeometry.distance(robot.targetPos, robot.robotPos);

        if (distance < 0.5) {
            return;
        }
        double velocity = robot.maxVelocity;
        double angleToTarget = GameGeometry.angleTo(robot.robotPos, robot.targetPos);


        moveRobot(velocity, gameGeometry.handleAngularVelocity(angleToTarget, robot));
    }


    private void moveRobot(double velocity, double angularVelocity) {
        velocity = GameGeometry.applyLimits(velocity, 0, robot.maxVelocity);
        angularVelocity = GameGeometry.applyLimits(angularVelocity, -robot.maxAngularVelocity, robot.maxAngularVelocity);
        double newX = robot.robotPos.posX + velocity / angularVelocity *
                (Math.sin(robot.m_robotDirection + angularVelocity * (double) 10) -
                        Math.sin(robot.m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robot.robotPos.posX + velocity * (double) 10 * Math.cos(robot.m_robotDirection);
        }
        double newY = robot.robotPos.posY - velocity / angularVelocity *
                (Math.cos(robot.m_robotDirection + angularVelocity * (double) 10) -
                        Math.cos(robot.m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robot.robotPos.posY + velocity * (double) 10 * Math.sin(robot.m_robotDirection);
        }
        robot.robotPos.posX = newX;
        robot.robotPos.posY = newY;
        robot.m_robotDirection = GameGeometry.asNormalizedRadians(robot.m_robotDirection + angularVelocity * (double) 10);
    }

}
