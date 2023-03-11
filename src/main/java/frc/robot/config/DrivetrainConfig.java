package frc.robot.config;

import java.util.Optional;

import frc.robot.subsystem.BoxDrive;
import frc.robot.subsystem.Lifter;
import frc.robot.utility.ConstantBindableValue;
import frc.robot.utility.RapidStopSlewRateLimiter;
import frc.robot.utility.SlewRateLimiter;

public class DrivetrainConfig {
    public static void configSRL(BoxDrive drivetrain, Lifter lifter) {
        LifterUpDeceleration decelerationBindableValue = new LifterUpDeceleration(lifter);
    
        SlewRateLimiter frontBackSRL = new RapidStopSlewRateLimiter(
            new ConstantBindableValue<Double>(0.015), 
            new ConstantBindableValue<Double>(0.015), 
            decelerationBindableValue, 
            new ConstantBindableValue<Double>(Double.POSITIVE_INFINITY));
    
        drivetrain.setSlewRateLimiters(Optional.of(frontBackSRL.clone()), Optional.of(frontBackSRL.clone()), Optional.empty(), Optional.empty());    
    
        
    }
}
