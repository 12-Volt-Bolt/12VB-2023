// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.Drivetrain;

public class SetIdleMode extends CommandBase {
  private Drivetrain drivetrain;
  private IdleMode mode;

  /** Creates a new SetBrakeMode. */
  public SetIdleMode(Drivetrain drivetrain, IdleMode mode) {
    this.drivetrain = drivetrain;
    this.mode = mode;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setIdleMode(mode);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
