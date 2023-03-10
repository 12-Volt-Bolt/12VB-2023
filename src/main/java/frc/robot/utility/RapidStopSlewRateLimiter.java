package frc.robot.utility;

public class RapidStopSlewRateLimiter implements SlewRateLimiter {

    private double previousValue;

    private double maxPositiveMagnitudeChange;
    private double maxNegativeMagnitudeChange;

    private double maxPositiveMagnitudeStopChange;
    private double maxNegativeMagnitudeStopChange;

    public RapidStopSlewRateLimiter(double maxPositiveMagnitudeChange, double maxNegativeMagnitudeChange, double maxPositiveMagnitudeStopChange, double maxNegativeMagnitudeStopChange) {
        this.maxPositiveMagnitudeChange = Math.abs(maxPositiveMagnitudeChange);
        this.maxNegativeMagnitudeChange = Math.abs(maxNegativeMagnitudeChange);

        this.maxPositiveMagnitudeStopChange = Math.abs(maxPositiveMagnitudeStopChange);
        this.maxNegativeMagnitudeStopChange = Math.abs(maxNegativeMagnitudeStopChange);
    }

    public RapidStopSlewRateLimiter(double maxChange, double maxStopChange) {
        this(maxChange, maxChange, maxStopChange, maxStopChange);
    }

    public RapidStopSlewRateLimiter(double maxChange) {
        this(maxChange, maxChange, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    private double setAndReturn(double newValue) {
        previousValue = newValue;
        return previousValue;
    }

    private double differentDirectionCalculation(double nextValue) {
        if (nextValue > 0) {
            return setAndReturn(previousValue + Math.min(maxNegativeMagnitudeStopChange, Math.abs(previousValue)));
        } else if (nextValue < 0) {
            return setAndReturn(previousValue - Math.min(maxPositiveMagnitudeStopChange, Math.abs(previousValue)));
        } 
        return setAndReturn(0);
    }

    private double positivePower(double nextValue, double diff) {
        if (diff > 0) {
            // traveling forward and speeding up
            if (diff > maxPositiveMagnitudeChange) {
                return setAndReturn(previousValue + maxPositiveMagnitudeChange);
            }
        } else {
            // traveling forward and slowing down
            if (-diff > maxPositiveMagnitudeStopChange) {
                return setAndReturn(previousValue - maxPositiveMagnitudeStopChange);
            }
        }
        return setAndReturn(nextValue);
    }

    private double negativePower(double nextValue, double diff) {
        if (diff < 0) {
            // traveling backward and speeding up
            if (-diff > maxNegativeMagnitudeChange) {
                return setAndReturn(previousValue - maxNegativeMagnitudeChange);
            }
        } else {
            // traveling backward and slowing down
            if (diff > maxNegativeMagnitudeStopChange) {
                return setAndReturn(previousValue + maxNegativeMagnitudeStopChange);
            }
        }
        return setAndReturn(nextValue);
    }

    @Override
    public double step(double nextValue) {
        if (Math.copySign(1, nextValue) != Math.copySign(1, previousValue) && previousValue != 0) {
            return differentDirectionCalculation(nextValue);
        }

        double diff = nextValue - previousValue;
        if (nextValue > 0) {
            return positivePower(nextValue, diff);
        } else if (nextValue < 0) {
            return negativePower(nextValue, diff);
        }

        return setAndReturn(nextValue);
    }

}
