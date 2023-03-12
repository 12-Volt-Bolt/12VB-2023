package frc.robot.utility;

import java.util.ArrayList;
import java.util.List;

public class RemapperChain<T> implements Remapper<T> {
    public List<Remapper<T>> remappers = new ArrayList<>();

    public RemapperChain() {}

    public RemapperChain(List<Remapper<T>> remapper) {
        remappers = remapper;
    }

    @Override
    public T calculate(T value) {
        for (Remapper<T> remapper : remappers) {
            value = remapper.calculate(value);
        }
        return value;
    }
    
}
