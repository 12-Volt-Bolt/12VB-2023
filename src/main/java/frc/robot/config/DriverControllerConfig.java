package frc.robot.config;

import java.util.ArrayList;
import java.util.List;

import frc.robot.DriverController;
import frc.robot.subsystem.Lifter;
import frc.robot.utility.DeadzoneWithLinearRemap;
import frc.robot.utility.ExponentialRemap;
import frc.robot.utility.Remapper;
import frc.robot.utility.RemapperChain;

public class DriverControllerConfig {
    public static RemapperChain<Double> yInputRemappers(DriverController controller, Lifter lifter) {
        List<Remapper<Double>> remappers = new ArrayList<>();

        // deadzone
        remappers.add(new DeadzoneWithLinearRemap(1, 0.2, 1));

        // throttle curve
        remappers.add(new ExponentialRemap(1.5, true));
        
        // turbo mode
        DeadzoneWithLinearRemap turboRemapper = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue turboOutputLimit = new TurboModeBindableValue(controller, 0.5, 1.0);
        turboRemapper.maxPositiveOutput = turboOutputLimit.cloneValue();
        turboRemapper.maxNegativeOutput = turboOutputLimit.cloneValue().negate();
        remappers.add(turboRemapper);

        // wall detection
        
        DeadzoneWithLinearRemap wallDetectorRemapper = new DeadzoneWithLinearRemap(1.0, 1.0);
        wallDetectorRemapper.maxPositiveOutput = new WallDetectorBindableValue(lifter, 1.0, 0.25);
        wallDetectorRemapper.maxPositiveInput = turboOutputLimit.cloneValue();
        remappers.add(wallDetectorRemapper);

        return new RemapperChain<>(remappers);
    }
    
    public static RemapperChain<Double> xInputRemappers(DriverController controller) {
        List<Remapper<Double>> remappers = new ArrayList<>();

        // deadzone
        remappers.add(new DeadzoneWithLinearRemap(1, 0.2, 1));

        // throttle curve
        remappers.add(new ExponentialRemap(1.5, true));
        
        // turbo mode
        DeadzoneWithLinearRemap turboRemapper = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue turboOutputLimit = new TurboModeBindableValue(controller, 0.5, 1.0);
        turboRemapper.maxPositiveOutput = turboOutputLimit.cloneValue();
        turboRemapper.maxNegativeOutput = turboOutputLimit.cloneValue().negate();
        remappers.add(turboRemapper);

        return new RemapperChain<>(remappers);
    }
    
    public static RemapperChain<Double> yawInputRemappers(DriverController controller) {
        List<Remapper<Double>> remappers = new ArrayList<>();

        // deadzone
        remappers.add(new DeadzoneWithLinearRemap(1, 0.2, 1));

        // throttle curve
        remappers.add(new ExponentialRemap(1.5, true));
        
        // turbo mode
        DeadzoneWithLinearRemap turboLimiter = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue turboOutputLimit = new TurboModeBindableValue(controller, 0.7, 1.0);
        turboLimiter.maxPositiveOutput = turboOutputLimit.cloneValue();
        turboLimiter.maxNegativeOutput = turboOutputLimit.cloneValue().negate();
        remappers.add(turboLimiter);

        return new RemapperChain<>(remappers);
    }
}
