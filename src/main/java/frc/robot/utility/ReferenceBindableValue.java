package frc.robot.utility;

public class ReferenceBindableValue<T> implements BindableValue<T> {
    private T value;

    public ReferenceBindableValue(T value) {
        this.value = value;
    }

    public void updateValue(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }
    
    @Override
    public ReferenceBindableValue<T> cloneValue() {
        return new ReferenceBindableValue<T>(value);
    }
}
