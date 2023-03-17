// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utility.BindableValue;
import frc.robot.utility.ReferenceBindableValue;

public abstract class Drivetrain extends SubsystemBase {
  private double yPower;
  private double xPower;
  private double yawPower;

  public BindableValue<Boolean> forceStop = new ReferenceBindableValue<Boolean>(false);

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

  protected abstract void calculatrMotorPowers(double yPower, double xPower, double yawPower);

  protected abstract void stopMotors();

  public abstract void setIdleMode(IdleMode mode);

  public abstract IdleMode getIdleMode();

  public abstract double leftRotationCount();

  public abstract double leftInchCount();

  @Override
  public void periodic() {
    if (forceStop.value()) {
      stopMotors();
      setIdleMode(IdleMode.kBrake);
    } else {
      calculatrMotorPowers(yPower, xPower, yawPower);
    }
  }
}
