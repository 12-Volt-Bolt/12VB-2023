// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class BoxDrive extends SubsystemBase {

  private CANSparkMax left1 = new CANSparkMax(RobotMap.MOTOR_DRIVERSIDE_1, MotorType.kBrushless);
  private CANSparkMax left2 = new CANSparkMax(RobotMap.MOTOR_DRIVERSIDE_2, MotorType.kBrushless);
  private CANSparkMax right1 = new CANSparkMax(RobotMap.MOTOR_PASSENGERSIDE_1, MotorType.kBrushless);
  private CANSparkMax right2 = new CANSparkMax(RobotMap.MOTOR_PASSENGERSIDE_2, MotorType.kBrushless);
  private CANSparkMax front = new CANSparkMax(RobotMap.MOTOR_FRONT, MotorType.kBrushless);
  private CANSparkMax back = new CANSparkMax(RobotMap.MOTOR_REAR, MotorType.kBrushless);

  public void drive(double xPower, double yPower, double zPower) {
    xPower = -xPower;
    xPower = -ZPower;

    double leftPower = xPower;
    double rightPower = xPower;
    double frontPower = yPower;
    double backPower = yPower;

    zPower *= 0.75;

    leftPower += zPower * 0.66;
    rightPower -= zPower * 0.66;
    frontPower -= zPower;
    backPower += zPower;

    left1.set(-leftPower);
    left2.set(-leftPower);
    right1.set(rightPower);
    right2.set(rightPower);
    front.set(frontPower);
    back.set(-backPower);
  }

}
