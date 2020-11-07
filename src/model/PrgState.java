package model;

import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    public IStmt originalProgram;

    public PrgState(MyIStack<IStmt> stk,
                    MyIDictionary<String, Value> symTbl,
                    MyIList<Value> ot,
                    MyIDictionary<StringValue, BufferedReader> fileTable,
                    IStmt prg){
        this.exeStack = stk;
        this.symTable = symTbl;
        this.fileTable = fileTable;
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

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    @Override
    public String toString() {
        return "exeStack=" + exeStack + "\n" +
                "symTable=" + symTable + "\n" +
                "out=" + out;
    }

    public void reset(){
        this.exeStack.clear();
        this.exeStack.push(this.originalProgram);
        this.symTable.clear();
        this.fileTable.clear();
        this.out.clear();
    }

}
