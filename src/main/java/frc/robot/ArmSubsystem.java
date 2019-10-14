package frc.robot;

import java.util.HashMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmSubsystem
{
    //Number of seconds to keep the kicker extended  
    private static final double CARGO_KICKER_TIMER_DURATION = 1;
    private static final double HATCH_KICKER_TIMER_DURATION = 1;
    private Timer cargoKickerTimer;
    private Timer hatchKickerTimer;
    private boolean grippersClosed = false;
    private boolean extendCargoKicker = false;
    private boolean extendHatchKicker = false;


    private Victor elbowMotor, leftWristMotor, rightWristMotor;
    //Encoder leftWristEnc, rightWristEnc;
    private Solenoid gripSol, cargoSol, hatchSol;
    DigitalInput elbowUpperLimitSwitch;
    DigitalInput elbowLowerLimitSwitch;
    Encoder elbowEncoder;
    //Servo cameraServo;
    private Boolean isPositioning = false;
    /**Current target of elbow*/
    private static double target;
    /**Current position of elbow*/
    private static double pos;
    /**Current elbow motor speed*/ 
    private static double currentElbowSpeed;
    /** Coefficient to scale the rate of speed for the P (Proportional) control for the 
     *  elbow movement */
    private static final double P_COEFFICIENT = .01;
    private double error = 0;
    /** The bottom of the range where the elbow will stop moving because it is close enough to target */
    private static final double ERROR_LOWER_ACCEPTABLE_VALUE = -2;
    /** The top of the range where the elbow will stop moving because it is close enough to target */
    private static final double ERROR_UPPER_ACCEPTABLE_VALUE = 2;
    private static final double LEFT_WRIST_SPEED_MULTIPLIER = 0.25;
    private static final double RIGHT_WRIST_SPEED_MULTIPLIER = 0.25;
    private static final double MAX_ELBOW_SPEED_AUTO = .25;
    private static final double MAX_ELBOW_SPEED_MANUAL = .5;
    private static final int LOW_CARGO = -441;
    private static final int LOW_HATCH = -243;
    private static final int MID_CARGO = -805;
    private static final int MID_HATCH = -727;
    private static final int HIGH_CARGO = -1253;//TEMPORARY
    private static final int HIGH_HATCH = -1193;
    private static final int OFFSET = 25;

    ArmSubsystem()
    {
        //Motor Controllers
        elbowMotor = new Victor(Constants.ELBOW_MOTOR_PORT);
        leftWristMotor = new Victor(Constants.LEFT_WRIST_MOTOR_PORT);
        rightWristMotor = new Victor(Constants.RIGHT_WRIST_MOTOR_PORT);

        //Encoders
        elbowEncoder = new Encoder(Constants.ELBOW_ENCODER_PORT_A, Constants.ELBOW_ENCODER_PORT_B);
        //leftWristEnc = new AnalogInput(Constants.LEFT_WRIST_ENCODER_PORT_A);
        //rightWristEnc = new AnalogInput(Constants.RIGHT_WRIST_ENCODER_PORT_A);

        //Limit Switches
        elbowUpperLimitSwitch = new DigitalInput(Constants.ELBOW_UPPER_LIMIT_SWITCH);
        elbowLowerLimitSwitch = new DigitalInput(Constants.ELBOW_LOWER_LIMIT_SWITCH);

        //Wrist Solenoids
        gripSol = new Solenoid(Constants.GRIPPER_PORT);
        cargoSol = new Solenoid(Constants.CARGO_KICKER_PORT);
        hatchSol = new Solenoid(Constants.HATCH_RELEASE_PORT);

        //cameraServo = new Servo(Constants.CAMERA_SERVO_PORT);

        //Timers for solenoids
        cargoKickerTimer = new Timer();
        hatchKickerTimer = new Timer();
        cargoKickerTimer.reset();
        hatchKickerTimer.reset();
    
    }

    /**
     * Called every teleop periodic cycle
     * 
     * Calls wristPeriodic() and rotatePeriodic()
     * @see this.wristPeriodic()
     */
    public void periodic()
    {
        currentElbowSpeed = elbowMotor.getSpeed();
        SmartDashboard.putNumber("ElbowMotorSpeed", currentElbowSpeed);
        SmartDashboard.putNumber("ElbowEncValue", pos);
        SmartDashboard.putNumber("currentElbowTarget", target);
        SmartDashboard.putNumber("error", target - pos);
        SmartDashboard.putNumber("P_Coefficient * error", P_COEFFICIENT * (target - pos));
        wristPeriodic();
        //rotatePeriodic();
    }

    /**
     * Positioning function for the Arm
     * 
     * If a combination of are pressed, the highest precedence is 
     * the leftmost argument
     * characters:
     * @param position - based on the character input, arm will go to position based on encoder
     *      'l' - lowest position (default)
     *      'm' - middle position
     *      'h' - highest position
     * @param isHatch - If true, goes to corresponding Hatch Position, otherwise it goes to Cargo position
     * @value isPositioning allows or blocks the arm from moving
     */
    public void rotate(char position, boolean isHatch)
    {
            switch(position)
            {
                case 'l':
                        //TODO: Implement locking
                        //If intake up, move to low
                        //Lock the intake, add it to list of locked systems
                        //Move to low
                        // if the hatch is selected, go for hatch, otherwise, go to cargo
                        target = ((isHatch ? LOW_HATCH : LOW_CARGO) + OFFSET);
                        isPositioning = true;
                    break;
                case 'm':
                        target = ((isHatch ? MID_HATCH : MID_CARGO) + OFFSET);
                        isPositioning = true;
                    break;
                case 'h':
                        target = ((isHatch ? HIGH_HATCH : HIGH_CARGO) + OFFSET);
                        isPositioning = true;
                    break;
                case 'b':
                        target = 0;
                        isPositioning = true;
                default:
                    elbowMotor.set(0);
            }
    }
    /**
     * Determines position for arm based on button combinations
     * @param isHatch - True to go for hatch position
     * @param goForLowPos - Low position for hatch and cargo
     * @param goForMidPos - middle position for hatch and cargo
     * @param goForHighPos - high position for hatch and cargo
     */
    public void rotate(boolean isHatch, boolean goToStartPos, boolean goForLowPos, boolean goForMidPos, boolean goForHighPos)
    {
        if(goForLowPos)
        {
            this.rotate('l', isHatch);
        }
        else if(goForMidPos)
        {
            this.rotate('m', isHatch);
        }
        else if(goForHighPos)
        {
            this.rotate('h', isHatch);
        }
        else if (goToStartPos)
        {
            this.rotate('b', isHatch);
        }
    }

    /**
     * manual rotation of arm and wrist
     * 
     * @param armMag - arm magnitude
     * @param wristMag - wrist magnitude
     */
    public void manualRotate(double armMag, double wristMag)
    {
        if(armMag > Constants.DEAD_ZONE || armMag < (-Constants.DEAD_ZONE))
        {
            //armMag is a value between 0 and 1, this makes the max target change +/- 10 
            target = target + (10 * armMag);
            
            
            armMag = armMag-Constants.DEAD_ZONE;
            armMag = Math.max(Math.min(armMag, MAX_ELBOW_SPEED_MANUAL), -MAX_ELBOW_SPEED_MANUAL);
            elbowMotor.set(armMag);
            isPositioning = false;
            //*/
        }
        else
            elbowMotor.set(0);

        if(wristMag > Constants.DEAD_ZONE || wristMag < (-Constants.DEAD_ZONE))
        {
            leftWristMotor.set(LEFT_WRIST_SPEED_MULTIPLIER * wristMag);
            rightWristMotor.set(RIGHT_WRIST_SPEED_MULTIPLIER * -wristMag);
        }
        else
        {
            leftWristMotor.set(0);
            rightWristMotor.set(0);
        }
        
    }


    /**
     * Periodic check to see if arm is at position
     * @value isPositioning allows or blocks the arm from moving
     */
    public void rotatePeriodic()
    {   
        pos = elbowEncoder.get();
        error = P_COEFFICIENT * (target - pos);

        SmartDashboard.putNumber("Error before check", error);

        if(!elbowLowerLimitSwitch.get())
        {
            elbowEncoder.reset();
        }

        if ((error > ERROR_UPPER_ACCEPTABLE_VALUE || error < ERROR_LOWER_ACCEPTABLE_VALUE)
            && (elbowEncoder.get() <= 0))
        {
            //Force error to be within -.1 and .1 (or whatever value designated by MAX_ELBOW_SPEED_AUTO)
            error = Math.max(Math.min(error, MAX_ELBOW_SPEED_AUTO), -MAX_ELBOW_SPEED_AUTO);
            
            if(error > 0 && !elbowLowerLimitSwitch.get())
            {
                elbowMotor.set(error);
            }
            else
            {
                elbowMotor.set(0);
            }
            
        }
        
        SmartDashboard.putNumber("Position Target", target);
        SmartDashboard.putBoolean("Positioning", isPositioning);
        SmartDashboard.putNumber("MotorValue", error);
    }

    /**
     * Periodic function for controlling the smooth actuation of solenoids
     */
    public void wristPeriodic()
    {
        gripSol.set(grippersClosed);
        cargoSol.set(extendCargoKicker);
        hatchSol.set(extendHatchKicker);

        if(extendCargoKicker && cargoKickerTimer.hasPeriodPassed(CARGO_KICKER_TIMER_DURATION))
        {
            extendCargoKicker = false;
            cargoKickerTimer.reset();
            cargoKickerTimer.stop();
        }

        if(extendHatchKicker && hatchKickerTimer.hasPeriodPassed(HATCH_KICKER_TIMER_DURATION))
        {
            extendHatchKicker = false;
            hatchKickerTimer.reset();
            hatchKickerTimer.stop();
        }
    }

    /**
     * This is the function to be called inside the Robot class
     * for controlling the solenoids on the wrist
     * 
     * @param toggleGrippers - Passed as the parameter to actuateGrippers
     * @see this.actuateGrippers()
     * @param extendCargo - Passed as the parameter to actuateCargoKicker
     * @see this.actuateCargoKicker()
     * @param extendHatch - Passed as the parameter to actuateHatckKicker
     * @see this.actuateHatchKicker()
     */
    public void actuateWrist(boolean toggleGrippers, boolean extendCargo, boolean extendHatch)
    {
        this.actuateGrippers(toggleGrippers);
        this.actuateCargoKicker(extendCargo);
        this.actuateHatchKicker(extendHatch);
    }

    /**
     * Function for toggling the grippers between open and closed
     * 
     * @param toggle - Boolean value; when set to true, inverts the state of the grippers
     */
    public void actuateGrippers(boolean toggle)
    {
        if(toggle)
        {
            grippersClosed = !grippersClosed;
        }
    }

    /**
     * Function for setting the kicker cylinders for the cargo (inside the grippers) 
     * to the extended state
     * 
     * @see this.wristPeriodic() for where the kicker cylinders for the cargo are set to the
     * retracted state after time period (in seconds) CARGO_KICKER_TIMER_DURATION
     * 
     * @param extend - Boolean value; if true, will set extendCargoKicker to true and the periodic
     * function will activate the kicker solenoid.
     */
    public void actuateCargoKicker(boolean extend)
    {
        if(extend == true && extendCargoKicker == false)
        {
            extendCargoKicker = true;
            cargoKickerTimer.start();
        }
    }

    /**
     * Function for setting the kicker/release cylinders for the hatch (inside the grippers) 
     * to the extended state
     * 
     * @see this.wristPeriodic() for where the kicker/release cylinders for the hatch are set to the
     * retracted state after time period (in seconds) HATCH_KICKER_TIMER_DURATION
     * 
     * @param extend - Boolean value; if true, will set extendHatchKicker to true and the periodic
     * function will activate the kicker solenoid.
     */
    public void actuateHatchKicker(boolean extend)
    {
        if(extend == true && extendHatchKicker == false)
        {
            extendHatchKicker = true;
            hatchKickerTimer.start();
        }
    }
}