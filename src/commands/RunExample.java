package commands;

import controller.Controller;
import model.MyException;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.statement.IStmt;
import repository.Repository;

import java.security.cert.TrustAnchor;

public class RunExample extends Command {
    private final IStmt program;

    public RunExample(String key, String desc, IStmt program) {
        super(key, desc);
        this.program = program;
    }

    @Override
    public void execute() {
        try{
            this.program.typecheck(new MyDictionary<>());
        }
        catch (MyException e){
            System.out.println(e.toString());
            return;
        }
        PrgState prg = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), this.program);
        Repository repo = new Repository(prg, "log" + this.getKey() + ".txt");
        Controller ctr = new Controller(repo, true);
        ctr.allStep();
    }
}
