// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.config.TurboModeBindableValue;
import frc.robot.utility.DeadzoneWithLinearRemap;
import frc.robot.utility.ExponentialRemap;

/** Add your docs here. */
public class DriverController extends XboxController {

    private DeadzoneWithLinearRemap drivestickRemapper = new DeadzoneWithLinearRemap(1.0, 0.2, 1.0);

    private DeadzoneWithLinearRemap yDeadzone = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
    private DeadzoneWithLinearRemap xDeadzone = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
    private DeadzoneWithLinearRemap yawDeadzone = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);

    public DriverController(int port) {
        super(port);

        TurboModeBindableValue yBindValue = new TurboModeBindableValue(this, 0.5, 1.0);
        yDeadzone.maxPositiveOutput = yBindValue.cloneValue();
        yDeadzone.maxNegativeOutput = yBindValue.cloneValue().negate();
        
        TurboModeBindableValue xBindValue = new TurboModeBindableValue(this, 0.5, 1.0);
        xDeadzone.maxPositiveOutput = xBindValue.cloneValue();
        xDeadzone.maxNegativeOutput = xBindValue.cloneValue().negate();
        
        TurboModeBindableValue yawBindValue = new TurboModeBindableValue(this, 0.7, 1.0);
        yawDeadzone.maxPositiveOutput = yawBindValue.cloneValue();
        yawDeadzone.maxNegativeOutput = yawBindValue.cloneValue().negate();
    }
  
    public void setRemappers(
        Optional<DeadzoneWithLinearRemap> yRemapper,
        Optional<DeadzoneWithLinearRemap> xRemapper,
        Optional<DeadzoneWithLinearRemap> yawRemapper) {
      if (yRemapper.isPresent()) {
        this.yDeadzone = yRemapper.get();
      }
      if (xRemapper.isPresent()) {
        this.xDeadzone = xRemapper.get();
      }
      if (yawRemapper.isPresent()) {
        this.yawDeadzone = yawRemapper.get();
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
        input = ExponentialRemap.calculate(input, 1.5);
        input = yDeadzone.remap(input);
        return input;
    }

    /**
     * @return Right/left strafe power (positive/negative).
     */
    public double xDriveAxis() {
        double input = getRightTriggerAxis() - getLeftTriggerAxis();
        input = drivestickRemapper.remap(input);
        input = ExponentialRemap.calculate(input, 1.5);
        input = xDeadzone.remap(input);
        return input;
    }

    /**
     * @return Clockwise/counterclockwise rotation power (positive/negative).
     */
    public double yawDriveAxis() {
        double input = getRightX();
        input = drivestickRemapper.remap(input);
        input = ExponentialRemap.calculate(input, 1.5);
        input = yawDeadzone.remap(input);
        return input;
    }

    public boolean turboMode() {
        return getLeftBumper() || getRightBumper();
    }
}
