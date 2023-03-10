package frc.robot.utility;

public class ThrottleCurve {
    public static double calculate(double input, double exponent) {
        return Math.copySign(Math.pow(Math.abs(input), exponent), input);
    }
}
