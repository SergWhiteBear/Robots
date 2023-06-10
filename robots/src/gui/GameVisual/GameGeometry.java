package gui.GameVisual;

import gui.GameVisual.Position.RobotPosition;
import gui.GameVisual.Position.TargetPosition;

public class GameGeometry {

    protected static double distance(TargetPosition targetPos, RobotPosition robotPos) {
        double diffX = targetPos.targetX - robotPos.posX;
        double diffY = targetPos.targetY - robotPos.posY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    protected static double angleTo(RobotPosition robotPos, TargetPosition targetPos) {
        double diffX = targetPos.targetX - robotPos.posX;
        double diffY = targetPos.targetY - robotPos.posY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected double handleAngularVelocity(double angleToTarget, final Robot robot) {
        double angularVelocity = 0;
        if (angleToTarget - robot.m_robotDirection > Math.PI) {
            angularVelocity = -robot.maxAngularVelocity;
        }
        if (angleToTarget - robot.m_robotDirection < -Math.PI) {
            angularVelocity = robot.maxAngularVelocity;
        }
        if (angleToTarget - robot.m_robotDirection < Math.PI
                && angleToTarget - robot.m_robotDirection >= 0) {
            angularVelocity = robot.maxAngularVelocity;
        }
        if (angleToTarget - robot.m_robotDirection < 0
                && angleToTarget - robot.m_robotDirection >= -Math.PI) {
            angularVelocity = -robot.maxAngularVelocity;
        }
        if (unreachable(robot)) {
            angularVelocity = 0;
        }
        return angularVelocity;
    }

    protected static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    protected static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    protected static int round(double value) {
        return (int) (value + 0.5);
    }// в геометрию


    protected boolean unreachable(final Robot robot) {
        double dx = robot.targetPos.targetX - robot.robotPos.posX;
        double dy = robot.targetPos.targetY - robot.robotPos.posY;

        double new_dx = Math.cos(robot.m_robotDirection) * dx + Math.sin(robot.m_robotDirection) * dy;
        double new_dy = Math.cos(robot.m_robotDirection) * dy - Math.sin(robot.m_robotDirection) * dx;

        double y_center = robot.maxVelocity / robot.maxAngularVelocity;
        double dist1 = (Math.sqrt(Math.pow((new_dx), 2) + Math.pow(new_dy - y_center, 2)));
        double dist2 = (Math.sqrt(Math.pow((new_dx), 2) + Math.pow(new_dy + y_center, 2)));

        return !(dist1 > robot.maxVelocity / robot.maxAngularVelocity)
                || !(dist2 > robot.maxVelocity / robot.maxAngularVelocity);
    }

}
