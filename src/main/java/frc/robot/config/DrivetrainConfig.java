package frc.robot.config;

import java.util.Optional;

import frc.robot.subsystem.BoxDrive;
import frc.robot.subsystem.Lifter;
import frc.robot.utility.ReferenceBindableValue;
import frc.robot.utility.RapidStopSlewRateLimiter;
import frc.robot.utility.SlewRateLimiter;

public class DrivetrainConfig {
    public static void configSRL(BoxDrive drivetrain, Lifter lifter) {
        LifterUpDeceleration decelerationBindableValue = new LifterUpDeceleration(lifter);
    
        SlewRateLimiter frontBackSRL = new RapidStopSlewRateLimiter(
            new ReferenceBindableValue<Double>(0.015), 
            new ReferenceBindableValue<Double>(0.015), 
            decelerationBindableValue, 
            new ReferenceBindableValue<Double>(Double.POSITIVE_INFINITY));
    
        drivetrain.setSlewRateLimiters(Optional.of(frontBackSRL.cloneSRL()), Optional.of(frontBackSRL.cloneSRL()), Optional.empty(), Optional.empty());    
    
        
    }
}
