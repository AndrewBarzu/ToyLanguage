package model.statement;

import model.MyException;
import model.PrgState;
import model.expression.Exp;
import model.value.RefValue;
import model.value.Value;

public class WriteHeap implements IStmt {
    private final String var_name;
    private final Exp expression;

    public WriteHeap(String var_name, Exp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTbl = state.getSymTable();
        if (!symTbl.isDefined(this.var_name))
            throw new MyException("Variable not defined!");
        Value tblVal = symTbl.get(var_name);
        if (!(tblVal instanceof RefValue))
            throw new MyException("Value is not a RefValue!");
        RefValue tblValConv = (RefValue) tblVal;
        int addr = tblValConv.getAddr();
        var heap = state.getHeap();
        if (!heap.isDefined(addr))
            throw new MyException("Address does not exist");

        Value evaluated = expression.eval(symTbl, heap);
        if (!evaluated.getType().equals(tblValConv.getLocationType()))
            throw new MyException("Expression type does not match referenced value!");

        heap.update(addr, evaluated);

        return state;
    }
}
