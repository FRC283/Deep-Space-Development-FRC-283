package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class LiftSubsystem
{
    boolean liftUnlocked = false;
    TalonSRX frontLeft;
    TalonSRX frontRight;
    VictorSPX backLeft;
    VictorSPX backRight;
    
    DigitalInput frontLeftLimitSwitch;
    DigitalInput frontRightLimitSwitch;
    AnalogInput backLeftLimitSwitch;
    DigitalInput backRightLimitSwitch;

    LiftSubsystem()
    {
        /*leftFront = new Solenoid(Constants.FRONT_LEFT_LIFT_PORT);
        rightFront = new Solenoid(Constants.FRONT_RIGHT_LIFT_PORT);
        leftBack = new Solenoid(Constants.BACK_LEFT_LIFT_PORT);
        rightBack = new Solenoid(Constants.BACK_RIGHT_LIFT_PORT);

        lift = new Solenoid[] {leftFront, rightFront, leftBack, rightBack}; 
        frontLift = new Solenoid[] {leftFront, rightFront};
        backLift = new Solenoid[] {leftBack, rightBack};
        */

        frontLeft = new TalonSRX(Constants.FRONT_LEFT_LIFT_PORT);
        frontRight = new TalonSRX(Constants.FRONT_RIGHT_LIFT_PORT);
        backLeft = new VictorSPX(Constants.BACK_LEFT_LIFT_PORT);
        backRight = new VictorSPX(Constants.BACK_RIGHT_LIFT_PORT);

        frontLeftLimitSwitch = new DigitalInput(Constants.LIFT_FRONT_LEFT_LIMIT_SWITCH);
        frontRightLimitSwitch = new DigitalInput(Constants.LIFT_FRONT_RIGHT_LIMIT_SWITCH);
        backLeftLimitSwitch = new AnalogInput(Constants.LIFT_BACK_LEFT_LIMIT_SWITCH);
        backRightLimitSwitch = new DigitalInput(Constants.LIFT_BACK_RIGHT_LIMIT_SWITCH);
        frontLeft.follow(frontRight);
        backLeft.follow(backRight);
    }

    /**
     * Checks bools then runs through for-loops to cycle Solenoid array elements
     * @param extendAll - Bool to extend all pistons
     * @param retractFront - Bool to retract front pistons
     * @param retractBack - Bool to retract back pistons
     */
    public void actuateLift(boolean extendAll, boolean retractFront, boolean retractBack)
    {
        if(liftUnlocked)
        {
            if(extendAll)
            {
                if(!retractFront)
                {
                    frontRight.set(ControlMode.PercentOutput, .25);
                }
                
                if(!retractBack)
                {
                    backRight.set(ControlMode.PercentOutput, .25);
                }    
            }

        if(retractFront)
            {
                if(frontRightLimitSwitch.get())
                {
                    frontRight.set(ControlMode.PercentOutput, .25);
                }
            }

            if(retractBack)
            {
                if(backRightLimitSwitch.get())
                {
                    backRight.set(ControlMode.PercentOutput, .25);
                }
            }
        }
        
    }

    /**
     * This unlocks the ability activate the lift pistons to accidental activation
     * @param activate - boolean toggled to true for first time the button is pressed; stays true until power cycle
     */
    public void unlockLift(boolean activate)
    {
        if(activate = true)
            liftUnlocked = true;
    }

    /**Sets SmartDashboard values for LiftSubsystem periodically*/
    public void periodic()
    {
        SmartDashboard.putBoolean("Lift Unlocked", liftUnlocked);
        SmartDashboard.putBoolean("Front Left Limit Switch", frontLeftLimitSwitch.get());
        SmartDashboard.putBoolean("Front Right Limit Switch", frontRightLimitSwitch.get());
        SmartDashboard.putBoolean("Back Left Limit Switch", backLeftLimitSwitch.getVoltage());
        SmartDashboard.putNumber("Back Right Limit Switch", backRightLimitSwitch.get());
    }
}