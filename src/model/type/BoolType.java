package model.type;

public class BoolType implements Type{
    @Override
    public boolean equals(Type other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
