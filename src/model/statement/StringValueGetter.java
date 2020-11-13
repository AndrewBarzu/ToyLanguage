package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public interface StringValueGetter {
    static StringValue run(PrgState state, Exp exp) throws MyException{
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Value expValue = exp.eval(tbl);
        if (!expValue.getType().equals(new StringType())){
            throw new MyException("Expression should be of type string!");
        }
        return  (StringValue)expValue;
    }
}
