// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static Joystick controller1 = new Joystick(0);

  private CANSparkMax left1 = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax left2 = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax right1 = new CANSparkMax(7, MotorType.kBrushless);
  private CANSparkMax right2 = new CANSparkMax(8, MotorType.kBrushless);
  private CANSparkMax front = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax back = new CANSparkMax(10, MotorType.kBrushless);

  private Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  private Solenoid lifter = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
  private Solenoid grabber = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    compressor.enableDigital();
    lifter.set(false);
    grabber.set(false);

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    double xpower = -controller1.getRawAxis(1);
    double ypower = controller1.getRawAxis(0);
    double zpower = -controller1.getRawAxis(4);
    
    double leftPower = xpower;
    double rightPower = xpower;
    double frontPower = ypower;
    double backPower = ypower;

    zpower *= 0.75;

    leftPower += zpower * 0.66;
    rightPower -= zpower * 0.66;
    frontPower -= zpower;
    backPower += zpower;

    // left1.set(-leftPower);
    // left2.set(-leftPower);
    // right1.set(rightPower);
    // right2.set(rightPower);
    // front.set(frontPower);
    // back.set(-backPower);

    if(controller1.getRawButton(1)) {
      grabber.set(false);
    }
    if (controller1.getRawButton(2)) {
      grabber.set(true);
    }

    if(controller1.getPOV() == 0) {
      lifter.set(false);
    }
    if(controller1.getPOV() == 180) {
      lifter.set(true);
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
