// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.CloseGrabber;
import frc.robot.command.Drive;
import frc.robot.command.LowerLifter;
import frc.robot.command.OpenGrabber;
import frc.robot.command.RaiseLifter;
import frc.robot.command.SetIdleMode;
import frc.robot.command.Wait;
import frc.robot.config.DrivetrainConfig;
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

  private BoxDrive drivetrain = new BoxDrive();

  private Compressor compressor = new Compressor(RobotMap.PNEUMATICS_MODULE_TYPE);
  private Lifter lifter = new Lifter(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.LIFTER_CHANNEL);
  private Grabber grabber = new Grabber(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.GRABBER_CHANNEL);

  public DriverController controller1 = new DriverController(0, lifter);

  private SequentialCommandGroup coastOnDisable = new Wait(10000)
      .ignoringDisable(true)
      .andThen(
          new SetIdleMode(drivetrain, IdleMode.kCoast)
              .ignoringDisable(true));

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    DrivetrainConfig.configSRL(drivetrain, lifter);

    compressor.enableDigital();
    lifter.lower();
    grabber.open();

    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    coastOnDisable.cancel();

    SequentialCommandGroup autoSequence = new Wait(100)
        .andThen(new CloseGrabber(grabber))
        .andThen(new RaiseLifter(lifter))
        .andThen(new Drive(drivetrain, 1000, 0, 0.2, 0))
        .andThen(new OpenGrabber(grabber))
        .andThen(new LowerLifter(lifter), new Drive(drivetrain, 3000, 0, -0.4, 0));

    autoSequence.schedule();
    drivetrain.setIdleMode(IdleMode.kBrake);
  }
  
  @Override
  public void teleopInit() {
    coastOnDisable.cancel();

    lifter.lower();
    grabber.open();
    drivetrain.setIdleMode(IdleMode.kBrake);
  }
  

  @Override
  public void teleopPeriodic() {
    drivetrain.drive(controller1.yDriveAxis(), controller1.xDriveAxis(), controller1.yawDriveAxis());

    if (controller1.closeGrabber()) {
      grabber.open();
    }
    if (controller1.openGrabber()) {
      grabber.close();
    }

    if (controller1.raiseLifter()) {
      lifter.raise();
    }
    if (controller1.lowerLifter()) {
      lifter.lower();
    }
  }
  
  @Override
  public void disabledInit() {
    coastOnDisable.schedule();
  }

  /* 
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
