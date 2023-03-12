// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.config.DriverControllerConfig;
import frc.robot.subsystem.Lifter;
import frc.robot.utility.RemapperChain;

/** Add your docs here. */
public class DriverController extends XboxController {
    private RemapperChain<Double> yRemappers;
    private RemapperChain<Double> xRemappers;
    private RemapperChain<Double> yawRemappers;

    public DriverController(int port, Lifter lifter) {
        super(port);  
        yRemappers = DriverControllerConfig.yInputRemappers(this, lifter);
        xRemappers = DriverControllerConfig.xInputRemappers(this);
        yawRemappers = DriverControllerConfig.yawInputRemappers(this);
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
        return getBButton();
    }

    /**
     * @return Whether the button assigned to close the grabber is pressed.
     */
    public boolean closeGrabber() {
        return getAButton();
    }

    /**
     * @return Forward/backward drive power (positive/negative).
     */
    public double yDriveAxis() {
        return yRemappers.calculate(-getLeftY());
    }

    /**
     * @return Right/left strafe power (positive/negative).
     */
    public double xDriveAxis() {
        return xRemappers.calculate(getRightTriggerAxis() - getLeftTriggerAxis());
    }

    /**
     * @return Clockwise/counterclockwise rotation power (positive/negative).
     */
    public double yawDriveAxis() {
        return yawRemappers.calculate(getRightX());
    }

    public boolean turboMode() {
        return getLeftBumper() || getRightBumper();
    }
}
