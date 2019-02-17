package frc.robot;

public class Constants 
{
	//Computer Ports
		public static final int LOGITECH_PORT = 0;
		public static final int XBOX_PORT = 1;
	//Logitech Ports
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
		//Sticks
			public static final int LEFT_X = 0;
			public static final int LEFT_Y = 1;
			public static final int LEFT_TRIGGER = 2;
			public static final int RIGHT_TRIGGER = 3;
			public static final int RIGHT_X = 4;
			public static final int RIGHT_Y = 5;
	//Robot Ports
		//CAN
			public static final int FRONT_LEFT_DRIVE_PORT = 0; //Drive
			public static final int FRONT_RIGHT_DRIVE_PORT = 1; //Drive
			public static final int BACK_LEFT_DRIVE_PORT = 2; //Drive
			public static final int BACK_RIGHT_DRIVE_PORT = 3; //Drive

		//PWM
			public static final int ARM_MOTOR_PORT = 1;
			public static final int LEFT_WRIST_MOTOR_PORT = 0;
			public static final int RIGHT_WRIST_MOTOR_PORT = 5;
			public static final int LEFT_INTAKE_ROTATION_MOTOR_PORT = 3;
			public static final int RIGHT_INTAKE_ROTATION_MOTOR_PORT = 4;
			public static final int INTAKE_MOTOR_PORT = 7;
			public static final int CAMERA_SERVO_PORT = 6;
			

		//DIO

		//Analog
			public static final int ARM_ENCODER_PORT_A = 1;
			//public static final int ARM_ENCODER_PORT_B = 0;
			public static final int LEFT_WRIST_ENCODER_PORT_A = 2;
			//public static final int LEFT_WRIST_ENCODER_PORT_B = 3;
			public static final int RIGHT_WRIST_ENCODER_PORT_A = 3;
			//public static final int RIGHT_WRIST_ENCODER_PORT_B = 5;
			public static final int LEFT_INTAKE_ACTUATION_ENCODER_PORT_A = 4;
			//public static final int LEFT_INTAKE_ACTUATION_ENCODER_PORT_B = 7;
			public static final int RIGHT_INTAKE_ACTUATION_ENCODER_PORT_A = 5;
			//public static final int RIGHT_INTAKE_ACTUATION_ENCODER_PORT_B = 9;
		//Pneumatics
			public static final int FRONT_LEFT_LIFT_PORT = 0;
			public static final int FRONT_RIGHT_LIFT_PORT = 1;
			public static final int BACK_LEFT_LIFT_PORT = 2;
			public static final int BACK_RIGHT_LIFT_PORT = 3;
			public static final int GRIPPER_PORT = 4;
			public static final int CARGO_KICKER_PORT = 5;
			public static final int HATCH_RELEASE_PORT = 6;
}
