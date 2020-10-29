package model;

import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statement.IStmt;
import model.value.Value;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    public IStmt originalProgram;

    public PrgState(MyIStack<IStmt> stk,
                    MyIDictionary<String, Value> symtbl,
                    MyIList<Value> ot,
                    IStmt prg){
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        originalProgram = prg;
        if (originalProgram != null)
            stk.push(prg);

    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    @Override
    public String toString() {
        return "exeStack=" + exeStack + "\n" +
                "symTable=" + symTable + "\n" +
                "out=" + out;
    }
}
