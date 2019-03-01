/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  DriveSubsystem drive;
  ArmSubsystem arm;
  IntakeSubsystem intake;
  LiftSubsystem lift;
  Utilities283 utils;
  Joystick logitech, xbox;
  //Encoder elbowEnc;
  //DigitalInput elbowUpperLimitSwitch;
  //DigitalInput elbowLowerLimitSwitch;
  //Encoder rotateEncoder;
  //DigitalInput intakeUpperLimitSwitch;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    /*elbowEnc = new Encoder(Constants.ELBOW_ENCODER_PORT_A, Constants.ELBOW_ENCODER_PORT_B);
    elbowUpperLimitSwitch = new DigitalInput(Constants.ELBOW_UPPER_LIMIT_SWITCH);
    elbowLowerLimitSwitch = new DigitalInput(Constants.ELBOW_LOWER_LIMIT_SWITCH);
    */
    //rotateEncoder = new Encoder(Constants.INTAKE_ROTATION_ENCODER_PORT_A, Constants.INTAKE_ROTATION_ENCODER_PORT_B);
    //intakeUpperLimitSwitch = new DigitalInput(Constants.INTAKE_UPPER_LIMIT_SWITCH);
    drive = new DriveSubsystem();
    arm = new ArmSubsystem();
    intake = new IntakeSubsystem();
    //lift = new LiftSubsystem();
    logitech = new Joystick(Constants.LOGITECH_PORT);
    xbox = new Joystick(Constants.XBOX_PORT);
    CameraServer.getInstance().startAutomaticCapture();
  }

  /**   */
  @Override
  public void robotPeriodic() {
  }

  /**   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    /*
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
    */
    periodic();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() 
  {
    periodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    /*
    */ 
    //SmartDashboard.putNumber("Intake Rotation Encoder Val", rotateEncoder.get());
    //SmartDashboard.putBoolean("Intake Rotation Limit Switch", intakeUpperLimitSwitch.get());
  }
  public void periodic()
  {
    arm.periodic();
    drive.periodic();
    intake.periodic();
    //lift.periodic();

    drive.drive(logitech.getRawAxis(Constants.LEFT_Y),  
                logitech.getRawAxis(Constants.LEFT_X), 
                logitech.getRawAxis(Constants.RIGHT_X));
    
    arm.manualRotate(xbox.getRawAxis(Constants.LEFT_Y), xbox.getRawAxis(Constants.RIGHT_Y));
    
    arm.rotate(xbox.getRawButton(Constants.BACK),
                xbox.getRawButton(Constants.START), 
                xbox.getRawButton(Constants.B),
                xbox.getRawButton(Constants.A), 
                xbox.getRawButton(Constants.X));

    SmartDashboard.putNumber("Elbow Encoder Value (Test Periodic)", arm.elbowEnc.get());
    SmartDashboard.putBoolean("Elbow Upper Limit Switch", arm.elbowUpperLimitSwitch.get());
    SmartDashboard.putBoolean("Elbow Lower Limit Switch", arm.elbowLowerLimitSwitch.get()); 
             

    arm.actuateWrist(xbox.getRawButtonPressed(Constants.Y),
                     xbox.getRawButton(Constants.LEFT_BUMPER),
                     xbox.getRawButton(Constants.RIGHT_BUMPER)); 
    //lift.unlockLift(logitech.getRawButton(Constants.BACK));
    //lift.liftPistons(xbox.getRawButton(Constants.A), 
    //               xbox.getRawButton(Constants.X), 
    //               xbox.getRawButton(Constants.B));
    
    intake.intake(logitech.getRawButton(Constants.LEFT_BUMPER), logitech.getRawButton(Constants.RIGHT_BUMPER));
    intake.rotate((logitech.getRawAxis(Constants.LEFT_TRIGGER) >= 0.5), (logitech.getRawAxis(Constants.RIGHT_TRIGGER) >= 0.5),
    logitech.getRawButton(Constants.BACK));
  
  }
}
