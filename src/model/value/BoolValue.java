package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public boolean equals(Value other) {
        BoolValue oth = (BoolValue) other;
        return this.val == oth.val;
    }

    @Override
    public String toString() {
        return val + "";
    }
}
