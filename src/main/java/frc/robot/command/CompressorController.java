// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CompressorController extends CommandBase {
  private Compressor compressor;
  private boolean enabled;
  private boolean force;

  /** Creates a new Compressor. */
  public CompressorController(Compressor compressor, boolean enabled, boolean force) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.compressor = compressor;
    this.enabled = enabled;
    this.force = force;

    this.ignoringDisable(true);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (enabled) {
      compressor.enableDigital();
    } else {
      compressor.disable();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (force) {
      if (enabled) {
        compressor.enableDigital();
      } else {
        compressor.disable();
      }
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
