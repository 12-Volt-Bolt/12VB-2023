// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import java.util.Optional;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.utility.RapidStopSlewRateLimiter;
import frc.robot.utility.SlewRateLimiter;

public class BoxDrive extends SubsystemBase {

  private CANSparkMax left1 = new CANSparkMax(RobotMap.MOTOR_DRIVERSIDE_1, MotorType.kBrushless);
  private CANSparkMax left2 = new CANSparkMax(RobotMap.MOTOR_DRIVERSIDE_2, MotorType.kBrushless);
  private CANSparkMax right1 = new CANSparkMax(RobotMap.MOTOR_PASSENGERSIDE_1, MotorType.kBrushless);
  private CANSparkMax right2 = new CANSparkMax(RobotMap.MOTOR_PASSENGERSIDE_2, MotorType.kBrushless);
  private CANSparkMax front = new CANSparkMax(RobotMap.MOTOR_FRONT, MotorType.kBrushless);
  private CANSparkMax back = new CANSparkMax(RobotMap.MOTOR_REAR, MotorType.kBrushless);

  private double leftPower = 0;
  private double rightPower = 0;
  private double frontPower = 0;
  private double backPower = 0;

  private SlewRateLimiter leftSRL = new RapidStopSlewRateLimiter(0.015);
  private SlewRateLimiter rightSRL = new RapidStopSlewRateLimiter(0.015);
  private SlewRateLimiter frontSRL = new RapidStopSlewRateLimiter(0.015);
  private SlewRateLimiter backSRL = new RapidStopSlewRateLimiter(0.015);

  public void setIdleMode(IdleMode mode) {
    left1.setIdleMode(mode);
    left2.setIdleMode(mode);
    right1.setIdleMode(mode);
    right2.setIdleMode(mode);
    front.setIdleMode(mode);
    back.setIdleMode(mode);
  }

  public void setSlewRateLimiters(
      Optional<SlewRateLimiter> leftSRL, 
      Optional<SlewRateLimiter> rightSRL,
      Optional<SlewRateLimiter> frontSRL,
      Optional<SlewRateLimiter> backSRL) {
    if (leftSRL.isPresent()) {
      this.leftSRL = leftSRL.get();
    }
    if (rightSRL.isPresent()) {
      this.rightSRL = rightSRL.get();
    }
    if (frontSRL.isPresent()) {
      this.frontSRL = frontSRL.get();
    }
    if (backSRL.isPresent()) {
      this.backSRL = backSRL.get();
    }
  }

  /**
   * Parameter definitions use the pattern positive/negative.
   * 
   * @param yPower Drive forward/backward.
   * @param xPower Strafe right/left.
   * @param yawPower Rotate clockwise/counterclockwise.
   */
  public void drive(double yPower, double xPower, double yawPower) {
    yawPower = -yawPower;

    double newLeftPower = yPower;
    double newRightPower = yPower;
    double newFrontPower = xPower;
    double newBackPower = xPower;

    yawPower *= 0.75;

    newLeftPower += yawPower * 0.66;
    newRightPower -= yawPower * 0.66;
    newFrontPower -= yawPower;
    newBackPower += yawPower;

    leftPower = newLeftPower;
    rightPower = newRightPower;
    frontPower = newFrontPower;
    backPower = newBackPower;
  }

  @Override
  public void periodic() {
    double leftSetPower = leftSRL.calculate(leftPower);
    double rightSetPower = rightSRL.calculate(rightPower);
    double frontSetPower = frontSRL.calculate(frontPower);
    double backSetPower = backSRL.calculate(backPower);

    left1.set(-leftSetPower);
    left2.set(-leftSetPower);
    right1.set(rightSetPower);
    right2.set(rightSetPower);
    front.set(frontSetPower);
    back.set(-backSetPower);
  }
}
