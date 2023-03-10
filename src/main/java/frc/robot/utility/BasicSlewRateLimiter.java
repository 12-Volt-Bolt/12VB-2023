package frc.robot.utility;

public class BasicSlewRateLimiter implements SlewRateLimiter {

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

    public double step(double nextValue) {
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
    public SlewRateLimiter clone() {
        return new BasicSlewRateLimiter(maxPositiveChange, maxNegativeChange);
    }
}
