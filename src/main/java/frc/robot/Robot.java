// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystem.BoxDrive;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static DriverController controller1 = new DriverController(0);

  public BoxDrive drivetrain = new BoxDrive();

  private Compressor compressor = new Compressor(RobotMap.PNEUMATICS_MODULE_TYPE);
  private Lifter lifter = new Lifter(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.LIFTER_CHANNEL);
  private Grabber grabber = new Grabber(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.GRABBER_CHANNEL);

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    compressor.enableDigital();
    lifter.lower();
    grabber.open();

  }

  /*
   * @Override
   * public void robotPeriodic() {
   * }
   * 
   * @Override
   * public void autonomousInit() {
   * }
   * 
   * @Override
   * public void autonomousPeriodic() {
   * }
   * 
   * @Override
   * public void teleopInit() {
   * }
   */

  @Override
  public void teleopPeriodic() {

    drivetrain.drive(-controller1.getXDriveAxis(), controller1.getYDriveAxis(),  -controller1.getZDriveAxis());

    if (controller1.getAButton()) {
      grabber.open();
    }
    if (controller1.getBButton()) {
      grabber.close();
    }

    if (controller1.isDpadUp()) {
      lifter.raise();
    }
    if (controller1.isDpadDown()) {
      lifter.lower();
    }
  }

  /*
   * @Override
   * public void disabledInit() {
   * }
   * 
   * @Override
   * public void disabledPeriodic() {
   * }
   * 
   * @Override
   * public void testInit() {
   * }
   * 
   * @Override
   * public void testPeriodic() {
   * }
   * 
   * @Override
   * public void simulationInit() {
   * }
   * 
   * @Override
   * public void simulationPeriodic() {
   * }
   */
}
