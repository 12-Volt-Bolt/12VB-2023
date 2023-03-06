// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Lifter extends SubsystemBase {
  private Solenoid lifter = new Solenoid(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.LIFTER_CHANNEL);
  /** Creates a new Lifter. */
  public Lifter() {}

  public void raise() {
    lifter.set(true);
  }
  public void lower() {
    lifter.set(false);
  }

}
