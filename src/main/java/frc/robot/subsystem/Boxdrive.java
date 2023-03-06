// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Boxdrive extends SubsystemBase {
  /** Creates a new Boxdrive. */
  public Boxdrive() {}

  private CANSparkMax left1 = new CANSparkMax(RobotMap.MOTOR_LEFT_1, MotorType.kBrushless);
  private CANSparkMax left2 = new CANSparkMax(RobotMap.MOTOR_LEFT_2, MotorType.kBrushless);
  private CANSparkMax right1 = new CANSparkMax(RobotMap.MOTOR_RIGHT_1, MotorType.kBrushless);
  private CANSparkMax right2 = new CANSparkMax(RobotMap.MOTOR_RIGHT_2, MotorType.kBrushless);
  private CANSparkMax front = new CANSparkMax(RobotMap.MOTOR_FRONT, MotorType.kBrushless);
  private CANSparkMax back = new CANSparkMax(RobotMap.MOTOR_REAR, MotorType.kBrushless);

  public void drive(double xpower, double ypower, double zpower) {
    double leftPower = xpower;
    double rightPower = xpower;
    double frontPower = ypower;
    double backPower = ypower;

    zpower *= 0.75;

    leftPower += zpower * 0.66;
    rightPower -= zpower * 0.66;
    frontPower -= zpower;
    backPower += zpower;

    left1.set(-leftPower);
    left2.set(-leftPower);
    right1.set(rightPower);
    right2.set(rightPower);
    front.set(frontPower);
    back.set(-backPower);
  }
}
