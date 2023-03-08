package frc.robot.utility;

public class RapidStopSlewRateLimiter implements SlewRateLimiter {

    private double previousValue;

    private double maxPositiveMagnitudeChange;
    private double maxNegativeMagnitudeChange;

    private double maxPositiveMagnitudeStopChange;
    private double maxNegativeMagnitudeStopChange;

    public RapidStopSlewRateLimiter(double maxPositiveMagnitudeChange, double maxNegativeMagnitudeChange, double maxPositiveMagnitudeStopChange, double maxNegativeMagnitudeStopChange) {
        this.maxPositiveMagnitudeChange = maxPositiveMagnitudeChange;
        this.maxNegativeMagnitudeChange = maxNegativeMagnitudeChange;

        this.maxPositiveMagnitudeStopChange = maxPositiveMagnitudeStopChange;
        this.maxNegativeMagnitudeStopChange = maxNegativeMagnitudeStopChange;
    }

    public RapidStopSlewRateLimiter(double maxChange, double maxStopChange) {
        this(maxChange, maxChange, maxStopChange, maxStopChange);
    }

    public RapidStopSlewRateLimiter(double maxChange) {
        this(maxChange, maxChange, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Override
    public double step(double nextValue) {
        if (Math.copySign(1, nextValue) != Math.copySign(1, previousValue) && previousValue != 0) {
            if (nextValue > 0) {
                previousValue += Math.min(maxNegativeMagnitudeStopChange, Math.abs(previousValue));
                return previousValue;
            } else if (nextValue < 0) {
                previousValue -= Math.min(maxPositiveMagnitudeStopChange, Math.abs(previousValue));
                return previousValue;
            }
        }

        double diff = nextValue - previousValue;
        if (nextValue > 0) {
            if (diff > 0) {
                if (diff > maxPositiveMagnitudeChange) {
                    previousValue += maxPositiveMagnitudeChange;
                    return previousValue;
                }
            } else {
                if (-diff > maxPositiveMagnitudeStopChange) {
                    previousValue += maxPositiveMagnitudeStopChange;
                    return previousValue;
                }
            }
        } else if (nextValue < 0) {
            diff = -diff;
            if (diff > 0) {
                if (diff > maxNegativeMagnitudeChange) {
                    previousValue -= maxNegativeMagnitudeChange;
                    return previousValue; 
                }
            } else {
                if (-diff > maxNegativeMagnitudeStopChange) {
                    previousValue -= maxNegativeMagnitudeStopChange;
                    return previousValue;
                }
            }
        }

        previousValue = nextValue;
        return previousValue;
    }

}
