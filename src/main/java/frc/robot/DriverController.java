// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.utility.Remapper;
import frc.robot.utility.ThrottleCurve;

/** Add your docs here. */
public class DriverController extends XboxController {

    private Remapper drivestickRemapper = new Remapper(1.0, 0.2, 1.0);

    public DriverController(int port) {
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

    /**
     * @return Whether the button assigned to open the grabber is pressed.
     */
    public boolean openGrabber() {
        return getRightBumper();
    }

    /**
     * @return Whether the button assigned to close the grabber is pressed.
     */
    public boolean closeGrabber() {
        return getLeftBumper();
    }

    /**
     * @return Forward/backward drive power.
     */
    public double yDriveAxis() {
        double input = -getRawAxis(1);
        input = drivestickRemapper.remap(input);
        input = ThrottleCurve.calculate(input, 1.5);
        return input;
    }

    /**
     * @return Right/left strafe power.
     */
    public double xDriveAxis() {
        double input = getRawAxis(0);
        input = drivestickRemapper.remap(input);
        input = ThrottleCurve.calculate(input, 1.5);
        return input;
    }

    /**
     * @return Clockwise/counterclockwise rotation power.
     */
    public double yawDriveAxis() {
        double input = getRawAxis(4);
        input = drivestickRemapper.remap(input);
        input = ThrottleCurve.calculate(input, 1.5);
        return input;
    }
}
