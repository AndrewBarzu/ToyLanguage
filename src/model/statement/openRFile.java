package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStmt {
    Exp exp;

    public openRFile(Exp exp){
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        Value expValue = this.exp.eval(tbl);
        if (!expValue.getType().equals(new StringType())){
            throw new MyException("Expression should be of type string!");
        }
        StringValue value = (StringValue)expValue;
        if (fileTbl.isDefined(value)){
            throw new MyException("Value is already defined!");
        }
        try {
            BufferedReader descriptor = new BufferedReader(new FileReader(value.getVal()));
            fileTbl.add(value, descriptor);
        }
        catch (IOException e){
            throw new MyException("IO Exception: " + e);
        }
        return state;
    }

    @Override
    public String toString() {
        return "open " + this.exp.toString();
    }
}
