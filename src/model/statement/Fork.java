package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyStack;
import model.value.Value;

import java.util.Map;
import java.util.stream.Collectors;

public class Fork implements IStmt {

    IStmt statement;

    public Fork(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = new MyStack<IStmt>();
        var heap = state.getHeap();
        var fileTbl = state.getFileTable();
        var out = state.getOut();
        var symTbl = new MyDictionary<String, Value>();
        symTbl.setContent(state.getSymTable().getContent().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().copy())));

        return new PrgState(stack, symTbl, out, fileTbl, heap, statement);
    }

    @Override
    public String toString() {
        return "Fork( " + this.statement.toString() + " )";
    }
}
