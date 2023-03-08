package frc.robot.utility;

public class Deadzone {

    public static double deadzone(double value, double deadzone) {
        if (Math.abs(value) < Math.abs(deadzone)) {
            return 0;
        }
        return value;
    }

    public static double inputRemap(double input, double maxMagnitude, double minMagnitude) {
        return inputRemap(input, maxMagnitude, minMagnitude, -maxMagnitude, -minMagnitude);
    }

    public static double inputRemap(double input, double maxPositive, double minPositive, double maxNegative, double minNegative) {
        if (input < minPositive && input > minNegative) {
            return 0;
        }

        if (input > minPositive) {
            return (input - minPositive) / (maxPositive - minPositive);
        } else if (input > minNegative) {
            return (input - minNegative) / (maxNegative - minNegative);
        }

        return input;
    }    
}
