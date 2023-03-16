// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utility.BindableValue;

public class PneumaticBrake extends SubsystemBase implements BindableValue<Boolean> {

  private Solenoid upBrake;
  private Solenoid downBrake;

  /** Creates a new PneumaticBrake. */
  public PneumaticBrake(PneumaticsModuleType moduleType, int upChannel, int downChannel) {
    upBrake = new Solenoid(moduleType, upChannel);
    downBrake = new Solenoid(moduleType, downChannel);
  }

  public void setBrake(boolean state) {
    upBrake.set(state);
    downBrake.set(state);
  }

  @Override
  public Boolean value() {
    return upBrake.get();
  }

  public void toggle() {
    setBrake(!value());
  }

  @Override
  public PneumaticBrake cloneValue() {
    return this;
  }
}
