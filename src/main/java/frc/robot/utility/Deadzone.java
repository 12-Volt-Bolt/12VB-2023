package frc.robot.utility;

public class Deadzone {

    public static double deadzone(double value, double deadzone) {
        if (Math.abs(value) < Math.abs(deadzone)) {
            return 0;
        }
        return value;
    }

    public static double inputRemap(double input, double maxPositiveInput, double maxNegativeInput, double minPositiveInput, double minNegativeInput, double maxPositiveOutput, double maxNegativeOutput) {
        if (input < minPositiveInput && input > minNegativeInput) {
            return 0;
        }

        input = Math.min(input, maxPositiveInput);
        input = Math.max(input, maxNegativeInput);
        
        if (input >= minPositiveInput) {
            return (input - minPositiveInput) / (maxPositiveInput - minPositiveInput) * maxPositiveOutput;
        } else if (input <= minNegativeInput) {
            return (input - minNegativeInput) / (maxNegativeInput - minNegativeInput) * maxNegativeOutput;
        }

        return input;
    }  

    /**
     * Remaps an input to an output.
     * <p>
     * This method uses the same magnitudes for remaping positive and negative values.
     * "Magnitude" parameters are negated for negative remaps. 
     * 
     * @param input The value to remap.
     * @param maxInputMagnitude The maximum input magnitude. 
     * @param minInputMagnitude The minumum value the input must be for the output
     * @param maxOutputMagnitude The maximun output magnitude.
     * @return The remaped value.
     */
    public static double inputRemap(double input, double maxInputMagnitude, double minInputMagnitude, double maxOutputMagnitude) {
        return inputRemap(input, maxInputMagnitude, -maxInputMagnitude, minInputMagnitude, -minInputMagnitude, maxOutputMagnitude, -maxOutputMagnitude);
    }

    /**
     * Remaps an input to an output.
     * <p>
     * This method uses the same magnitudes for remaping positive and negative values.
     * "Magnitude" parameters are negated for negative remaps. 
     * 
     * @param input The value to remap.
     * @param maxInputMagnitude The maximum input magnitude. 
     * @param maxOutputMagnitude The maximun output magnitude.
     * @return The remaped value.
     */
    public static double inputRemap(double input, double maxInputMagnitude, double maxOutputMagnitude) {
        return inputRemap(input, maxInputMagnitude, -maxInputMagnitude, 0, -0, maxOutputMagnitude, -maxOutputMagnitude);
    }  
}
