package frc.robot.utility;

public class RapidStopSlewRateLimiter extends SlewRateLimiter {

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
                new ReferenceBindableValue<Double>(Math.abs(maxPositiveMagnitudeChange)),
                new ReferenceBindableValue<Double>(Math.abs(maxNegativeMagnitudeChange)),
                new ReferenceBindableValue<Double>(Math.abs(maxPositiveMagnitudeStopChange)),
                new ReferenceBindableValue<Double>(Math.abs(maxNegativeMagnitudeStopChange)));
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

    @Override
    public double step(double nextValue) {
        if (previousValue > 0) {
            // traveling forward
            if (nextValue > previousValue) {
                // accelerating
                if (nextValue - previousValue > maxPositiveMagnitudeChange.value()) {
                    return setAndReturn(previousValue + maxPositiveMagnitudeChange.value());
                }
            } else if (nextValue < previousValue) {
                // decelerating
                if (previousValue - nextValue > maxPositiveMagnitudeStopChange.value()) {
                    return setAndReturn(previousValue - maxPositiveMagnitudeStopChange.value());
                }
            }
        } else if (previousValue < 0) {
            // traveling backward
            if (nextValue < previousValue) {
                // accelerating
                if (previousValue - nextValue > maxNegativeMagnitudeChange.value()) {
                    return setAndReturn(previousValue - maxNegativeMagnitudeChange.value());
                }
            } else if (nextValue > previousValue) {
                // decelerating
                if (nextValue - previousValue > maxNegativeMagnitudeStopChange.value()) {
                    return setAndReturn(previousValue + maxNegativeMagnitudeStopChange.value());
                }
            }
        } else {
            // currently stopped
            if (nextValue > 0) {
                if (nextValue > maxPositiveMagnitudeChange.value()) {
                    return setAndReturn(maxPositiveMagnitudeChange.value());
                }
            } else if (nextValue < 0) {
                if (-nextValue > maxNegativeMagnitudeChange.value()) {
                    return setAndReturn(-maxNegativeMagnitudeChange.value());
                }
            }
        }

        return setAndReturn(nextValue);
    }

    @Override
    public SlewRateLimiter cloneSRL() {
        return new RapidStopSlewRateLimiter(
                maxPositiveMagnitudeChange.cloneValue(), 
                maxNegativeMagnitudeChange.cloneValue(), 
                maxPositiveMagnitudeStopChange.cloneValue(), 
                maxNegativeMagnitudeStopChange.cloneValue());
    }
}
