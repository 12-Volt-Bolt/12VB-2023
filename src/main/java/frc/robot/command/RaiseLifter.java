// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.Lifter;

public class RaiseLifter extends CommandBase {

  private Lifter lifter; 
  private long endTimeMillis;

  /** Creates a new RaiseLifter. */
  public RaiseLifter(Lifter lifter) {
    this.lifter = lifter;
    addRequirements(lifter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lifter.raise();
    endTimeMillis = System.currentTimeMillis() + 1000;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > endTimeMillis;
  }
}
