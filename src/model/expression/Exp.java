package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl) throws MyException;
}
