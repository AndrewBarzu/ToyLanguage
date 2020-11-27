package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(Value other) {
        if (!(other instanceof RefValue))
            return false;
        RefValue oth = (RefValue) other;
        return this.address == oth.address;
    }

    @Override
    public Value copy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType.toString() + ")";
    }
}
