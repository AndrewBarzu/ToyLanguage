package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.value.Value;

public class ValueExp implements Exp {
    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
