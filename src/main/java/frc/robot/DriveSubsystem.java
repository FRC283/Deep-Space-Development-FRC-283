package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveSubsystem
{
    TalonSRX frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive;

    

    DriveSubsystem()
    {
        frontLeftDrive = new TalonSRX(Constants.FRONT_LEFT_DRIVE_PORT);
        backLeftDrive = new TalonSRX(Constants.BACK_LEFT_DRIVE_PORT);
        frontRightDrive = new TalonSRX(Constants.FRONT_RIGHT_DRIVE_PORT);
        backRightDrive = new TalonSRX(Constants.BACK_RIGHT_DRIVE_PORT);
    }
    /**
     * Function for controlling the motor controllers on the robot.
     * Uses split arcade controls.
     * 
     * @param lYMag - forward backward control
     * @param lXMag - strafe (side to side) control
     * @param rXMag - rotation control
     * 
     */
    public void drive(double lYMag, double lXMag, double rXMag)
    {
        frontLeftDrive.set(ControlMode.PercentOutput, (rXMag + lYMag + lXMag) * 1);
		frontRightDrive.set(ControlMode.PercentOutput, (-rXMag + lYMag - lXMag) * -1);
		backLeftDrive.set(ControlMode.PercentOutput, (-rXMag - lYMag + lXMag) * -1);
		backRightDrive.set(ControlMode.PercentOutput, (rXMag - lYMag - lXMag) * 1);	
    }

}