// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RunCommandInAuto extends CommandBase {
  private Robot robot; 
  private Command commandToRun;
  private boolean endCommand = false;

  /** Creates a new AutoSignal. */
  public RunCommandInAuto(Robot robot, Command commandToRun) {
    this.robot = robot;
    this.commandToRun = commandToRun;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    endCommand = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (robot.isAutonomous() && !robot.isDisabled()) {
      commandToRun.schedule();
      endCommand = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
