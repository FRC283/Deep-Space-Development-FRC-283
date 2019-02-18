package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class IntakeSubsystem
{
    /**
     * @var intakeRollers - Motor controller that controls the wheels that intake balls
     * @var leftIntakeRotation - Motor controller on the left (when looking at front of robot) for the intake arm rotation
     * @var rightIntakeRotation - Motor controller on the right (when looking at front of robot) for the intake arm rotation
     * @var ROLLER_SPEED_MAGNITUDE - speed of the intake rollers
     */
    VictorSP intakeRollers;
    VictorSP leftIntakeRotation;
    VictorSP rightIntakeRotation;
    private static final double ROLLER_SPEED_MAGNITUDE = 1/4;

    IntakeSubsystem()
    {
        intakeRollers = new VictorSP(Constants.INTAKE_ROLLER_MOTOR_PORT);
        leftIntakeRotation = new VictorSP(Constants.LEFT_INTAKE_ACTUATION_MOTOR_PORT);
        rightIntakeRotation = new VictorSP(Constants.RIGHT_INTAKE_ACTUATION_MOTOR_PORT);
    }

    public void periodic()
    {
        
    }

    /**
     * controls the rotation of the intake arms
     * @param mag - magnitude of the rotation speed
     */
    public void rotate(double mag)
    {
        leftIntakeRotation.set(mag);
        rightIntakeRotation.set(-mag);
    }
    /**
     * controls the intake on the robot
     * @param dir - direction that the intake wheels is moving in, toggle between inwards(default) and outwards
     * @param running - if the wheels are running, toggle between on and off(default)
     */
    public void intake(boolean dir,boolean running) 
    {
        if(running)
            intakeRollers.set(ROLLER_SPEED_MAGNITUDE*(dir?-1:1));
    }
}