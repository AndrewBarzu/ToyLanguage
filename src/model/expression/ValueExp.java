package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.value.Value;

public class ValueExp implements Exp {
    Value e;

    public ValueExp(Value e){
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
