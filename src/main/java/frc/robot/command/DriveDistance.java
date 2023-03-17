// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.BoxDrive;
import frc.robot.subsystem.Drivetrain;
import frc.robot.utility.MyMath;

public class DriveDistance extends CommandBase {
  public static enum Direction {
    FORWARD,
    BACKWARD
  }

  private Drivetrain drivetrain;
  private double distanceInches;
  private double startDistance;
  private double yPower;
  private Direction direction;

  /** Creates a new DriveDistance. */
  public DriveDistance(Drivetrain drivetrain, double distanceInches, double yPower, Direction direction) {
    this.drivetrain = drivetrain;
    this.distanceInches = Math.abs(distanceInches);
    this.yPower = Math.abs(yPower);
    this.direction = direction;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startDistance = drivetrain.leftInchCount();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (direction == Direction.FORWARD) {
      drivetrain.drive(yPower, 0, 0);
    } else {
      drivetrain.drive(-yPower, 0, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return distanceInches < MyMath.difference(startDistance, drivetrain.leftInchCount());
  }
}
