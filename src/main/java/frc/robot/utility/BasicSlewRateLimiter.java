package frc.robot.utility;

public class BasicSlewRateLimiter extends SlewRateLimiter {

    private double previousValue;

    private double maxPositiveChange;
    private double maxNegativeChange;

    public BasicSlewRateLimiter(double maxPositiveChange, double maxNegativeChange) {
        this.maxPositiveChange = maxPositiveChange;
        this.maxNegativeChange = maxNegativeChange;
    }

    public BasicSlewRateLimiter(double maxChange) {
        this(maxChange, maxChange);
    }

    @Override
    public Double calculate(Double nextValue) {
        if (nextValue > previousValue && nextValue - previousValue > maxPositiveChange) {
            previousValue += maxPositiveChange;
            return previousValue;
        }
        
        if (nextValue < previousValue && nextValue - previousValue > maxNegativeChange) {
            previousValue += maxNegativeChange;
            return previousValue;
        }

        previousValue = nextValue;
        return previousValue;
    }

    @Override
    public SlewRateLimiter cloneSRL() {
        return new BasicSlewRateLimiter(maxPositiveChange, maxNegativeChange);
    }
}
