package frc.robot.config;

import frc.robot.subsystem.Lifter;
import frc.robot.subsystem.Lifter.LifterPosition;
import frc.robot.utility.BindableValue;

public class LifterUpDeceleration implements BindableValue<Double> {
    private Lifter lifter;

    public LifterUpDeceleration(Lifter lifter) {
        this.lifter = lifter;
    }

    @Override
    public Double value() {
      return lifter.getPosition() == LifterPosition.RAISED ? 0.02 : Double.POSITIVE_INFINITY;
    }
    
    @Override
    public BindableValue<Double> clone() {
      return new LifterUpDeceleration(lifter);
    }
}
