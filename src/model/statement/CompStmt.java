package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIStack;

public class CompStmt implements IStmt{
    final private IStmt first;
    final private IStmt second;

    public CompStmt(IStmt i, IStmt ii){
        this.first = i;
        this.second = ii;
    }

    @Override
    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return state;
    }
}
