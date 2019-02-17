package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class LiftSubsystem
{
    boolean liftUnlocked = false;
    
    Solenoid leftFront, rightFront, leftBack, rightBack;
    Solenoid[] lift, frontLift, backLift;

    LiftSubsystem()
    {
        leftFront = new Solenoid(Constants.FRONT_LEFT_LIFT_PORT);
        rightFront = new Solenoid(Constants.FRONT_RIGHT_LIFT_PORT);
        leftBack = new Solenoid(Constants.BACK_LEFT_LIFT_PORT);
        rightBack = new Solenoid(Constants.BACK_RIGHT_LIFT_PORT);

        lift = new Solenoid[] {leftFront, rightFront, leftBack, rightBack}; 
        frontLift = new Solenoid[] {leftFront, rightFront};
        backLift = new Solenoid[] {leftBack, rightBack};
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
}