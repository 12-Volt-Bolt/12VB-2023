package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class CodriverController extends XboxController {
    public CodriverController(int port) {
        super(port);
    }

    /**
     * @return Whether the up direction on the dpad is pressed.
     */
    public boolean isDpadUp() {
        return getPOV() == 0;
    }

    /**
     * @return Whether the down direction on the dpad is pressed.
     */
    public boolean isDpadDown() {
        return getPOV() == 180;
    }

    /**
     * @return Whether the button assigned to raise the lifter is pressed.
     */
    public boolean raiseLifter() {
        return isDpadUp();
    }

    /**
     * @return Whether the button assigned to lower the lifter is pressed.
     */
    public boolean lowerLifter() {
        return isDpadDown();
    }
}
