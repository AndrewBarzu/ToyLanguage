package repository;

import model.MyException;
import model.PrgState;

public interface RepoInterface {
    void logPrgStateExec() throws MyException;
    PrgState getCrtPrg();
}
