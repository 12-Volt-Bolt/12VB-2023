package frc.robot.command;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.Grabber;

public class CloseGrabber extends CommandBase {

  private Grabber grabber;
  private long endTimeMillis;

  /** Creates a new OpenGrabber. */
  public CloseGrabber(Grabber grabber) {
    this.grabber = grabber;
    addRequirements(grabber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    grabber.close();
    endTimeMillis = System.currentTimeMillis() + 500;
  }

  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > endTimeMillis;
  }
}
