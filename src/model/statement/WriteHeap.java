package model.statement;

import model.MyException;
import model.PrgState;
import model.TypecheckException;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
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

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        if (!typeEnv.isDefined(var_name))
            throw new TypecheckException("Variable is not defined");

        if (!typeEnv.get(var_name).equals(new RefType(expression.typecheck(typeEnv))))
            throw new TypecheckException("not ok");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(" + this.var_name + ", " + this.expression.toString() + ")";
    }
}
