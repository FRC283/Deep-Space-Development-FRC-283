package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmSubsystem
{
    Victor elbowMotor, leftWristMotor, rightWristMotor;
    //Encoder leftWristEnc, rightWristEnc;
    Solenoid gripSol, cargoSol, hatchSol;
    AnalogInput  leftWristEnc, rightWristEnc;
    Encoder elbowEnc;
    Servo cameraSer;
    Boolean isPositioning = false;
    /**Current target of elbow*/
    static double target; 
    /**Current position of elbow*/
    static double pos;
    /**Current elbow motor speed*/ 
    static double currentElbowSpeed;
    private static final double speed = .5;
    private static final int LOW_CARGO = 30;
    private static final int LOW_HATCH = 35;
    private static final int MID_CARGO = 60;
    private static final int MID_HATCH = 65;
    private static final int HIGH_CARGO = 90;
    private static final int HIGH_HATCH = 95;


    ArmSubsystem()
    {
        elbowMotor = new Victor(Constants.ELBOW_MOTOR_PORT);
        leftWristMotor = new Victor(Constants.LEFT_WRIST_MOTOR_PORT);
        rightWristMotor = new Victor(Constants.RIGHT_WRIST_MOTOR_PORT);

        elbowEnc = new Encoder(Constants.ELBOW_ENCODER_PORT_A, Constants.ELBOW_ENCODER_PORT_B);
        leftWristEnc = new AnalogInput(Constants.LEFT_WRIST_ENCODER_PORT_A);
        rightWristEnc = new AnalogInput(Constants.RIGHT_WRIST_ENCODER_PORT_A);

        gripSol = new Solenoid(Constants.GRIPPER_PORT);
        cargoSol = new Solenoid(Constants.CARGO_KICKER_PORT);
        hatchSol = new Solenoid(Constants.HATCH_RELEASE_PORT);

        cameraSer = new Servo(Constants.CAMERA_SERVO_PORT);
    
    }
    /** 
     *Set SmartDashboard values for ArmSubsystem periodically
    */
    public void periodic()
    {
        currentElbowSpeed = elbowMotor.getSpeed();
        pos = elbowEnc.get();//Divide VoltIn by Volts per degree ~0.1389V
        SmartDashboard.putNumber("ElbowMotorSpeed", currentElbowSpeed);
        SmartDashboard.putNumber("ElbowEncValue", pos);
        SmartDashboard.putNumber("currentElbowTarget", target);
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
        //rotatePeriodic(position, isHatch);
        if(isPositioning == false)
        {
            switch(position)
            {
                case 'l':
                        // if the hatch is selected, go for hatch, otherwise, go to cargo
                        target =(isHatch ? LOW_HATCH : LOW_CARGO);
                    break;
                case 'm':
                        target = (isHatch ? MID_HATCH : MID_CARGO);
                    break;
                case 'h':
                        target =(isHatch ? HIGH_HATCH : HIGH_CARGO);
                    break;
                default:
                elbowMotor.set(0);
            }
            elbowMotor.set((pos < target) ? (speed) : ((pos > target) ? (-(speed)) : 0));
            isPositioning = true;
        }
    }
    /**
     * manual rotation of arm and wrist
     * 
     * @param armMag - arm magnitude
     * @param wristMag - wrist magnitude
     */
    public void rotate(double armMag, double wristMag)
    {
        elbowMotor.set(armMag*0.5);
        leftWristMotor.set(wristMag);
        rightWristMotor.set(-wristMag);
    }


    /**
     * Periodic check to see if arm is at position
     * @value isPositioning allows or blocks the arm from moving
     */
    public void rotatePeriodic()
    {   
        //if the arm has reached its target, stop
        if(((currentElbowSpeed > 0) && (target <= pos)) ||((currentElbowSpeed < 0) && (target >= pos)))
        {
            elbowMotor.set(0);
            isPositioning = false;
        }
    }
}