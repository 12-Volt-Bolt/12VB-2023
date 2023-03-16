// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import java.util.Optional;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lifter extends SubsystemBase {

  private static final double CM_SCALE_FACTOR = 0.125;

  public static enum LifterPosition {
    RAISED,
    LOWERED
  }
 
  private Solenoid upSolenoid;
  private Solenoid downSolenoid;
  private Optional<AnalogInput> ultrasonicSensor = Optional.empty();
  private double wallDetectorLimitCm = 150;

  /** Creates a new Lifter. */
  public Lifter(
      PneumaticsModuleType moduleType, 
      int upChannel, 
      int downChannel, 
      Optional<Integer> sensorChannel) {
    upSolenoid = new Solenoid(moduleType, upChannel);
    downSolenoid = new Solenoid(moduleType, downChannel);

    if (sensorChannel.isPresent()) {
      ultrasonicSensor = Optional.of(new AnalogInput(sensorChannel.get()));
    }
  }

  public Lifter setWallDetectorLimitCm(double distance) {
    wallDetectorLimitCm = distance;
    return this;
  }

  public void raise() {
    upSolenoid.set(true);
    downSolenoid.set(true);
  }

  public void lower() {
    upSolenoid.set(false);
    downSolenoid.set(false);
  }

  public LifterPosition getPosition() {
    return upSolenoid.get() ? LifterPosition.RAISED : LifterPosition.LOWERED;
  }

  public void setPosition(LifterPosition position) {
    switch (position) {
      case LOWERED:
        lower();
        break;
      case RAISED:
        raise();
        break;
      default:
        throw new RuntimeException("Lifter 'setPosition' method missing switch case!");
    }
  }

  public boolean wallDetected() {
    if (ultrasonicSensor.isEmpty()) {
      return false;
    } 
    
    double voltageScaleFactor = 5 / RobotController.getVoltage5V(); //Calculate what percentage of 5 Volts we are actually at
    //Get a reading from the first sensor, scale it by the voltageScaleFactor, and then scale to Centimeters
    double detectedDistanceCm = ultrasonicSensor.get().getValue() * voltageScaleFactor * CM_SCALE_FACTOR;
    SmartDashboard.putNumber("Sensor Range (cm)", detectedDistanceCm);

    return detectedDistanceCm < wallDetectorLimitCm;
  }
}
