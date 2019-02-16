package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class LiftSubsystem
{
    boolean upDown = false;
    
    Solenoid leftFront, rightFront, leftBack, rightBack;
    Solenoid[] lift;
    LiftSubsystem()
    {
        lift = new Solenoid[] {leftFront, rightFront, leftBack, rightBack}; 
    }
}