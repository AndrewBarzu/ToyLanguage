package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) {
        return tbl.get(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
