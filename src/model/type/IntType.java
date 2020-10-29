package model.type;

public class IntType implements Type{
    @Override
    public boolean equals(Type other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
