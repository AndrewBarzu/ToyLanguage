package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type {
    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "bool";
    }
}
