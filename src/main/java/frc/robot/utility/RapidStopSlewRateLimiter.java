package frc.robot.utility;

public class RapidStopSlewRateLimiter implements SlewRateLimiter {

    private double previousValue;

    private BindableValue<Double> maxPositiveMagnitudeChange;
    private BindableValue<Double> maxNegativeMagnitudeChange;

    private BindableValue<Double> maxPositiveMagnitudeStopChange;
    private BindableValue<Double> maxNegativeMagnitudeStopChange;

    /**
     * All magnitudes should give positive values.
     * @param maxPositiveMagnitudeChange
     * @param maxNegativeMagnitudeChange
     * @param maxPositiveMagnitudeStopChange
     * @param maxNegativeMagnitudeStopChange
     */
    public RapidStopSlewRateLimiter(
            BindableValue<Double> maxPositiveMagnitudeChange,
            BindableValue<Double> maxNegativeMagnitudeChange,
            BindableValue<Double> maxPositiveMagnitudeStopChange,
            BindableValue<Double> maxNegativeMagnitudeStopChange) {
        this.maxPositiveMagnitudeChange = maxPositiveMagnitudeChange;
        this.maxNegativeMagnitudeChange = maxNegativeMagnitudeChange;

        this.maxPositiveMagnitudeStopChange = maxPositiveMagnitudeStopChange;
        this.maxNegativeMagnitudeStopChange = maxNegativeMagnitudeStopChange;
    }

    public RapidStopSlewRateLimiter(
            double maxPositiveMagnitudeChange,
            double maxNegativeMagnitudeChange,
            double maxPositiveMagnitudeStopChange,
            double maxNegativeMagnitudeStopChange) {
        this(
                new ConstantBindableValue<Double>(Math.abs(maxPositiveMagnitudeChange)),
                new ConstantBindableValue<Double>(Math.abs(maxNegativeMagnitudeChange)),
                new ConstantBindableValue<Double>(Math.abs(maxPositiveMagnitudeStopChange)),
                new ConstantBindableValue<Double>(Math.abs(maxNegativeMagnitudeStopChange)));
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
            return setAndReturn(
                    previousValue + Math.min(maxNegativeMagnitudeStopChange.value(), Math.abs(previousValue)));
        } else if (nextValue < 0) {
            return setAndReturn(
                    previousValue - Math.min(maxPositiveMagnitudeStopChange.value(), Math.abs(previousValue)));
        }
        return setAndReturn(0);
    }

    private double positivePower(double nextValue, double diff) {
        if (diff > 0) {
            // traveling forward and speeding up
            if (diff > maxPositiveMagnitudeChange.value()) {
                return setAndReturn(previousValue + maxPositiveMagnitudeChange.value());
            }
        } else {
            // traveling forward and slowing down
            if (-diff > maxPositiveMagnitudeStopChange.value()) {
                return setAndReturn(previousValue - maxPositiveMagnitudeStopChange.value());
            }
        }
        return setAndReturn(nextValue);
    }

    private double negativePower(double nextValue, double diff) {
        if (diff < 0) {
            // traveling backward and speeding up
            if (-diff > maxNegativeMagnitudeChange.value()) {
                return setAndReturn(previousValue - maxNegativeMagnitudeChange.value());
            }
        } else {
            // traveling backward and slowing down
            if (diff > maxNegativeMagnitudeStopChange.value()) {
                return setAndReturn(previousValue + maxNegativeMagnitudeStopChange.value());
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

    @Override
    public SlewRateLimiter clone() {
        return new RapidStopSlewRateLimiter(
                maxPositiveMagnitudeChange.clone(), 
                maxNegativeMagnitudeChange.clone(), 
                maxPositiveMagnitudeStopChange.clone(), 
                maxNegativeMagnitudeStopChange.clone());
    }
}
