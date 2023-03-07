// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.Grabber;

public class OpenGrabber extends CommandBase {

  private Grabber grabber;
  private long endTimeMillis;

  /** Creates a new OpenGrabber. */
  public OpenGrabber(Grabber grabber) {
    this.grabber = grabber;
    addRequirements(grabber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    grabber.open();
    endTimeMillis = System.currentTimeMillis() + 500;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > endTimeMillis;
  }
}
