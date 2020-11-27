package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStmt {
    Exp exp;

    public openRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        StringValue value = StringValueGetter.run(state, this.exp);
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        if (fileTbl.isDefined(value)) {
            throw new MyException("Value is already defined!");
        }
        try {
            BufferedReader descriptor = new BufferedReader(new FileReader(value.getVal()));
            fileTbl.add(value, descriptor);
        } catch (IOException e) {
            throw new MyException("IO Exception: " + e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "open " + this.exp.toString();
    }
}
