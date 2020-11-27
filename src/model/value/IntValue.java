package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getVal() {
        return value;
    }

    @Override
    public boolean equals(Value other) {
        if (!(other instanceof IntValue))
            return false;
        IntValue oth = (IntValue) other;
        return this.value == oth.value;
    }

    @Override
    public Value copy() {
        return new IntValue(value);
    }

    @Override
    public String toString() {
        return value + "";
    }
}
