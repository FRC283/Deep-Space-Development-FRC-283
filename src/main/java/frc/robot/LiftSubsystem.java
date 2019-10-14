package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class LiftSubsystem
{
    private static final double LIFT_MOTOR_SPEED_MAGNITUDE = .75;
    private static final double P_CONST = .1;
    private static final double P_UPDATE = .1;
    private static final int liftErr = 10;
    private static int avg = 0;
    private static final int lvl2plat = 0;
    private static final int lvl3plat = 0;
    private static boolean isLifting = false;
    private static int target = 0;
    /*
        | Motor     |Signal | Direction
          FrontLeft |   -   | Down
          FrontRight|   +   | Down
          BackLeft  |   +   | Down
          BackRight |   -   | Down
    */

    boolean liftUnlocked = false;
    
    VictorSPX frontLeft;
    VictorSPX frontRight;
    VictorSPX backLeft;
    VictorSPX backRight;
    
    DigitalInput topFrontLeftLimitSwitch;
    DigitalInput topFrontRightLimitSwitch;

    //AnalogInput  topBackLeftLimitSwitch;
    DigitalInput  topBackLeftLimitSwitch;

    DigitalInput topBackRightLimitSwitch;
    
    DigitalInput bottomFrontLeftLimitSwitch;
    DigitalInput bottomFrontRightLimitSwitch;
    DigitalInput bottomBackLeftLimitSwitch;
    DigitalInput bottomBackRightLimitSwitch;

    /*Encoder fL_LiftEnc;
    Encoder fR_LiftEnc;
    Encoder bL_LiftEnc;
    Encoder bR_LiftEnc;//*/

    int loopCount;

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

        loopCount = 0;

        backLeft = new VictorSPX(Constants.BACK_LEFT_LIFT_PORT);
        backRight = new VictorSPX(Constants.BACK_RIGHT_LIFT_PORT);
        frontRight = new VictorSPX(Constants.FRONT_RIGHT_LIFT_PORT);
        frontLeft = new VictorSPX(Constants.FRONT_LEFT_LIFT_PORT);

        backRight.setInverted(true);
        backLeft.setInverted(true);
        


        topFrontLeftLimitSwitch = new DigitalInput(Constants.LIFT_TOP_FRONT_LEFT_LIMIT_SWITCH);
        topFrontRightLimitSwitch = new DigitalInput(Constants.LIFT_TOP_FRONT_RIGHT_LIMIT_SWITCH);
        //topBackLeftLimitSwitch = new AnalogInput(Constants.LIFT_TOP_BACK_LEFT_LIMIT_SWITCH);
        topBackLeftLimitSwitch = new DigitalInput(Constants.LIFT_TOP_BACK_LEFT_LIMIT_SWITCH);
        topBackRightLimitSwitch = new DigitalInput(Constants.LIFT_TOP_BACK_RIGHT_LIMIT_SWITCH);

        bottomFrontLeftLimitSwitch = new DigitalInput(Constants.LIFT_BOTTOM_FRONT_LEFT_LIMIT_SWITCH);
        bottomFrontRightLimitSwitch = new DigitalInput(Constants.LIFT_BOTTOM_FRONT_RIGHT_LIMIT_SWITCH);
        bottomBackLeftLimitSwitch = new DigitalInput(Constants.LIFT_BOTTOM_BACK_LEFT_LIMIT_SWITCH);
        bottomBackRightLimitSwitch = new DigitalInput(Constants.LIFT_BOTTOM_BACK_RIGHT_LIMIT_SWITCH);

        /*fL_LiftEnc = new Encoder(Constants.FRONT_LEFT_ENCODER_PORT_A,Constants.FRONT_LEFT_ENCODER_PORT_B);
        fR_LiftEnc = new Encoder(Constants.FRONT_RIGHT_ENCODER_PORT_A,Constants.FRONT_RIGHT_ENCODER_PORT_B);
        bL_LiftEnc = new Encoder(Constants.BACK_LEFT_ENCODER_PORT_A,Constants.BACK_LEFT_ENCODER_PORT_B);
        bR_LiftEnc = new Encoder(Constants.BACK_RIGHT_ENCODER_PORT_A,Constants.BACK_RIGHT_ENCODER_PORT_B);//*/

        Object[][] liftArr = {{frontLeft,  frontRight, backLeft,   backRight},
                              /*{fL_LiftEnc, fR_LiftEnc, bL_LiftEnc, bR_LiftEnc},*/
                              {topFrontLeftLimitSwitch, topFrontRightLimitSwitch, topBackLeftLimitSwitch, topBackRightLimitSwitch}};
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
                    if(topFrontLeftLimitSwitch.get())
                        frontLeft.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE - 0.2);
                    else
                        frontLeft.set(ControlMode.PercentOutput, 0);

                    if(topFrontRightLimitSwitch.get())
                        frontRight.set(ControlMode.PercentOutput, (LIFT_MOTOR_SPEED_MAGNITUDE + 0.15));
                    else
                        frontRight.set(ControlMode.PercentOutput, 0);
                }
                
                if(!retractBack)
                {
                    if(topBackLeftLimitSwitch.get())
                        backLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE + 0.2);
                    else
                        backLeft.set(ControlMode.PercentOutput, 0);

                    if(topBackRightLimitSwitch.get())
                        backRight.set(ControlMode.PercentOutput, (LIFT_MOTOR_SPEED_MAGNITUDE)+.05);
                    else
                        backRight.set(ControlMode.PercentOutput, 0);
                }    
            }

            if(retractFront)
            {   
                if(bottomFrontRightLimitSwitch.get())
                {
                    frontRight.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                else
                {
                    frontRight.set(ControlMode.PercentOutput, 0);
                }

                if(bottomFrontLeftLimitSwitch.get())
                {
                    frontLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                else
                {
                    frontLeft.set(ControlMode.PercentOutput, 0);
                }
                
            }
            

            if(retractBack)
            {
                if(bottomBackRightLimitSwitch.get())
                {
                    backRight.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                else
                {
                    backRight.set(ControlMode.PercentOutput, 0);
                }

                //if(topBackLeftLimitSwitch.getVoltage() < .5)
                //{
                //    backLeft.set(ControlMode.PercentOutput, -LIFT_MOTOR_SPEED_MAGNITUDE);
                //}



                if(bottomBackLeftLimitSwitch.get())
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
    public void unlockLift(boolean activate, DriveSubsystem drive)
    {
        if(activate)
        {
            liftUnlocked = true;
            drive.setDriveCoefficient(.75);
        }
            
    }

    /**Sets SmartDashboard values for LiftSubsystem periodically*/
    public void periodic()
    {
        SmartDashboard.putBoolean("Lift Unlocked", liftUnlocked);
        SmartDashboard.putBoolean("Top Front Left Limit Switch",    topFrontLeftLimitSwitch.get());
        SmartDashboard.putBoolean("Top Front Right Limit Switch",   topFrontRightLimitSwitch.get());
        SmartDashboard.putBoolean("Top Back Left Limit Switch",     topBackLeftLimitSwitch.get());
        SmartDashboard.putBoolean("Top Back Right Limit Switch",    topBackRightLimitSwitch.get());
        SmartDashboard.putBoolean("Bottom Front Left Limit Switch",    bottomFrontLeftLimitSwitch.get());
        SmartDashboard.putBoolean("Bottom Front Right Limit Switch",   bottomFrontRightLimitSwitch.get());
        SmartDashboard.putBoolean("Bottom Back Left Limit Switch",     bottomBackLeftLimitSwitch.get());
        SmartDashboard.putBoolean("Bottom Back Right Limit Switch",    bottomBackRightLimitSwitch.get());
        //liftPerodic();
    }
    /*public void liftPerodic()
    {
        avg = (fL_LiftEnc.get() + 
                fR_LiftEnc.get() +
                bL_LiftEnc.get() +
                bR_LiftEnc.get()) / 4;
        if(isLifting)
        {
            if(topBackLeftLimitSwitch.get() || (bL_LiftEnc.get() <= target - liftErr) || (bL_LiftEnc.get() >= target + liftErr))
            {
                backLeft.set(ControlMode.PercentOutput, 0);
            }
            if(topBackRightLimitSwitch.get() || (bR_LiftEnc.get() <= target - liftErr) || (bR_LiftEnc.get() >= target + liftErr))
            {
                backRight.set(ControlMode.PercentOutput, 0);
            }
            if(topFrontRightLimitSwitch.get() || (fL_LiftEnc.get() <= target - liftErr) || (fL_LiftEnc.get() >= target + liftErr))
            {
                frontRight.set(ControlMode.PercentOutput, 0);
            }
            if(topFrontLeftLimitSwitch.get() || (fR_LiftEnc.get() <= target - liftErr) || (fR_LiftEnc.get() >= target + liftErr))
            {
                frontLeft.set(ControlMode.PercentOutput, 0);
            }
            else
            {
                if(bL_LiftEnc.get() > (avg + liftErr))
                {
                    backLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE-P_UPDATE);
                }
                else if(bL_LiftEnc.get() < (avg - liftErr))
                {
                    backLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE+P_UPDATE);
                }
                else
                {
                    backLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                //////////////////////////////////////
                if(bR_LiftEnc.get() > (avg + liftErr))
                {
                    backRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE-P_UPDATE);
                }
                else if(bR_LiftEnc.get() < (avg - liftErr))
                {
                    backRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE+P_UPDATE);
                }
                else
                {
                    backRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                //////////////////////////////////////
                if(fL_LiftEnc.get() > (avg + liftErr))
                {
                    frontLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE-P_UPDATE);
                }
                else if(bL_LiftEnc.get() < (avg - liftErr))
                {
                    frontLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE+P_UPDATE);
                }
                else
                {
                    frontLeft.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE);
                }
                //////////////////////////////////////
                if(fR_LiftEnc.get() > (avg + liftErr))
                {
                    frontRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE-P_UPDATE);
                }
                else if(fR_LiftEnc.get() < (avg - liftErr))
                {
                    frontRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE+P_UPDATE);
                }
                else
                {
                    frontRight.set(ControlMode.PercentOutput, LIFT_MOTOR_SPEED_MAGNITUDE);
                }
            }
        }
    }//*/
}