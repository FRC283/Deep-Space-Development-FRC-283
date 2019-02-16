package frc.robot;

import edu.wpi.first.wpilibj.*;

public class ArmSubsystem
{
    Victor armMotor, leftWristMotor, rightWristMotor;
    //Encoder leftWristEnc, rightWristEnc;
    Solenoid gripSol, cargoSol, hatchSol;
    AnalogInput armEnc, leftWristEnc, rightWristEnc;
    Boolean isPositioning = false;

    double target; 
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
                    if (isHatch)
                        armMotor.set((pos < LOW_HATCH) ? (speed) : ((pos > LOW_HATCH) ? (-(speed)) : 0));
                    else
                        armMotor.set((pos < LOW_CARGO) ? (speed) : ((pos > LOW_CARGO) ? (-(speed)) : 0));
                    break;
                case 'm':
                    if (isHatch)
                        armMotor.set((pos < MID_HATCH) ? (speed) : ((pos > MID_HATCH) ? (-(speed)) : 0));
                    else
                        armMotor.set((pos < MID_CARGO) ? (speed) : ((pos > MID_CARGO) ? (-(speed)) : 0));
                    break;
                case 'h':
                    if (isHatch)
                        armMotor.set((pos < HIGH_HATCH) ? (speed) : ((pos > HIGH_HATCH) ? (-(speed)) : 0));
                    else
                        armMotor.set((pos < HIGH_CARGO) ? (speed) : ((pos > HIGH_CARGO) ? (-(speed)) : 0));
                    break;
                default:
                    armMotor.set(0);
            }
            isPositioning = true;
        }
    }

    public void rotate(double armMag)
    {
        armMotor.set(armMag);
    }



    public void rotatePeriodic()
    {
        if(current != 0 && ( &&    ))
        {
            armMotor.set(0.25);
        }
    }
}