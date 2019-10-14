package frc.robot;

public abstract class Utilities283
{	
	public static void main(String[] args)
	{
	}
	//Logitech Ports (Default)
		//Digital
		public static final int LOGITECH_A = 0;
		public static final int LOGITECH_B = 1;
		public static final int LOGITECH_X = 2;
		public static final int LOGITECH_Y = 3;
		public static final int LOGITECH_LEFT_BUMPER = 4;
		public static final int LOGITECH_RIGHT_BUMPER = 5;
		public static final int LOGITECH_BACK = 6;
		public static final int LOGITECH_START = 7;
		public static final int LOGITECH_LEFT_STICK_BUTTON = 8;
		public static final int LOGITECH_RIGHT_STICK_BUTTON = 9;
	//Analog
		public static final int LOGITECH_LEFT_X = 10;
		public static final int LOGITECH_LEFT_Y = 11;
		public static final int LOGITECH_LEFT_TRIGGER = 12;
		public static final int LOGITECH_RIGHT_TRIGGER = 13;
		public static final int LOGITECH_RIGHT_X = 14;
		public static final int LOGITECH_RIGHT_Y = 15;
//Xbox Ports
	//Digital
		public static final int XBOX_A = 16;
		public static final int XBOX_B = 17;
		public static final int XBOX_X = 18;
		public static final int XBOX_Y = 19;
		public static final int XBOX_LEFT_BUMPER = 20;
		public static final int XBOX_RIGHT_BUMPER = 21;
		public static final int XBOX_BACK = 22;
		public static final int XBOX_START = 23;
		public static final int XBOX_LEFT_STICK_BUTTON = 24;
		public static final int XBOX_RIGHT_STICK_BUTTON = 25;
	//Analog
		public static final int XBOX_LEFT_X = 26;
		public static final int XBOX_LEFT_Y = 27;
		public static final int XBOX_LEFT_TRIGGER = 28;
		public static final int XBOX_RIGHT_TRIGGER = 29;
		public static final int XBOX_RIGHT_X = 30;
		public static final int XBOX_RIGHT_Y = 31; 
	/**
	 * Shortcut for using the rescaler with a deadzone
	 * @param value - value to be rescaled
	 * @param deadzone - abs of deadzone e.g. 0.1
	 * @return - new value
	 */
	public static double deadzone(double value, double deadzone)
	{
		return rescale(deadzone, 1, 0, 1, value);
	}
	
	/**
	 * A function that changes scales, cutting out outlying values and allowing negatives
	 * @param lowero - The lower end of the old scale
	 * @param uppero - The upper end of the old scale
	 * @param lowern - The lower end of the new scale
	 * @param uppern - The upper end of the new scale
	 * @param value - The value, on the old scale, to be returned as its equivalent on the new scale
	 * @return
	 */
	public static double rescale(double lowero, double uppero, double lowern, double uppern, double value)
	{
		boolean neg = false;
		double rescaledValue = 0;	//Rescaled Value = number to be returned
		if (value < 0)
		{
				neg = true;
				value *= -1;
		}
		double oldscale = uppero - lowero;
		double newscale = uppern - lowern;

		rescaledValue = value - lowero;
		rescaledValue /= oldscale;
		rescaledValue *= newscale;
		rescaledValue += lowern;

		if (rescaledValue < 0)
		{
			rescaledValue = 0;
		}
		if (neg == true)
		{
			rescaledValue *= -1;
		}
		return rescaledValue;
	}
}
