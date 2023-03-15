// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import java.util.Optional;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.utility.RapidStopSlewRateLimiter;
import frc.robot.utility.SlewRateLimiter;

public class KopDrive extends Drivetrain {

  private CANSparkMax left1 = new CANSparkMax(RobotMap.MOTOR_DRIVERSIDE_1, MotorType.kBrushless);
  private CANSparkMax left2 = new CANSparkMax(RobotMap.MOTOR_DRIVERSIDE_2, MotorType.kBrushless);
  private CANSparkMax right1 = new CANSparkMax(RobotMap.MOTOR_PASSENGERSIDE_1, MotorType.kBrushless);
  private CANSparkMax right2 = new CANSparkMax(RobotMap.MOTOR_PASSENGERSIDE_2, MotorType.kBrushless);

  private SlewRateLimiter leftSRL = new RapidStopSlewRateLimiter(0.015);
  private SlewRateLimiter rightSRL = new RapidStopSlewRateLimiter(0.015);

  /** Creates a new KopDrive. */
  public KopDrive() {}

  public void setSlewRateLimiters(
      Optional<SlewRateLimiter> leftSRL, 
      Optional<SlewRateLimiter> rightSRL) {
    if (leftSRL.isPresent()) {
      this.leftSRL = leftSRL.get();
    }
    if (rightSRL.isPresent()) {
      this.rightSRL = rightSRL.get();
    }
  }

  @Override
  protected void calculatrMotorPowers(double yPower, double xPower, double yawPower) {
    yawPower = -yawPower;

    double newLeftPower = yPower;
    double newRightPower = yPower;

    yawPower *= 0.75;

    newLeftPower += yawPower * 0.66;
    newRightPower -= yawPower * 0.66;

    newLeftPower = leftSRL.calculate(newLeftPower);
    newRightPower = rightSRL.calculate(newRightPower);

    left1.set(-newLeftPower);
    left2.set(-newLeftPower);
    right1.set(newRightPower);
    right2.set(newRightPower);
  }

  @Override
  public void setIdleMode(IdleMode mode) {
    left1.setIdleMode(mode);
    left2.setIdleMode(mode);
    right1.setIdleMode(mode);
    right2.setIdleMode(mode);
  }

  @Override
  public IdleMode getIdleMode() {
    return left1.getIdleMode();
  }

  @Override
  protected void stopMotors() {
    left1.set(0);
    left2.set(0);
    right1.set(0);
    right2.set(0);
  }
}
