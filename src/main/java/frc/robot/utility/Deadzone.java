package frc.robot.utility;

public class Deadzone {

    public static double deadzone(double value, double deadzone) {
        if (Math.abs(value) < Math.abs(deadzone)) {
            return 0;
        }
        return value;
    }
}
