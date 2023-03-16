// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.DrivetrainIdleMode;
import frc.robot.command.CloseGrabber;
import frc.robot.command.CompressorController;
import frc.robot.command.Drive;
import frc.robot.command.LowerLifter;
import frc.robot.command.OpenGrabber;
import frc.robot.command.RaiseLifter;
import frc.robot.command.SetIdleMode;
import frc.robot.command.Wait;
import frc.robot.config.DriverControllerConfig;
import frc.robot.config.DrivetrainConfig;
import frc.robot.subsystem.BoxDrive;
import frc.robot.subsystem.Drivetrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;
import frc.robot.subsystem.PneumaticBrake;

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

  private Compressor compressor = new Compressor(RobotMap.PNEUMATICS_MODULE_TYPE);

  private Lifter lifter = new Lifter(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.LIFTER_UP_CHANNEL, RobotMap.LIFTER_DOWN_CHANNEL, Optional.of(0));
  private Drivetrain drivetrain = DrivetrainConfig.configBoxDrive(new BoxDrive(), lifter);
  private Grabber grabber = new Grabber(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.GRABBER_CHANNEL);
  private PneumaticBrake pneumaticBrake = new PneumaticBrake(RobotMap.PNEUMATICS_MODULE_TYPE, RobotMap.PNEUMATIC_BRAKE_DOWN_CHANNEL, RobotMap.PNEUMATIC_BRAKE_DOWN_CHANNEL);

  public DriverController driverController = DriverControllerConfig.configDriverController(new DriverController(0), lifter);
  public CodriverController codriverController = new CodriverController(1);

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
    drivetrain.forceStop = pneumaticBrake;
                
    compressor.enableDigital();
    lifter.lower();
    grabber.open();

    CameraServer.startAutomaticCapture();

    SmartDashboard.putData("Activite brake", new DrivetrainIdleMode(drivetrain, IdleMode.kBrake, false).ignoringDisable(true));
    SmartDashboard.putData("Deactivite brake", new DrivetrainIdleMode(drivetrain, IdleMode.kCoast, false).ignoringDisable(true));
    SmartDashboard.putData("Force brake on", new DrivetrainIdleMode(drivetrain, IdleMode.kBrake, true).ignoringDisable(true));
    SmartDashboard.putData("Force brake off", new DrivetrainIdleMode(drivetrain, IdleMode.kCoast, true).ignoringDisable(true));

    SmartDashboard.putData("Force compressor off", new CompressorController(compressor, false, true).ignoringDisable(true));
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
    drivetrain.drive(driverController.yDriveAxis(), driverController.xDriveAxis(), driverController.yawDriveAxis());

    if (driverController.closeGrabber()) {
      grabber.close();
    }
    if (driverController.openGrabber()) {
      grabber.open();
    }

    if (driverController.raiseLifter()) {
      lifter.raise();
    }
    if (driverController.lowerLifter()) {
      lifter.lower();
    }

    if (codriverController.isConnected()) {
      if (codriverController.raiseLifter()) {
        lifter.raise();
      }
      if (codriverController.lowerLifter()) {
        lifter.lower();
      }
    }

    if (driverController.togglePneumaticBrake()) {
      pneumaticBrake.toggle(); 
    }
  }
  
  @Override
  public void disabledInit() {
    coastOnDisable = new Wait(10000)
      .ignoringDisable(true)
      .andThen(
          new SetIdleMode(drivetrain, IdleMode.kCoast)
              .ignoringDisable(true));
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
