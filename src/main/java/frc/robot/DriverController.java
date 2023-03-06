// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

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
        return getRawAxis(1);
    }

    public double getYDriveAxis() {
        return getRawAxis(0);
    }

    public double getZDriveAxis() {
        return getRawAxis(4);
    }
}
