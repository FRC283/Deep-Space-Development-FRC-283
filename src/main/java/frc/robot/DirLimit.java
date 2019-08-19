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
     * @param switchPort - Limit switch port
     * @param normalClose - State when limit is not hit
     */
    DirLimit(int dir, int switchPort, boolean normalClose)
    {
        direction = dir;
        limitSwitch = new DigitalInput(switchPort);
        this.normalClose = normalClose;
    }

    /**
     * 
     * @param axisMag - direction that is going
     * @return - go (true) stop (false)
     */
    public boolean check(double axisMag)
    {
        if((axisMag * direction > 0 ) && (isPressed()))
        {
            //if the directions match and the switch is tripped, do not allow movement
            return false;
        }
        else
        {
            return true;
        }
    }

    public double filter(double axisMag)
    {
        return axisMag * (check(axisMag) ? 1 : 0);
    }

    /**
     * @return - Returns true if the inner switch
     */
    public boolean isPressed()
    {
        return normalClose != limitSwitch.get();
    }
    public boolean get()
    {
        return limitSwitch.get();
    }
}