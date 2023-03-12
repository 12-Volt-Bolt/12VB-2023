package frc.robot.utility;

public class DeadzoneWithLinearRemap {
    public BindableValue<Double> maxPositiveInput;
    public BindableValue<Double> maxNegativeInput;
    public BindableValue<Double> minPositiveInput;
    public BindableValue<Double> minNegativeInput;
    public BindableValue<Double> maxPositiveOutput;
    public BindableValue<Double> maxNegativeOutput;

    /**
     * Creates a remapper, which remaps an input to an output.
     * 
     * @param maxPositiveInput The maximum positive input value.
     * @param maxNegativeInput The maximum negative input value.
     * @param minPositiveInput The minumum positive magnitude the input must be before the output will change beyond zero.
     *                         Similar to a deadzone.
     * @param minNegativeInput The minumum negative magnitude the input must be before the output will change beyond zero.
     *                         Similar to a deadzone.
     * @param maxPositiveOutput The maximun positive output value.
     * @param maxNegativeOutput The maximun negative output value.
     */
    public DeadzoneWithLinearRemap(
            BindableValue<Double> maxPositiveInput,
            BindableValue<Double> maxNegativeInput,
            BindableValue<Double> minPositiveInput,
            BindableValue<Double> minNegativeInput,
            BindableValue<Double> maxPositiveOutput,
            BindableValue<Double> maxNegativeOutput) {
        this.maxPositiveInput = maxPositiveInput;
        this.maxNegativeInput = maxNegativeInput;
        this.minPositiveInput = minPositiveInput;
        this.minNegativeInput = minNegativeInput;
        this.maxPositiveOutput = maxPositiveOutput;
        this.maxNegativeOutput = maxNegativeOutput;
    }

    /**
     * Creates a remapper, which remaps an input to an output.
     * 
     * @param maxPositiveInput The maximum positive input value.
     * @param maxNegativeInput The maximum negative input value.
     * @param minPositiveInput The minumum positive magnitude the input must be before the output will change beyond zero.
     *                         Similar to a deadzone.
     * @param minNegativeInput The minumum negative magnitude the input must be before the output will change beyond zero.
     *                         Similar to a deadzone.
     * @param maxPositiveOutput The maximun positive output value.
     * @param maxNegativeOutput The maximun negative output value.
     */
    public DeadzoneWithLinearRemap(
            double maxPositiveInput,
            double maxNegativeInput,
            double minPositiveInput,
            double minNegativeInput,
            double maxPositiveOutput,
            double maxNegativeOutput) {
        this(
                new ReferenceBindableValue<Double>(maxPositiveInput),
                new ReferenceBindableValue<Double>(maxNegativeInput),
                new ReferenceBindableValue<Double>(minPositiveInput),
                new ReferenceBindableValue<Double>(minNegativeInput),
                new ReferenceBindableValue<Double>(maxPositiveOutput),
                new ReferenceBindableValue<Double>(maxNegativeOutput));
    }

    /**
     * Creates a remapper, which remaps an input to an output.
     * <p>
     * This method uses the same magnitudes for remaping positive and negative values.
     * "Magnitude" parameters are negated for negative remaps.
     * 
     * @param maxInputMagnitude  The maximum input magnitude.
     * @param minInputMagnitude  The minumum value the input must be before the output will change beyond zero.
     *                           Equivilant to a deadzone.
     * @param maxOutputMagnitude The maximun output magnitude.
     */
    public DeadzoneWithLinearRemap(
            double maxInputMagnitude,
            double minInputMagnitude,
            double maxOutputMagnitude) {
        this(
                maxInputMagnitude,
                -maxInputMagnitude,
                minInputMagnitude,
                -minInputMagnitude,
                maxOutputMagnitude,
                -maxOutputMagnitude);
    }

    /**
     * Creates a remapper, which remaps an input to an output.
     * <p>
     * The same magnitudes will be used for remaping positive and negative values.
     * 
     * @param maxInputMagnitude  The maximum input magnitude.
     * @param maxOutputMagnitude The maximun output magnitude.
     */
    public DeadzoneWithLinearRemap(
            double maxInputMagnitude,
            double maxOutputMagnitude) {
        this(
                maxInputMagnitude,
                -maxInputMagnitude,
                0,
                -0,
                maxOutputMagnitude,
                -maxOutputMagnitude);
    }

    /**
     * Remaps the input using this remapper's settings.
     * @param input The value to remap.
     * @return The remaped value.
     */
    public double remap(double input) {
        if (input < minPositiveInput.value() && input > minNegativeInput.value()) {
            return 0;
        }

        input = Math.min(input, maxPositiveInput.value());
        input = Math.max(input, maxNegativeInput.value());

        if (input >= minPositiveInput.value()) {
            return (input - minPositiveInput.value()) / (maxPositiveInput.value() - minPositiveInput.value())
                    * maxPositiveOutput.value();
        } else if (input <= minNegativeInput.value()) {
            return (input - minNegativeInput.value()) / (maxNegativeInput.value() - minNegativeInput.value())
                    * maxNegativeOutput.value();
        }

        return input;
    }
}
