package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.expression.Exp;
import model.value.Value;

public class PrintStmt implements IStmt {
    final private Exp exp;
    public PrintStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> tbl = state.getSymTable();
        out.add(exp.eval(tbl));
        return state;
    }

    @Override
    public String toString() {
        return "print(" +exp.toString()+")";
    }
}
