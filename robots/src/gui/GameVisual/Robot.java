package gui.GameVisual;

import gui.GameVisual.Position.RobotPosition;
import gui.GameVisual.Position.TargetPosition;

public class Robot {

    public RobotPosition robotPos = new RobotPosition();
    public TargetPosition targetPos = new TargetPosition();

    public volatile double m_robotDirection = 0;
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.001;

}
