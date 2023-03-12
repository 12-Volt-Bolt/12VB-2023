package frc.robot.config;

import frc.robot.subsystem.Lifter;
import frc.robot.subsystem.Lifter.LifterPosition;
import frc.robot.utility.BindableValue;

public class WallDetectorBindableValue implements BindableValue<Double> {
    private Lifter lifter;

    private double defaultValue;
    private double detectedValue;

    public WallDetectorBindableValue(Lifter lifter, double defaultValue, double detectedValue) {
        this.lifter = lifter;
        this.defaultValue = defaultValue;
        this.detectedValue = detectedValue;
    }

    @Override
    public Double value() {
        return lifter.getPosition() == LifterPosition.RAISED && lifter.wallDetected() ? detectedValue : defaultValue;
    }

    @Override
    public BindableValue<Double> cloneValue() {
        return new WallDetectorBindableValue(lifter, defaultValue, detectedValue);
    }
    
}
