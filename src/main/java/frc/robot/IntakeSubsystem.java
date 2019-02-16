package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class IntakeSubsystem
{
    /**
     * @var intakeRollers - Motor controller that controls the wheels that intake balls
     * @var leftIntakeRotation - Motor controller on the left (when looking at front of robot) for the intake arm rotation
     * @var rightIntakeRotation - Motor controller on the right (when looking at front of robot) for the intake arm rotation
     */
    VictorSP intakeRollers;
    VictorSP leftIntakeRotation;
    VictorSP rightIntakeRotation;

    IntakeSubsystem()
    {
        intakeRollers = new VictorSP(Constants.INTAKE_MOTOR_PORT);
        leftIntakeRotation = new VictorSP(Constants.LEFT_INTAKE_ROTATION_MOTOR_PORT);
        rightIntakeRotation = new VictorSP(Constants.RIGHT_INTAKE_ROTATION_MOTOR_PORT);
    }

    public void periodic()
    {
        
    }

    public void rotate(double mag)
    {
        leftIntakeRotation.set(mag);
        rightIntakeRotation.set(-mag);
    }

    public void intake(double mag) 
    {
        intakeRollers.set(mag);
    }
}