package frc.robot.config;

import frc.robot.DriverController;
import frc.robot.utility.BindableValue;

public class TurboModeBindableValue implements BindableValue<Double> {
    private DriverController controller;
    private double defaultValue;
    private double turboValue;

    public TurboModeBindableValue(DriverController controller, double defaultValue, double turboValue) {
        this.controller = controller;
        this.defaultValue = defaultValue;
        this.turboValue = turboValue;
    }

    public TurboModeBindableValue negate() {
        defaultValue = -defaultValue;
        turboValue = -turboValue;
        return this;
    }

    @Override
    public Double value() {
        return controller.turboMode() ? turboValue : defaultValue;
    }
    
    @Override 
    public TurboModeBindableValue cloneValue() {
        return new TurboModeBindableValue(controller, defaultValue, turboValue);
    }
}
