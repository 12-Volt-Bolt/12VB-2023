// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utility.BindableValue;

public class PneumaticBrake extends SubsystemBase implements BindableValue<Boolean> {

  private Solenoid brake;

  /** Creates a new PneumaticBrake. */
  public PneumaticBrake(PneumaticsModuleType moduleType, int channel) {
    brake = new Solenoid(moduleType, channel);
  }

  public void setBrake(boolean state) {
    brake.set(state);
  }

  @Override
  public Boolean value() {
    return brake.get();
  }

  public void toggle() {
    brake.set(!brake.get());
  }

  @Override
  public PneumaticBrake cloneValue() {
    return this;
  }
}
