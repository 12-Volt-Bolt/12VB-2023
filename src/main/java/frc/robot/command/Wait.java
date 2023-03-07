// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Wait extends CommandBase {

  private long endTimeMillis;
  private long waitTimeMillis;

  /** Creates a new Wait. */
  public Wait(long waitTimeMillis) {
    this.waitTimeMillis = waitTimeMillis;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    endTimeMillis = waitTimeMillis + System.currentTimeMillis();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > endTimeMillis;
  }
}
