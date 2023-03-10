package frc.robot.utility;

public interface BindableValue<T> {
    public T value();

    public BindableValue<T> clone();
}
