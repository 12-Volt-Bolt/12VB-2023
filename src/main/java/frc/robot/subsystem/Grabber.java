// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Grabber extends SubsystemBase {
  private Solenoid grabber = new Solenoid(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.GRABBER_CHANNEL);
  /** Creates a new grabber. */
  public Grabber() {}

  public void open() {
    grabber.set(true);
  }
  public void close() {
    grabber.set(false);
  }

  
}
