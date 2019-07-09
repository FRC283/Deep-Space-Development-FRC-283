package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class DirLimit
{
    /**Direction of non movement */
    private int direction;
    /**Limit switch port */
    private DigitalInput limitSwitch;
    /**State when limit is not hit */
    private boolean normalClose;
    /**
     * Directional Limit Switch
     * @param dir - Direction of non movement
     * @param swit - Limit switch port
     * @param normal - State when limit is not hit
     */
    DirLimit(int dir, int swit, boolean normal)
    {
        direction = dir;
        limitSwitch = new DigitalInput(swit);
        normalClose = normal;
    }
    /**
     * 
     * @param mag - direction that is going
     * @return - go (1) or no go (0)
     */
    public int check(double mag)
    {
        if((mag/direction > 0 ) && (normalClose != limitSwitch.get() ) )
        //if the directions match and the switch is tripped, do not allow movement
            return 0;
        return 1;
    }
}