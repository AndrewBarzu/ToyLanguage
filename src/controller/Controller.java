package controller;

import model.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statement.IStmt;
import repository.RepoInterface;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class Controller {
    RepoInterface repo;
    boolean debugFlag;

    public Controller(RepoInterface repo, boolean debugFlag){
        this.repo = repo;
        this.debugFlag = debugFlag;
    }

    public Controller(RepoInterface repo){
        this(repo, false);
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        if (stk.empty())
            throw new MyException("Program stack is empty");
        IStmt stmt = stk.pop();
        return stmt.execute(state);
    }

    public void allStep() throws MyException{
        PrgState prg = repo.getCrtPrg();
        // This is for printing to console
        //System.out.println(this.repo.getCrtPrg());
        try {
            Files.deleteIfExists(FileSystems.getDefault().getPath(repo.getLogFilePath()));
        }
        catch (IOException e){
            throw new MyException(String.format("Given path %s is a directory!", repo.getLogFilePath()));
        }
        if (this.debugFlag)
            repo.logPrgStateExec();
        while (!prg.getExeStack().empty()){
            oneStep(prg);
            // These 2 are as well for printing to console
            //System.out.println("---------");
            //System.out.println(this.repo.getCrtPrg());
            if (this.debugFlag)
                repo.logPrgStateExec();
        }
        prg.reset();
    }

    public PrgState getMainProg(){
        return this.repo.getCrtPrg();
    }
}
