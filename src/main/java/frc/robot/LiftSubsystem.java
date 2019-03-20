package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class LiftSubsystem
{
    private static final double LIFT_MOTOR_SPEED_MAGNITUDE = .25;
    private static final double P_CONST = .1;
    private static final double P_UPDATE = .1;
    private static final int liftErr = 10;
    private static int avg = 0;
    private static final int lvl2plat = 0;
    private static final int lvl3plat = 0;
    private static boolean isLifting = false;
    private static int target = 0;
    

    boolean liftUnlocked = false;
    VictorSPX frontLeft;
    VictorSPX frontRight;
    VictorSPX backLeft;
    VictorSPX backRight;
    
    DigitalInput frontLeftLimitSwitch;
    DigitalInput frontRightLimitSwitch;
    AnalogInput backLeftLimitSwitch;
    DigitalInput backRightLimitSwitch;
    Encoder frontLeftLiftEnc;
    Encoder frontRightLiftEnc;
    Encoder backLeftLiftEnc;
    Encoder backRightLiftEnc;
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

        frontLeft = new VictorSPX(Constants.FRONT_LEFT_LIFT_PORT);
        frontRight = new VictorSPX(Constants.FRONT_RIGHT_LIFT_PORT);
        backLeft = new VictorSPX(Constants.BACK_LEFT_LIFT_PORT);
        backRight = new VictorSPX(Constants.BACK_RIGHT_LIFT_PORT);

        frontLeftLimitSwitch = new DigitalInput(Constants.LIFT_FRONT_LEFT_LIMIT_SWITCH);
        frontRightLimitSwitch = new DigitalInput(Constants.LIFT_FRONT_RIGHT_LIMIT_SWITCH);
        backLeftLimitSwitch = new AnalogInput(Constants.LIFT_BACK_LEFT_LIMIT_SWITCH);
        backRightLimitSwitch = new DigitalInput(Constants.LIFT_BACK_RIGHT_LIMIT_SWITCH);
        frontLeftLiftEnc = new Encoder(Constants.FRONT_LEFT_ENCODER_PORT_A,Constants.FRONT_LEFT_ENCODER_PORT_B);
        frontRightLiftEnc = new Encoder(Constants.FRONT_RIGHT_ENCODER_PORT_A,Constants.FRONT_RIGHT_ENCODER_PORT_B);
        backLeftLiftEnc = new Encoder(Constants.BACK_LEFT_ENCODER_PORT_A,Constants.BACK_LEFT_ENCODER_PORT_B);
        backRightLiftEnc = new Encoder(Constants.BACK_RIGHT_ENCODER_PORT_A,Constants.BACK_RIGHT_ENCODER_PORT_B);



    }

    /**
     * Checks bools then runs through for-loops to cycle Solenoid array elements
     * @param extendAll - Bool to extend all pistons
     * @param retractFront - Bool to retract front pistons
     * @param retractBack - Bool to retract back pistons
     */
    public void actuateLift(boolean extendAll, boolean retractFront, boolean retractBack)
    {
        //unlockLift() must be called before the lift can be actuated
        if(liftUnlocked)
        {
            if(extendAll)
            {
                if(!retractFront)
                {
                    frontLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE + .1);
                    frontRight.set(ControlMode.PercentOutput, (-LIFT_MOTOR_SPEED_MAGNITUDE) - .2);
                }
                
                if(!retractBack)
                {
                    backLeft.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE);
                    backRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE);
                }    
            }

            if(retractFront)
            {
                if(frontRightLimitSwitch.get())
                {
                    frontRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE + .2);
                }
                else
                {
                    frontRight.set(ControlMode.PercentOutput, 0);
                }

                if(frontLeftLimitSwitch.get())
                {
                    frontLeft.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE - .1);
                }
                else
                {
                    frontLeft.set(ControlMode.PercentOutput, 0);
                }
            }
            

            if(retractBack)
            {
                if(backRightLimitSwitch.get())
                {
                    backRight.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                else
                {
                    backRight.set(ControlMode.PercentOutput, 0);
                }

                if(backLeftLimitSwitch.getVoltage() < .5)
                {
                    backLeft.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                else
                {
                    backLeft.set(ControlMode.PercentOutput, 0);
                }
            }

            if(!extendAll && !retractFront)
            {
                frontLeft.set(ControlMode.PercentOutput, 0);
                frontRight.set(ControlMode.PercentOutput, 0);
            }


            if(!extendAll && !retractBack)
            {
                backLeft.set(ControlMode.PercentOutput, 0);
                backRight.set(ControlMode.PercentOutput, 0);
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
        SmartDashboard.putNumber("Back Left Limit Switch", backLeftLimitSwitch.getVoltage());
        SmartDashboard.putBoolean("Back Right Limit Switch", backRightLimitSwitch.get());
        liftPerodic();
    }
    public void liftPerodic()
    {
        avg = (frontLeftLiftEnc.get() + 
                frontRightLiftEnc.get() +
                backLeftLiftEnc.get() +
                backRightLiftEnc.get()) / 4;
        if(isLifting)
        {
            if(backLeftLimitSwitch.getValue() > 0 || (backLeftLiftEnc.get() <= target - liftErr) || (backLeftLiftEnc.get() >= target + liftErr))
            {
                backLeft.set(ControlMode.PercentOutput, 0);
            }
            else
            {
                if(backLeftLiftEnc.get() > (avg + liftErr))
                {
                    backLeft.set(ControlMode.PercentOutput,LIFT_MOTOR_SPEED_MAGNITUDE-P_UPDATE);
                }
                else if(backLeftLiftEnc.get() < (avg - liftErr))
                {
                    backLeft.set(ControlMode.PercentOutput,LIFT_MOTOR_SPEED_MAGNITUDE+P_UPDATE);
                }
                else
                {
                    backLeft.set(ControlMode.PercentOutput,LIFT_MOTOR_SPEED_MAGNITUDE);
                }
            }
        }
    }
}