package frc.robot.config;

import java.util.ArrayList;
import java.util.List;

import frc.robot.DriverController;
import frc.robot.utility.DeadzoneWithLinearRemap;
import frc.robot.utility.ExponentialRemap;
import frc.robot.utility.Remapper;
import frc.robot.utility.RemapperChain;

public class DriverControllerConfig {
    public static RemapperChain<Double> yInputRemappers(DriverController controller) {
        List<Remapper<Double>> remappers = new ArrayList<>();

        // deadzone
        remappers.add(new DeadzoneWithLinearRemap(1, 0.2, 1));

        // turbo mode
        DeadzoneWithLinearRemap yDeadzone = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue yBindValue = new TurboModeBindableValue(controller, 0.5, 1.0);
        yDeadzone.maxPositiveOutput = yBindValue.cloneValue();
        yDeadzone.maxNegativeOutput = yBindValue.cloneValue().negate();
        remappers.add(yDeadzone);

        // throttle curve
        remappers.add(new ExponentialRemap(1.5, true));
        
        return new RemapperChain<>(remappers);
    }
    
    public static RemapperChain<Double> xInputRemappers(DriverController controller) {
        List<Remapper<Double>> remappers = new ArrayList<>();

        // deadzone
        remappers.add(new DeadzoneWithLinearRemap(1, 0.2, 1));

        // turbo mode
        DeadzoneWithLinearRemap xDeadzone = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue xBindValue = new TurboModeBindableValue(controller, 0.5, 1.0);
        xDeadzone.maxPositiveOutput = xBindValue.cloneValue();
        xDeadzone.maxNegativeOutput = xBindValue.cloneValue().negate();
        remappers.add(xDeadzone);

        // throttle curve
        remappers.add(new ExponentialRemap(1.5, true));
        
        return new RemapperChain<>(remappers);
    }
    
    public static RemapperChain<Double> yawInputRemappers(DriverController controller) {
        List<Remapper<Double>> remappers = new ArrayList<>();

        // deadzone
        remappers.add(new DeadzoneWithLinearRemap(1, 0.2, 1));

        // turbo mode
        DeadzoneWithLinearRemap yawDeadzone = new DeadzoneWithLinearRemap(1.0, 0.0, 1.0);
        TurboModeBindableValue yawBindValue = new TurboModeBindableValue(controller, 0.7, 1.0);
        yawDeadzone.maxPositiveOutput = yawBindValue.cloneValue();
        yawDeadzone.maxNegativeOutput = yawBindValue.cloneValue().negate();
        remappers.add(yawDeadzone);

        // throttle curve
        remappers.add(new ExponentialRemap(1.5, true));
        
        return new RemapperChain<>(remappers);
    }
}
