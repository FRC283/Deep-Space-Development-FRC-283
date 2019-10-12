package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Scheme.Schema;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class LiftSubsystem
{
    private static final double LIFT_MOTOR_SPEED_MAGNITUDE = .75;
    /*
        | Motor     |Signal | Direction
          FrontLeft |   -   | Down
          FrontRight|   +   | Down
          BackLeft  |   +   | Down
          BackRight |   -   | Down
    */

    private boolean liftUnlocked = false;
    
    private VictorSPX frontLeft;
    private VictorSPX frontRight;
    private VictorSPX backLeft;
    private VictorSPX backRight;
    
    private DirLimit topFrontLeftLimit;
    private DirLimit topFrontRightLimit;
    private DirLimit  topBackLeftLimit;
    private DirLimit topBackRightLimit;
    private DirLimit bottomFrontLeftLimit;
    private DirLimit bottomFrontRightLimit;
    private DirLimit bottomBackLeftLimit;
    private DirLimit bottomBackRightLimit;

    private int loopCount;

    LiftSubsystem()
    {

        loopCount = 0;

        backLeft = new VictorSPX(Constants.BACK_LEFT_LIFT_PORT);
        backRight = new VictorSPX(Constants.BACK_RIGHT_LIFT_PORT);
        frontRight = new VictorSPX(Constants.FRONT_RIGHT_LIFT_PORT);
        frontLeft = new VictorSPX(Constants.FRONT_LEFT_LIFT_PORT);

        backRight.setInverted(true);
        backLeft.setInverted(true);



        topFrontLeftLimit     = new DirLimit(-1, Constants.LIFT_TOP_FRONT_LEFT_LIMIT_SWITCH, true);
        topFrontRightLimit    = new DirLimit(1, Constants.LIFT_TOP_FRONT_RIGHT_LIMIT_SWITCH, true);
        topBackLeftLimit      = new DirLimit(1, Constants.LIFT_TOP_BACK_LEFT_LIMIT_SWITCH, true);
        topBackRightLimit     = new DirLimit(1, Constants.LIFT_TOP_BACK_RIGHT_LIMIT_SWITCH, true);

        bottomFrontLeftLimit  = new DirLimit(1, Constants.LIFT_BOTTOM_FRONT_LEFT_LIMIT_SWITCH, true);
        bottomFrontRightLimit = new DirLimit(-1, Constants.LIFT_BOTTOM_FRONT_RIGHT_LIMIT_SWITCH, true);
        bottomBackLeftLimit   = new DirLimit(-1, Constants.LIFT_BOTTOM_BACK_LEFT_LIMIT_SWITCH, true);
        bottomBackRightLimit  = new DirLimit(-1, Constants.LIFT_BOTTOM_BACK_RIGHT_LIMIT_SWITCH, true);

        /*fL_LiftEnc = new Encoder(Constants.FRONT_LEFT_ENCODER_PORT_A,Constants.FRONT_LEFT_ENCODER_PORT_B);
        fR_LiftEnc = new Encoder(Constants.FRONT_RIGHT_ENCODER_PORT_A,Constants.FRONT_RIGHT_ENCODER_PORT_B);
        bL_LiftEnc = new Encoder(Constants.BACK_LEFT_ENCODER_PORT_A,Constants.BACK_LEFT_ENCODER_PORT_B);
        bR_LiftEnc = new Encoder(Constants.BACK_RIGHT_ENCODER_PORT_A,Constants.BACK_RIGHT_ENCODER_PORT_B);//*/

    }

    /**
     * Checks bools then runs through for-loops to cycle Solenoid array elements
     * @param extendAll - Bool to extend all pistons
     * @param retractFront - Bool to retract front pistons
     * @param retractBack - Bool to retract back pistons
     */
    @Schema(value = Utilities283.LOGITECH_RIGHT_BUMPER)
    public void actuateLift(boolean extendAll, boolean retractFront, boolean retractBack)
    {
        //unlockLift() must be called before the lift can be actuated
        if(liftUnlocked)
        {
            if(extendAll)
            {
                if(!retractFront)
                {
                        frontLeft.set(ControlMode.PercentOutput, topFrontLeftLimit.filter(-LIFT_MOTOR_SPEED_MAGNITUDE - 0.2));
                        frontRight.set(ControlMode.PercentOutput, topFrontRightLimit.filter(LIFT_MOTOR_SPEED_MAGNITUDE + 0.15));
                }
                
                if(!retractBack)
                {
                        backLeft.set(ControlMode.PercentOutput, topBackLeftLimit.filter(LIFT_MOTOR_SPEED_MAGNITUDE + 0.2));
                        backRight.set(ControlMode.PercentOutput, topBackRightLimit.filter(LIFT_MOTOR_SPEED_MAGNITUDE+.05));
                }    
            }

            if(retractFront)
            {   

                    frontRight.set(ControlMode.PercentOutput, bottomFrontRightLimit.filter(-LIFT_MOTOR_SPEED_MAGNITUDE));
                    frontLeft.set(ControlMode.PercentOutput, bottomFrontLeftLimit.filter(LIFT_MOTOR_SPEED_MAGNITUDE));
            }
            

            if(retractBack)
            {
                    backRight.set(ControlMode.PercentOutput, bottomBackRightLimit.filter(-LIFT_MOTOR_SPEED_MAGNITUDE));
                    backLeft.set(ControlMode.PercentOutput, bottomBackLeftLimit.filter(-LIFT_MOTOR_SPEED_MAGNITUDE));
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
        SmartDashboard.putBoolean("Top Front Left Limit Switch",    topFrontLeftLimit.get());
        SmartDashboard.putBoolean("Top Front Right Limit Switch",   topFrontRightLimit.get());
        SmartDashboard.putBoolean("Top Back Left Limit Switch",     topBackLeftLimit.get());
        SmartDashboard.putBoolean("Top Back Right Limit Switch",    topBackRightLimit.get());
        SmartDashboard.putBoolean("Bottom Front Left Limit Switch",    bottomFrontLeftLimit.get());
        SmartDashboard.putBoolean("Bottom Front Right Limit Switch",   bottomFrontRightLimit.get());
        SmartDashboard.putBoolean("Bottom Back Left Limit Switch",     bottomBackLeftLimit.get());
        SmartDashboard.putBoolean("Bottom Back Right Limit Switch",    bottomBackRightLimit.get());
        //liftPerodic();
    }

}