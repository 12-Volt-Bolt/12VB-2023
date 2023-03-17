package frc.robot.config;

import frc.robot.DriverController;
import frc.robot.subsystem.Lifter;
import frc.robot.utility.DeadzoneWithLinearRemap;
import frc.robot.utility.ExponentialRemap;

public class DriverControllerConfig {
    private static DeadzoneWithLinearRemap deadzone() {
        return new DeadzoneWithLinearRemap(1, 0.2, 1);
    }

    private static ExponentialRemap throttleCurve() {
        return new ExponentialRemap(1.5, true);
    }
    
    private static DeadzoneWithLinearRemap turboMode(DriverController controller, double defaultValue, double turboValue) {
        DeadzoneWithLinearRemap turboRemapper = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue turboOutputLimit = new TurboModeBindableValue(controller, defaultValue, turboValue);
        turboRemapper.maxPositiveOutput = turboOutputLimit.cloneValue();
        turboRemapper.maxNegativeOutput = turboOutputLimit.cloneValue().negate();
        return turboRemapper;
    }

    private static DeadzoneWithLinearRemap wallDetector(Lifter lifter) {
        DeadzoneWithLinearRemap wallDetectorRemapper = new DeadzoneWithLinearRemap(1.0, 1.0);
        wallDetectorRemapper.maxPositiveOutput = new WallDetectorBindableValue(lifter, 1.0, 0.25);
        return wallDetectorRemapper;
    }

    public static DriverController configDriverController(
            DriverController driverController, 
            Lifter lifter) {
        driverController.yRemappers()
                    .addRemapper(deadzone())
                    .addRemapper(throttleCurve())
                    .addRemapper(turboMode(driverController, 0.7, 1.0))
                    .addRemapper(wallDetector(lifter));
                    
        driverController.xRemappers()
                    .addRemapper(deadzone())
                    .addRemapper(throttleCurve())
                    .addRemapper(turboMode(driverController, 0.7, 1.0));
                    
        driverController.yawRemappers()
                    .addRemapper(deadzone())
                    .addRemapper(throttleCurve())
                    .addRemapper(turboMode(driverController, 0.7, 1.0));

        return driverController;
    }
}
