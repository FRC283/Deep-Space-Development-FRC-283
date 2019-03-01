package frc.robot;

import java.util.HashMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeSubsystem
{
    /**Encoder for telling the position of the intake arms*/
    private Encoder rotateEncoder;
    private static final int INTAKE_UPPER_ENCODER_LIMIT = 100;
    private static final double BALL_POSITION_ENCODER_VALUE = 28450;//Tested at 28744
    private static boolean rotatingToBall = false;
    private double rotationError;
    /**Motor controller that controls the wheels that intake balls*/
    private VictorSP intakeRollers;
    /**Motor controller on the left (when looking at front of robot) for the intake arm rotation */
    private VictorSP leftIntakeRotation;
    /**Motor controller on the right (when looking at front of robot) for the intake arm rotation */
    private VictorSP rightIntakeRotation;
    /**Limit switch for the upper limit for the intake */
    private DigitalInput intakeUpperLimitSwitch;
    /**Direction of intake (true - inward, false - outward)*/
    public static boolean intakeDir = false;
    /**Previous state of direction toggle*/
    public static boolean intakeDirPrev;
    /**Toggle of intake (true - spinning, false - stopped) */
    public static boolean intakeRunTog = false;
    /**previous state of the running toggle*/
    public static boolean intakeRunPrev;
    /**Speed of the intake rollers*/
    private static final double ROLLER_SPEED_MAGNITUDE = 1;
    private static final double ROTATION_SPEED_MAGNITUDE = .3;
    /**Current speed of intake rollers*/
    private static double intakeSpeed;

    private double pos;
    private static final double P_COFFECIENT = .5;

    private HashMap<String, Object> loadedSubsystems;

    IntakeSubsystem()
    {
        rotateEncoder = new Encoder(Constants.INTAKE_ROTATION_ENCODER_PORT_A, Constants.INTAKE_ROTATION_ENCODER_PORT_B);
        loadedSubsystems = new HashMap<String, Object>();
        intakeUpperLimitSwitch = new DigitalInput(Constants.INTAKE_UPPER_LIMIT_SWITCH);
        intakeRollers = new VictorSP(Constants.INTAKE_ROLLER_MOTOR_PORT);
        leftIntakeRotation = new VictorSP(Constants.LEFT_INTAKE_ACTUATION_MOTOR_PORT);
        rightIntakeRotation = new VictorSP(Constants.RIGHT_INTAKE_ACTUATION_MOTOR_PORT);
    }

    /**
     * Sets SmartDashboard values for IntakeSubsystem periodically
     */
    public void periodic()
    {
        if(!intakeUpperLimitSwitch.get())
        {
            rotateEncoder.reset();
        }

        intakeSpeed = intakeRollers.getSpeed();
        pos = (double) rotateEncoder.get();

        if(rotatingToBall)
        {
            if(rotateEncoder.get() > BALL_POSITION_ENCODER_VALUE + 10
                || rotateEncoder.get() < BALL_POSITION_ENCODER_VALUE - 10 )
            {
                rotationError = BALL_POSITION_ENCODER_VALUE - pos;
                rotationError =  Math.max(Math.min(rotationError, ROTATION_SPEED_MAGNITUDE), -ROTATION_SPEED_MAGNITUDE);
                this.rotate(P_COFFECIENT*rotationError);
            }
            else
            {
                rotatingToBall = false;
                this.rotate(0);
            }
        }
        
        SmartDashboard.putNumber("intakeSpeed", intakeSpeed);
        SmartDashboard.putBoolean("intakeDirection", intakeDir);
        SmartDashboard.putNumber("Intake Rotation Encoder Val", rotateEncoder.get());
        SmartDashboard.putBoolean("Intake Rotation Limit Switch", intakeUpperLimitSwitch.get());
        SmartDashboard.putNumber("P_COEFF*rotationError", P_COFFECIENT*rotationError);
    }

    public void rotateTo(Character pos)
    {
        switch(pos)
        {
            case 'b':
                rotatingToBall = true;
                break;
            default:
                rotatingToBall = false;
        }
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

    public void rotate(boolean moveDown, boolean moveUp, boolean moveToBall)
    {
        if (moveDown)
        {
            rotatingToBall = false;
            this.rotate(ROTATION_SPEED_MAGNITUDE);
        }
        else if(moveUp &&  intakeUpperLimitSwitch.get())
        {
            rotatingToBall = false;
            this.rotate(-ROTATION_SPEED_MAGNITUDE);
        }
        else if (moveToBall)
        {
            this.rotateTo('b');
        }
        else 
        {
            this.rotate(0);
        }
    }
    
    public boolean isIntakeUp()
    {
        if (!intakeUpperLimitSwitch.get() || rotateEncoder.get() < 2000)
            return true;
        else
            return false;
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

    public void loadSubsystem(String name, Object subsystem)
    {
        loadedSubsystems.put(name, subsystem);
    }

    public Object getSubsystem(String name)
    {
        return loadedSubsystems.get(name);
    }
}