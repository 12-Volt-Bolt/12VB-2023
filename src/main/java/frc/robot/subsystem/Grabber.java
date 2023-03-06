// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Grabber extends SubsystemBase {
 
  private Solenoid solenoid;

  /** Creates a new Lifter. */
  public Grabber(PneumaticsModuleType moduleType, int channel) {
    solenoid = new Solenoid(moduleType, channel);
  }

  public void close() {
    solenoid.set(true);
  }

  public void open() {
    solenoid.set(false);
  }
}
