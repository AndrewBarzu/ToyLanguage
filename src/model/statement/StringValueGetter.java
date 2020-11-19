package model.statement;

import model.MyException;
import model.PrgState;
import model.expression.Exp;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

public interface StringValueGetter {
    static StringValue run(PrgState state, Exp exp) throws MyException {
        var tbl = state.getSymTable();
        var heap = state.getHeap();
        Value expValue = exp.eval(tbl, heap);
        if (!expValue.getType().equals(new StringType())) {
            throw new MyException("Expression should be of type string!");
        }
        return (StringValue) expValue;
    }
}
