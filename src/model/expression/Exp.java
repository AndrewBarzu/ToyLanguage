package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException;
}
