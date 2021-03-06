package model;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Integer, Value> heap;
    public IStmt originalProgram;
    static private final AtomicInteger nextId = new AtomicInteger();
    private final int myid;

    public PrgState(MyIStack<IStmt> stk,
                    MyIDictionary<String, Value> symTbl,
                    MyIList<Value> ot,
                    MyIDictionary<StringValue, BufferedReader> fileTable,
                    MyIHeap<Integer, Value> heap,
                    IStmt prg) {
        this.exeStack = stk;
        this.symTable = symTbl;
        this.fileTable = fileTable;
        this.out = ot;
        this.heap = heap;
        this.myid = nextId.getAndIncrement();
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

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return Integer.toString(this.myid);
    }

    public void reset() {
        this.exeStack.clear();
        this.exeStack.push(this.originalProgram);
        this.symTable.clear();
        this.fileTable.clear();
        this.out.clear();
    }

    public int getId() {
        return this.myid;
    }

    public Boolean isNotCompleted() {
        return !this.exeStack.empty();
    }

    public PrgState oneStep() throws MyException {
        if (this.exeStack.empty())
            throw new MyException("PrgState stack is empty");
        IStmt crtStmt = this.exeStack.pop();
        return crtStmt.execute(this);
    }

}
