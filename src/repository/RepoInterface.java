package repository;

import model.MyException;
import model.PrgState;

import java.util.List;

public interface RepoInterface {
    void logPrgStateExec(PrgState prgState) throws MyException;

    String getLogFilePath();

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> programs);
}
