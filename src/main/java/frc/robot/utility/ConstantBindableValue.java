package frc.robot.utility;

public class ConstantBindableValue<T> implements BindableValue<T> {
    private T value;

    public ConstantBindableValue(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }
    
}
