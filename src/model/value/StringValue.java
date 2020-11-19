package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return value;
    }

    @Override
    public boolean equals(Value other) {
        StringValue oth = (StringValue) other;
        return this.value.equals(oth.value);
    }

    @Override
    public String toString() {
        return value.equals("") ? "null" : value;
    }
}
