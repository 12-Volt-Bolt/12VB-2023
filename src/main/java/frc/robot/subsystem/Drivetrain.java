// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Drivetrain extends SubsystemBase {
  private double yPower;
  private double xPower;
  private double yawPower;

  /** Creates a new Drivetrain. */
  public Drivetrain() {}
  
  /**
   * Parameter definitions use the pattern positive/negative.
   * 
   * @param yPower Drive forward/backward.
   * @param xPower Strafe right/left.
   * @param yawPower Rotate clockwise/counterclockwise.
   */
  public void drive(double yPower, double xPower, double yawPower) {
    this.yPower = yPower;
    this.xPower = xPower;
    this.yawPower = yawPower;
  }

  protected abstract void setMotors(double yPower, double xPower, double yawPower);

  public abstract void setIdleMode(IdleMode mode);

  public abstract IdleMode getIdleMode();

  @Override
  public void periodic() {
    setMotors(yPower, xPower, yawPower);
  }
}
