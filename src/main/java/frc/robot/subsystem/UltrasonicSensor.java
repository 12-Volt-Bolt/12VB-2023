// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
/*
 * robotInit() turnOnSensor
 * {any}Periodic or command: value = getUltrasonicSensorRange()
 * disabledInit: turnOffSensor
 * 
 * 
 */
public class UltrasonicSensor extends SubsystemBase {
 
  private DigitalOutput ultrasonicTriggerPin;
  private AnalogInput ultrasonicSensor;
  private double ultrasonicSensorRange = 0;
  private double voltageScaleFactor = 1;

  /** Creates a new Ultrasonic sensor. */
  public UltrasonicSensor(int channel) {
      ultrasonicTriggerPin = new DigitalOutput(channel);
      ultrasonicSensor = new AnalogInput(channel);
    }

  public UltrasonicSensor() {
      ultrasonicTriggerPin = new DigitalOutput(0);
      ultrasonicSensor = new AnalogInput(0);
  }
  
  public void turnOnSensor() {
    ultrasonicTriggerPin.set(true);
  }

  public void turnOffSensor() {
    ultrasonicTriggerPin.set(false);
  }

  public double getUltrasonicSensorRange() {
    voltageScaleFactor = 5/RobotController.getVoltage5V(); //Calculate what percentage of 5 Volts we are actually at
    //Get a reading from the first sensor, scale it by the voltageScaleFactor, and then scale to Centimeters
    ultrasonicSensorRange = ultrasonicSensor.getValue()*voltageScaleFactor*0.125;
    SmartDashboard.putNumber("Sensor Range (cm)", ultrasonicSensorOneRange);
    return ultrasonicSensorRange;
  }

}
