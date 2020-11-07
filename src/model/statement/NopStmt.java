package model.statement;

import model.PrgState;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) {
        return state;
    }
}
