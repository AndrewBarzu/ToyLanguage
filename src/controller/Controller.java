package controller;

import model.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statement.IStmt;
import repository.RepoInterface;

public class Controller {
    RepoInterface repo;
    boolean debugFlag;

    public Controller(RepoInterface repo, boolean debugFlag){
        this.repo = repo;
        this.debugFlag = true;
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
        System.out.println(this.repo.getCrtPrg());
        while (!prg.getExeStack().empty()){
            oneStep(prg);
            System.out.println("---------");
            System.out.println(this.repo.getCrtPrg());
        }
    }

    public PrgState getMainProg(){
        return this.repo.getCrtPrg();
    }
}
