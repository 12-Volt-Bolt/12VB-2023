// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/** Add your docs here. */
public class DriverController extends XboxController {

    private double deadzone(double value, double deadzone) {
        if (Math.abs(value) < Math.abs(deadzone)) {
            return 0;
        }
        return value;
    }

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
        return deadzone(getRawAxis(1), 0.2);
    }

    public double getYDriveAxis() {
        return deadzone(getRawAxis(0), 0.2);
    }

    public double getZDriveAxis() {
        return deadzone(getRawAxis(4), 0.2);
    }
}
