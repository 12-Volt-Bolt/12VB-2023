// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lifter extends SubsystemBase {

  public enum LifterPosition {
    RAISED,
    LOWERED
  }
 
  private Solenoid solenoid;

  /** Creates a new Lifter. */
  public Lifter(PneumaticsModuleType moduleType, int channel) {
    solenoid = new Solenoid(moduleType, channel);
  }

  public void raise() {
    solenoid.set(true);
  }

  public void lower() {
    solenoid.set(false);
  }

  public LifterPosition getPosition() {
    return solenoid.get() ? LifterPosition.RAISED : LifterPosition.LOWERED;
  }

  public void setPosition(LifterPosition position) {
    switch (position) {
      case LOWERED:
        lower();
        break;
      case RAISED:
        raise();
        break;
      default:
        throw new RuntimeException("Lifter 'setPosition' method missing switch case!");
    }
  }

  public boolean wallDetected() {
    return false;
  }
}
