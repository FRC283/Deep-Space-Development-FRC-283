package frc.robot;


import edu.wpi.first.wpilibj.*;
public class ArmSubsystem
{
    Victor armMotor, leftWristMotor, rightWristMotor;
    //Encoder leftWristEnc, rightWristEnc;
    Solenoid gripSol, cargoSol, hatchSol;
    AnalogInput armEnc, leftWristEnc, rightWristEnc;

    double target, pos;

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

    /**
     * Periodic for the Arm Subsystem
     * 
     * If a combination of y, x, and a are pressed, the highest precedence is 
     * the leftmost argument (y > x > a)
     * 
     * @param y - Logitech 'Y' button
     * @param x - Logitech 'X' button
     * @param a - Logitech 'A' button
     */
    public void periodic(boolean y, boolean x, boolean a)
    {
        // When 'y' is true, = 90 deg; When 'x' is true, = 60 deg; When 'a' is true,= 30 deg;
        target = (y ? (90) : (x ? (60) : (a ? 30 : 0))); 

        //armEnc feeds out voltage 0-5V corresponding to degree measures; 5V = 359deg
        pos = armEnc.getVoltage()/(5/360); //Divide VoltIn by Volts per degree ~0.1389V
        rotatePeriodic(target, pos);
    }

    public void rotate(double armMag)
    {
        armMotor.set(armMag);
    }
    public void rotateInit(double degree)
    {
        
    }

    public void rotatePeriodic(double target, double position)
    {
        if(position < target)
        {
            armMotor.set(0.25);
        }
        else if(position > target)
        {
            armMotor.set(-0.25);
        }
    }
}