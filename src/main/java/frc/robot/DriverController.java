// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.utility.Deadzone;
import frc.robot.utility.ThrottleCurve;

/** Add your docs here. */
public class DriverController extends XboxController {

    public DriverController(int port) {
        super(port);
    }

    public boolean isDpadUp() {
        return getPOV() == 0;
    }

    public boolean isDpadDown() {
        return getPOV() == 180;
    }

    public double getXDriveAxis() {
        double input = -getRawAxis(1);
        input = Deadzone.inputRemap(input, 1.0, 0.2);
        input = ThrottleCurve.calculate(input, 1.5);
        return input;
    }

    public double getYDriveAxis() {
        double input = getRawAxis(0);
        input = Deadzone.inputRemap(input, 1.0, 0.2);
        input = ThrottleCurve.calculate(input, 1.5);
        return input;
    }

    public double getZDriveAxis() {
        double input = getRawAxis(4);
        input = Deadzone.inputRemap(input, 1.0, 0.2);
        input = ThrottleCurve.calculate(input, 1.5);
        return input;
    }
}
