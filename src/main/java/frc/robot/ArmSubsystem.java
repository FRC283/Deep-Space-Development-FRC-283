package frc.robot;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;

public class ArmSubsystem
{
    Victor armMotor, leftWristMotor, rightWristMotor;
    //Encoder leftWristEnc, rightWristEnc;
    Solenoid gripSol, cargoSol, hatchSol;
    AnalogInput armEnc, leftWristEnc, rightWristEnc;
    Servo cameraSer;
    CameraServer aimArm;
    Boolean isPositioning = false;
    /** */
    double target; 
    /** */
    double pos;
    /**armEnc feeds out voltage 0-5V corresponding to degree measures; 5V = 359deg */ 
    double current;
    private static final double speed = .5;
    private static final int LOW_CARGO = 30;
    private static final int LOW_HATCH = 35;
    private static final int MID_CARGO = 60;
    private static final int MID_HATCH = 65;
    private static final int HIGH_CARGO = 90;
    private static final int HIGH_HATCH = 95;


    ArmSubsystem()
    {
        armMotor = new Victor(Constants.ARM_MOTOR_PORT);
        leftWristMotor = new Victor(Constants.LEFT_WRIST_MOTOR_PORT);
        rightWristMotor = new Victor(Constants.RIGHT_WRIST_MOTOR_PORT);

        armEnc = new AnalogInput(Constants.ARM_ENCODER_PORT_A);
        leftWristEnc = new AnalogInput(Constants.LEFT_WRIST_ENCODER_PORT_A);
        rightWristEnc = new AnalogInput(Constants.RIGHT_WRIST_ENCODER_PORT_A);

        gripSol = new Solenoid(Constants.GRIPPER_PORT);
        cargoSol = new Solenoid(Constants.CARGO_KICKER_PORT);
        hatchSol = new Solenoid(Constants.HATCH_RELEASE_PORT);

        cameraSer = new Servo(Constants.CAMERA_SERVO_PORT);
        aimArm = new CameraServer();
        
    }

    public void periodic()
    {
        current = armMotor.getSpeed();
        pos = armEnc.getVoltage()/(5/360);//Divide VoltIn by Volts per degree ~0.1389V
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
     */
    public void rotate(char position, boolean isHatch)
    {

        //

        //rotatePeriodic(position, isHatch);
        if(isPositioning == false)
        {
            switch(position)
            {
                case 'l':
                        target =(isHatch ? LOW_HATCH : LOW_CARGO);
                    break;
                case 'm':
                        target = (isHatch ? MID_HATCH : MID_CARGO);
                    break;
                case 'h':
                        target =(isHatch ? HIGH_HATCH : HIGH_CARGO);
                    break;
                default:
                    armMotor.set(0);
            }
            armMotor.set((pos < target) ? (speed) : ((pos > target) ? (-(speed)) : 0));
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
        armMotor.set(armMag*0.5);
        leftWristMotor.set(wristMag);
        rightWristMotor.set(-wristMag);
    }


    /**
     * periodic check to see if arm is at position
     */
    public void rotatePeriodic()
    {
        if(((current > 0) && (target <= pos)) ||((current < 0) && (target >= pos)))
        {
            armMotor.set(0);
        }
    }
}