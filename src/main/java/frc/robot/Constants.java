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
			public static final int FRONT_LEFT_DRIVE_PORT = 0; 	//Drive
			public static final int FRONT_RIGHT_DRIVE_PORT = 1; //Drive
			public static final int BACK_LEFT_DRIVE_PORT = 2; 	//Drive
			public static final int BACK_RIGHT_DRIVE_PORT = 3; 	//Drive


			//VictorSPX
			public static final int FRONT_LEFT_LIFT_PORT = 0;
			public static final int FRONT_RIGHT_LIFT_PORT = 1;
			//TalonSRX
			public static final int BACK_LEFT_LIFT_PORT = 4;
			public static final int BACK_RIGHT_LIFT_PORT = 5;

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
			public static final int LIFT_FRONT_LEFT_LIMIT_SWITCH = 6;
			public static final int LIFT_FRONT_RIGHT_LIMIT_SWITCH = 7;
			public static final int LIFT_BACK_RIGHT_LIMIT_SWITCH = 5;
		//Analog
			public static final int LEFT_WRIST_ENCODER_PORT_A = 1;
			//public static final int LEFT_WRIST_ENCODER_PORT_B = 3;
			public static final int RIGHT_WRIST_ENCODER_PORT_A = 2;
			//public static final int RIGHT_WRIST_ENCODER_PORT_B = 5;
			//public static final int LEFT_INTAKE_ACTUATION_ENCODER_PORT_A = 3;
			//public static final int LEFT_INTAKE_ACTUATION_ENCODER_PORT_B = 7;
			//public static final int RIGHT_INTAKE_ACTUATION_ENCODER_PORT_A = 4;
			//public static final int RIGHT_INTAKE_ACTUATION_ENCODER_PORT_B = 9;
			public static final int LIFT_BACK_LEFT_LIMIT_SWITCH = 1;
		//Pneumatics
			public static final int GRIPPER_PORT = 2;
			public static final int CARGO_KICKER_PORT = 1;
			public static final int HATCH_RELEASE_PORT = 0;

	//Deadzone
			public static final double DEAD_ZONE = 0.1;
}
