package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeSubsystem
{
    /**Motor controller that controls the wheels that intake balls*/
    VictorSP intakeRollers;
    /**Motor controller on the left (when looking at front of robot) for the intake arm rotation */
    VictorSP leftIntakeRotation;
    /**Motor controller on the right (when looking at front of robot) for the intake arm rotation */
    VictorSP rightIntakeRotation;
    /**Direction of intake (true - inward, false - outward)*/
    public static boolean intakeDir = false;
    /**Previous state of direction toggle*/
    public static boolean intakeDirPrev;
    /**Toggle of intake (true - spinning, false - stopped) */
    public static boolean intakeRunTog = false;
    /**prevoius state of the running toggle*/
    public static boolean intakeRunPrev;
    /**Speed of the intake rollers*/
    private static final double ROLLER_SPEED_MAGNITUDE = 1/4;
    /**Current speed of intake rollers*/
    private static double intakeSpeed;

    IntakeSubsystem()
    {
        intakeRollers = new VictorSP(Constants.INTAKE_ROLLER_MOTOR_PORT);
        leftIntakeRotation = new VictorSP(Constants.LEFT_INTAKE_ACTUATION_MOTOR_PORT);
        rightIntakeRotation = new VictorSP(Constants.RIGHT_INTAKE_ACTUATION_MOTOR_PORT);
    }

    /**
     * Sets SmartDashboard values for IntakeSubsystem periodically
     */
    public void periodic()
    {
        intakeSpeed = intakeRollers.getSpeed();
        
        SmartDashboard.putNumber("intakeSpeed", intakeSpeed);
        SmartDashboard.putBoolean("intakeDirection", intakeDir);
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
     * @param dirTog - direction that the intake wheels is moving in, toggle between inwards(default) and outwards
     * @param runTog - if the wheels are running, toggle between on and off(default)
     */
    public void intake(boolean dirTog,boolean runTog) 
    {
        if(dirTog && !intakeDirPrev)
            intakeDir = !intakeDir;
        if(runTog && !intakeRunPrev)
            intakeRunTog = !intakeRunTog;
        if(intakeRunTog)
            intakeRollers.set(ROLLER_SPEED_MAGNITUDE*(intakeDir?-1:1));
        else
            intakeRollers.set(0);
        intakeDirPrev = dirTog;
        intakeRunPrev = runTog;
    }
}