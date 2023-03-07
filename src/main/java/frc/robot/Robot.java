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
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.CloseGrabber;
import frc.robot.command.Drive;
import frc.robot.command.OpenGrabber;
import frc.robot.command.RaiseLifter;
import frc.robot.command.Wait;
import frc.robot.subsystem.Boxdrive;
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

  private Compressor compressor = new Compressor(RobotMap.PNEUMATICS_MODULE_TYPE);
  private Lifter lifter = new Lifter();
  private Grabber grabber = new Grabber();
  private Boxdrive boxdrive = new Boxdrive();

  private long autoStartTime = 0;

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

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    autoStartTime = System.currentTimeMillis();

    SequentialCommandGroup autoSequence = new Wait(100)
        .andThen(new CloseGrabber(grabber))
        .andThen(new RaiseLifter(lifter))
        .andThen(new Drive(boxdrive, 1000, 0, 0.2, 0))
        .andThen(new OpenGrabber(grabber));

  autoSequence.schedule();
  }

  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
    
    // boxdrive.drive(0, 0, 0);

    // if (System.currentTimeMillis() > autoStartTime && System.currentTimeMillis() < autoStartTime + 1000) {
    //   grabber.close();
    // }
    // if (System.currentTimeMillis() > autoStartTime + 1000 && System.currentTimeMillis() < autoStartTime + 2000) {
    //   lifter.raise();
    // }
    // if (System.currentTimeMillis() > autoStartTime + 2000 && System.currentTimeMillis() < autoStartTime + 3000) {
    //   boxdrive.drive(0, 0.2, 0);
    // }
    // if (System.currentTimeMillis() > autoStartTime + 3000 && System.currentTimeMillis() < autoStartTime + 4000) {
    //   grabber.open();
    // }
    // if (System.currentTimeMillis() > autoStartTime + 4000 && System.currentTimeMillis() < autoStartTime + 7000) {
    //   boxdrive.drive(0, -0.4, 0);
    // }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    boxdrive.drive(-controller1.getXDriveAxis(), controller1.getYDriveAxis(), -controller1.getZDriveAxis());

    if (controller1.getAButton()) {
      grabber.open();
    }
    if (controller1.getBButton()) {
      grabber.close();
    }

    if (controller1.isDPadUp()) {
      lifter.raise();
    }
    if (controller1.isDPadDown()) {
      lifter.lower();
    }
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
