package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftSubsystem
{
    boolean liftUnlocked = false;
    
    /**Solenoid controlling the left front climber piston*/
    Solenoid leftFront;
    /**Solenoid controlling the right front climber piston*/
    Solenoid rightFront;
    /**Solenoid controlling the left back climber piston*/
    Solenoid leftBack;
    /**Solenoid controlling the right back climber piston*/
    Solenoid rightBack;
    /**Solenoid array controlling all climber pistons */
    Solenoid[] lift;
    /**Solenoid array controlling front climber pistons */
    Solenoid[] frontLift;
    /**Solenoid array controlling back climber pistons */
    Solenoid[] backLift;
    /**Is the front left piston active? */
    boolean flActive = false;
    /**Is the front right piston active? */
    boolean frActive = false;
    /**Is the back left piston active? */
    boolean blActive = false;
    /**Is the back right piston active? */
    boolean brActive = false;

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
    }

    /**
     * Checks bools then runs through for-loops to cycle Solenoid array elements
     * @param extendAll - Bool to extend all pistons
     * @param retractFront - Bool to retract front pistons
     * @param retractBack - Bool to retract back pistons
     */
    public void liftPistons(boolean extendAll, boolean retractFront, boolean retractBack)
    {
        if(liftUnlocked)
        {
            if(extendAll)
            {
                for(Solenoid s : lift)
                {
                    s.set(true);
                }
            }
            else if(retractFront)
            {
                for(Solenoid s : frontLift)
                {
                    s.set(false);
                }
            }
            else if(retractBack)
            {
                for(Solenoid s : backLift)
                {
                    s.set(false);
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
        flActive = leftFront.get();
        frActive = rightFront.get();
        blActive = leftBack.get();
        brActive = rightBack.get();

        SmartDashboard.putBoolean("flActive", flActive);
        SmartDashboard.putBoolean("frActive", frActive);
        SmartDashboard.putBoolean("blActive", blActive);
        SmartDashboard.putBoolean("brActive", brActive);
    }
}