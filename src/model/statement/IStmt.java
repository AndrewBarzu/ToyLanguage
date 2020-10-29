package model.statement;

import model.MyException;
import model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
}
