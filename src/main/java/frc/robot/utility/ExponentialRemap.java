package frc.robot.utility;

public class ExponentialRemap {
    private BindableValue<Double> exponent;
    private BindableValue<Boolean> copySign;

    public ExponentialRemap(BindableValue<Double> exponent, BindableValue<Boolean> copySign) {
        this.exponent = exponent;
        this.copySign = copySign;
    }

    public ExponentialRemap(double exponent, boolean copySign) {
        this(
                new ReferenceBindableValue<Double>(exponent),
                new ReferenceBindableValue<Boolean>(copySign));
    }

    public double calculate(double value) {
        if (copySign.value() == true) {
            return Math.copySign(Math.pow(Math.abs(value), exponent.value()), value);
        } else {
            return Math.pow(Math.abs(value), exponent.value());
        }
    }
}
