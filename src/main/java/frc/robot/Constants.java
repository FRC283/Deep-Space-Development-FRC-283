package frc.robot;

public class Constants 
{
	/************************************************************************
	 *
	 *  Computer Controller Port Constants
	 *
	 ************************************************************************/

	public static final int LOGITECH_PORT = 0;
	public static final int XBOX_PORT = 1;


	/************************************************************************
	 *
	 *  Joystick Button/Axis Constants
	 *
	 ************************************************************************/

	//Buttons
	public static final int A = 1;
	public static final int B = 2;
	public static final int X = 3;
	public static final int Y = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int BACK = 7;
	public static final int START = 8;
	public static final int LEFT_STICK_BUTTON = 9;
	public static final int RIGHT_STICK_BUTTON = 10;

	//Stick Axis
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
	public static final int RIGHT_X = 4;
	public static final int RIGHT_Y = 5;

	
	/************************************************************************
	 *
	 *  Robot Port Constants
	 *
	 ************************************************************************/

	//CAN Bus
	public static final int FRONT_LEFT_DRIVE_PORT = 0; 	//Drive
	public static final int FRONT_RIGHT_DRIVE_PORT = 1; //Drive
	public static final int BACK_LEFT_DRIVE_PORT = 2; 	//Drive
	public static final int BACK_RIGHT_DRIVE_PORT = 3; 	//Drive


	//VictorSPX
	public static final int FRONT_LEFT_LIFT_PORT = 4;
	public static final int FRONT_RIGHT_LIFT_PORT = 5;
	public static final int BACK_LEFT_LIFT_PORT = 7;
	public static final int BACK_RIGHT_LIFT_PORT = 6;

	//PWM
	public static final int ELBOW_MOTOR_PORT = 1; 					//SOLID BLUE
	public static final int LEFT_WRIST_MOTOR_PORT = 0;				//SOLID YELLOW
	public static final int RIGHT_WRIST_MOTOR_PORT = 5;				//SOLID GREEN
	public static final int LEFT_INTAKE_ACTUATION_MOTOR_PORT = 2;	//SOLID ORANGE
	public static final int RIGHT_INTAKE_ACTUATION_MOTOR_PORT = 4;	//SOLID RED
	public static final int INTAKE_ROLLER_MOTOR_PORT = 3;			//SOLID BLACK
	public static final int CAMERA_SERVO_PORT = 8;


	//DIO
	public static final int ELBOW_ENCODER_PORT_A = 0;
	public static final int ELBOW_ENCODER_PORT_B = 1;
	public static final int INTAKE_ROTATION_ENCODER_PORT_A = 2;
	public static final int INTAKE_ROTATION_ENCODER_PORT_B = 3;
	public static final int INTAKE_UPPER_LIMIT_SWITCH = 4;
	public static final int ELBOW_UPPER_LIMIT_SWITCH = 8;
	public static final int ELBOW_LOWER_LIMIT_SWITCH = 9;

	//Limit switches
	public static final int LIFT_TOP_FRONT_LEFT_LIMIT_SWITCH = 10;
	public static final int LIFT_TOP_FRONT_RIGHT_LIMIT_SWITCH = 11;
	public static final int LIFT_TOP_BACK_RIGHT_LIMIT_SWITCH = 12;
	public static final int LIFT_TOP_BACK_LEFT_LIMIT_SWITCH = 13;

	public static final int LIFT_BOTTOM_FRONT_LEFT_LIMIT_SWITCH = 14;
	public static final int LIFT_BOTTOM_FRONT_RIGHT_LIMIT_SWITCH = 15;
	public static final int LIFT_BOTTOM_BACK_LEFT_LIMIT_SWITCH = 16;
	public static final int LIFT_BOTTOM_BACK_RIGHT_LIMIT_SWITCH = 17;

	//Analog
	public static final int LEFT_WRIST_ENCODER_PORT_A = 0;
	public static final int RIGHT_WRIST_ENCODER_PORT_A = 2;

	//Pneumatics
	public static final int GRIPPER_PORT = 2;
	public static final int CARGO_KICKER_PORT = 1;
	public static final int HATCH_RELEASE_PORT = 0;

	//Deadzone
	public static final double DEAD_ZONE = 0.05;
}
