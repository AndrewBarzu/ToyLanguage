package model.statement;

import model.MyException;
import model.PrgState;
import model.expression.Exp;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

public class New implements IStmt {
    private final String var_name;
    private final Exp expression;

    public New(String var_name, Exp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTbl = state.getSymTable();
        if (!symTbl.isDefined(var_name)) {
            throw new MyException("Variable not defined!");
        }
        Value val = symTbl.get(var_name);

        if (!(val.getType() instanceof RefType)) {
            throw new MyException("Type is not RefType!");
        }
        var heap = state.getHeap();
        Value referenceVal = expression.eval(symTbl, heap);

        if (!((RefValue) val).getLocationType().equals(referenceVal.getType())) {
            throw new MyException("Referenced type is not the same as the given value type!");
        }

        int address = heap.add(referenceVal);

        RefValue newVal = new RefValue(address, referenceVal.getType());

        symTbl.update(var_name, newVal);

        return state;
    }

    @Override
    public String toString() {
        return "new(" + this.var_name + " " + this.expression.toString() + ")";
    }
}
