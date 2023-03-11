// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.config.TurboModeBindableValue;
import frc.robot.utility.Remapper;
import frc.robot.utility.ThrottleCurve;

/** Add your docs here. */
public class DriverController extends XboxController {

    private Remapper drivestickRemapper = new Remapper(1.0, 0.2, 1.0);

    private Remapper yRemapper = new Remapper(1.0, 0.0, 1.0);
    private Remapper xRemapper = new Remapper(1.0, 0.0, 1.0);
    private Remapper yawRemapper = new Remapper(1.0, 0.0, 1.0);

    public DriverController(int port) {
        super(port);

        TurboModeBindableValue yBindValue = new TurboModeBindableValue(this, 0.5, 1.0);
        yRemapper.maxPositiveOutput = yBindValue.clone();
        yRemapper.maxNegativeOutput = yBindValue.clone().negate();
        
        TurboModeBindableValue xBindValue = new TurboModeBindableValue(this, 0.5, 1.0);
        xRemapper.maxPositiveOutput = xBindValue.clone();
        xRemapper.maxNegativeOutput = xBindValue.clone().negate();
        
        TurboModeBindableValue yawBindValue = new TurboModeBindableValue(this, 0.7, 1.0);
        yawRemapper.maxPositiveOutput = yawBindValue.clone();
        yawRemapper.maxNegativeOutput = yawBindValue.clone().negate();
    }
  
    public void setRemappers(
        Optional<Remapper> yRemapper,
        Optional<Remapper> xRemapper,
        Optional<Remapper> yawRemapper) {
      if (yRemapper.isPresent()) {
        this.yRemapper = yRemapper.get();
      }
      if (xRemapper.isPresent()) {
        this.xRemapper = xRemapper.get();
      }
      if (yawRemapper.isPresent()) {
        this.yawRemapper = yawRemapper.get();
      }
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
        double input = -getLeftY();
        input = drivestickRemapper.remap(input);
        input = ThrottleCurve.calculate(input, 1.5);
        input = yRemapper.remap(input);
        return input;
    }

    /**
     * @return Right/left strafe power (positive/negative).
     */
    public double xDriveAxis() {
        double input = getRightTriggerAxis() - getLeftTriggerAxis();
        input = drivestickRemapper.remap(input);
        input = ThrottleCurve.calculate(input, 1.5);
        input = xRemapper.remap(input);
        return input;
    }

    /**
     * @return Clockwise/counterclockwise rotation power (positive/negative).
     */
    public double yawDriveAxis() {
        double input = getRightX();
        input = drivestickRemapper.remap(input);
        input = ThrottleCurve.calculate(input, 1.5);
        input = yawRemapper.remap(input);
        return input;
    }

    public boolean turboMode() {
        return getLeftBumper() || getRightBumper();
    }
}
