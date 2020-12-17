package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NOP";
    }
}
