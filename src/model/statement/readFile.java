package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expression.Exp;
import model.type.IntType;
import model.type.StringType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt {
    Exp exp;
    String var_name;

    public readFile(Exp exp, String var_name){
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        if (!tbl.isDefined(this.var_name)){
            throw new MyException("Variable not defined!");
        }
        Value val = tbl.get(var_name);
        if (!val.getType().equals(new IntType())){
            throw new MyException("Variable should be an int!");
        }
        Value expValue = this.exp.eval(tbl);
        if (!expValue.getType().equals(new StringType())){
            throw new MyException("Expression should be of type string!");
        }
        StringValue stringValue = (StringValue)expValue;
        if (!fileTbl.isDefined(stringValue)){
            throw new MyException("File is not opened!");
        }
        BufferedReader reader = fileTbl.get(stringValue);
        try {
            String line = reader.readLine();
            if (line.equals("")){
                tbl.update(this.var_name, new IntValue(0));
            }
            else{
                int newVal = Integer.parseInt(line);
                tbl.update(this.var_name, new IntValue(newVal));
            }
        }
        catch (IOException e){
            throw new MyException("IO Exception: " + e);
        }
        return state;
    }

    @Override
    public String toString() {
        return "read from " + this.exp.toString() + " into " + this.var_name;
    }
}