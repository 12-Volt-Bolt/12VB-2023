// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.Drivetrain;

public class DrivetrainIdleMode extends CommandBase {
  private Drivetrain drivetrain;
  private IdleMode mode;
  private boolean force;
 
  /** Creates a new Break. */
  public DrivetrainIdleMode(Drivetrain drivetrain, IdleMode mode, boolean force) {
    this.drivetrain = drivetrain;
    this.mode = mode;
    this.force = force;
    this.ignoringDisable(true);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setIdleMode(mode);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (force && drivetrain.getIdleMode() != mode) {
      drivetrain.setIdleMode(mode);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !force;
  }
}
