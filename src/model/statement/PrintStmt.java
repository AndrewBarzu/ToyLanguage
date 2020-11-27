package model.statement;

import model.MyException;
import model.PrgState;
import model.expression.Exp;

public class PrintStmt implements IStmt {
    final private Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var out = state.getOut();
        var tbl = state.getSymTable();
        var heap = state.getHeap();
        out.add(exp.eval(tbl, heap));
        return null;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
