// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.Drivetrain;

public class Drive extends CommandBase {
  
  private Drivetrain drivetrain;
  private long runTimeMillis; 
  private double xPower;
  private double yPower; 
  private double zPower;

  private long endTimeMillis;

  public Drive(Drivetrain drivetrain, long runTimeMillis, double xPower, double yPower, double zPower) {
    this.drivetrain = drivetrain;
    this.runTimeMillis = runTimeMillis;
    this.xPower = xPower;
    this.yPower = yPower;
    this.zPower = zPower;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    endTimeMillis = System.currentTimeMillis() + runTimeMillis;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drive(xPower, yPower, zPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > endTimeMillis;
  }
}
