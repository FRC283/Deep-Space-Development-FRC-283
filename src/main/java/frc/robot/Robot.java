/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  private DriveSubsystem drive;
  private ArmSubsystem arm;
  private IntakeSubsystem intake;
  private LiftSubsystem lift;
  private Joystick logitech, xbox;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    drive = new DriveSubsystem();
    arm = new ArmSubsystem();
    intake = new IntakeSubsystem();
    lift = new LiftSubsystem();
    logitech = new Joystick(Constants.LOGITECH_PORT);
    xbox = new Joystick(Constants.XBOX_PORT);
    CameraServer.getInstance().startAutomaticCapture();

  }

  /**
   *  autonomousInit() is called once during the start of the Autonomous period of play
   */
  @Override
  public void autonomousInit()
  {

  }

  /**
   * autonomousPeriodic() is called every timed period during Autonomous. By default, this is 20 milliseconds
   * @see <a href="https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599697-choosing-a-base-class">WPILIB Screen Steps Live</a>
   * under the TimedRobot section for more information about the time interval.
   */
  @Override
  public void autonomousPeriodic()
  {
    periodic();
  }

  /**
   * teleopPeriodic() is called every timed period during Autonomous. By default, this is 20 milliseconds
   * @see <a href="https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599697-choosing-a-base-class">WPILIB Screen Steps Live</a>
   * under the TimedRobot section for more information about the time interval.
   */
  @Override
  public void teleopPeriodic() 
  {
    periodic();
  }

  /**
   * periodic() is called in both the teleopPeriodic() and autonomousPeriodic() because of the unique game setup this
   * year where operators were allowed to control the robot during the autonomous (sandstorm) period of the game
   */
  private void periodic()
  {
    arm.periodic();
    drive.periodic();
    intake.periodic();
    lift.periodic();

    drive.drive(logitech.getRawAxis(Constants.LEFT_Y),
                logitech.getRawAxis(Constants.LEFT_X),
                logitech.getRawAxis(Constants.RIGHT_X));

    arm.manualRotate(xbox.getRawAxis(Constants.LEFT_Y), xbox.getRawAxis(Constants.RIGHT_Y));

    /*
     arm.rotate(xbox.getRawButton(Constants.BACK),
               xbox.getRawButton(Constants.START),
               xbox.getRawButton(Constants.B),
               xbox.getRawButton(Constants.A),
              xbox.getRawButton(Constants.X));
              */

    SmartDashboard.putNumber("Elbow Encoder Value (Test Periodic)", arm.elbowEncoder.get());
    SmartDashboard.putBoolean("Elbow Upper Limit Switch", arm.elbowUpperLimitSwitch.get());
    SmartDashboard.putBoolean("Elbow Lower Limit Switch", arm.elbowLowerLimitSwitch.get());

    arm.actuateWrist(xbox.getRawButtonPressed(Constants.Y),
                     xbox.getRawButton(Constants.LEFT_BUMPER),
                     xbox.getRawButton(Constants.RIGHT_BUMPER));

    intake.intake(logitech.getRawButton(Constants.LEFT_BUMPER), logitech.getRawButton(Constants.RIGHT_BUMPER));
    intake.rotate((logitech.getRawAxis(Constants.LEFT_TRIGGER) >= 0.5),
                  (logitech.getRawAxis(Constants.RIGHT_TRIGGER) >= 0.5),
                   logitech.getRawButton(Constants.BACK));

    lift.unlockLift(xbox.getRawButton(Constants.BACK), drive);
    lift.actuateLift(logitech.getRawButton(Constants.A),
                     logitech.getRawButton(Constants.Y),
                     logitech.getRawButton(Constants.B));
  
  }
}
