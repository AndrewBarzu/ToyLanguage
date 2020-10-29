package repository;

import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.statement.IStmt;

import java.util.ArrayList;

public class Repository implements RepoInterface{

    ArrayList<PrgState> programs;
    int crtPrg;

    public Repository(IStmt prog){
        programs = new ArrayList<>();
        PrgState mainProgram = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), prog);
        programs.add(mainProgram);
        crtPrg = 0;
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(crtPrg);
    }
}
