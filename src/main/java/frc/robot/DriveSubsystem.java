package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem
{
    double driveCoefficient = 1.0;
    TalonSRX frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive;
    Utilities283 utils;
    DriveSubsystem()
    {
        frontLeftDrive = new TalonSRX(Constants.FRONT_LEFT_DRIVE_PORT);
        backLeftDrive = new TalonSRX(Constants.BACK_LEFT_DRIVE_PORT);
        frontRightDrive = new TalonSRX(Constants.FRONT_RIGHT_DRIVE_PORT);
        backRightDrive = new TalonSRX(Constants.BACK_RIGHT_DRIVE_PORT);

        frontLeftDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backLeftDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        frontRightDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backRightDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        frontLeftDrive.configOpenloopRamp(0);
        backLeftDrive.configOpenloopRamp(0);
        frontRightDrive.configOpenloopRamp(0);
        backRightDrive.configOpenloopRamp(0);
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
        frontLeftDrive.set(ControlMode.PercentOutput, Utilities283.rescale(Constants.DEAD_ZONE, 1.0, 0.0, 1.0, (rXMag - lYMag + lXMag) * (1 * driveCoefficient)));		
        frontRightDrive.set(ControlMode.PercentOutput, Utilities283.rescale(Constants.DEAD_ZONE, 1.0, 0.0, 1.0, (-rXMag - lYMag - lXMag) * (-1 * driveCoefficient)));
		backLeftDrive.set(ControlMode.PercentOutput, Utilities283.rescale(Constants.DEAD_ZONE, 1.0, 0.0, 1.0, (-rXMag + lYMag + lXMag) * (-1 * driveCoefficient)));
        backRightDrive.set(ControlMode.PercentOutput, Utilities283.rescale(Constants.DEAD_ZONE, 1.0, 0.0, 1.0, (rXMag + lYMag - lXMag) * (1 * driveCoefficient)));
    }

    /**Sets SmartDashboard values for DriveSubsystem periodically */
    public void periodic()
    {
        SmartDashboard.putNumber("FrontLeftDrive Encoder Value", frontLeftDrive.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("BackLeftDrive Encoder Value", backLeftDrive.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("FrontRightDrive Encoder Value", frontRightDrive.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("BackRightDrive Encoder Value", backRightDrive.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("FrontLeftDrive Encoder Velocity", frontLeftDrive.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("BackLeftDrive Encoder Velocity", backLeftDrive.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("FrontRightDrive Encoder Velocity", frontRightDrive.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("BackRightDrive Encoder Velocity", backRightDrive.getSelectedSensorVelocity(0));
        //SmartDashboard.putNumber("FrontLeftDrive Magnitude", frontLeftDrive.get());
        //SmartDashboard.putNumber("BackLeftDrive Magnitude", backLeftDrive.get());
        //SmartDashboard.putNumber("FrontRightDrive Magnitude", frontRightDrive.get());
        //SmartDashboard.putNumber("BackRightDrive Magnitude", backRightDrive;
        
    }

    public void setDriveCoefficient(double val)
    {
        driveCoefficient = val;
    }

    public void resetter()
    {
    }
}