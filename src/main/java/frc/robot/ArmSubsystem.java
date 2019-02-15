package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class ArmSubsystem
{
    Victor armMotor, leftWristMotor, rightWristMotor;
    Encoder armEnc, leftWristEnc, rightWristEnc;
    Solenoid gripSol, cargoSol, hatchSol;
    ArmSubsystem()
    {
        armMotor = new Victor(Constants.ARM_MOTOR_PORT);
        leftWristMotor = new Victor(Constants.LEFT_WRIST_MOTOR_PORT);
        rightWristMotor = new Victor(Constants.RIGHT_WRIST_MOTOR_PORT);
        armEnc = new Encoder(Constants.ARM_ENCODER_PORT_A, Constants.ARM_ENCODER_PORT_B);
        leftWristEnc = new Encoder(Constants.LEFT_WRIST_ENCODER_PORT_A, Constants.LEFT_WRIST_ENCODER_PORT_B);
        rightWristEnc = new Encoder(Constants.RIGHT_WRIST_ENCODER_PORT_A, Constants.RIGHT_WRIST_ENCODER_PORT_B);
        gripSol = new Solenoid(Constants.GRIPPER_PORT);
        cargoSol = new Solenoid(Constants.CARGO_KICKER_PORT);
        hatchSol = new Solenoid(Constants.HATCH_RELEASE_PORT);
    }
    public void rotate(double armMag)
    {
        armMotor.set(armMag);
    }
}