package model.statement;

import model.MyException;
import model.PrgState;
import model.TypecheckException;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

public class readFile implements IStmt {
    Exp exp;
    String var_name;

    public readFile(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var tbl = state.getSymTable();
        var fileTbl = state.getFileTable();
        var heap = state.getHeap();
        if (!tbl.isDefined(this.var_name)) {
            throw new MyException("Variable not defined!");
        }
        Value val = tbl.get(var_name);
        if (!val.getType().equals(new IntType())) {
            throw new MyException("Variable should be an int!");
        }
        Value expValue = this.exp.eval(tbl, heap);
        if (!expValue.getType().equals(new StringType())) {
            throw new MyException("Expression should be of type string!");
        }
        StringValue stringValue = (StringValue) expValue;
        if (!fileTbl.isDefined(stringValue)) {
            throw new MyException("File <" + stringValue.getVal() + "> is not open!");
        }
        BufferedReader reader = fileTbl.get(stringValue);
        try {
            String line = reader.readLine();
            if (line == null) {
                throw new MyException("Not enough data in file <" + stringValue.getVal() + ">!");
            }
            if (line.equals("")) {
                tbl.update(this.var_name, new IntValue(0));
            } else {
                int newVal = Integer.parseInt(line);
                tbl.update(this.var_name, new IntValue(newVal));
            }
        } catch (EOFException e) {
            throw new MyException("Not enough data in file " + stringValue.getVal() + "!");
        } catch (IOException e) {
            throw new MyException("IO Exception: " + e);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (!typeEnv.isDefined(var_name))
            throw new TypecheckException("Variable " + var_name + " not defined");

        if (!typeEnv.get(var_name).equals(new IntType()))
            throw new TypecheckException("Variable " + var_name + "is not of type int");

        if (exp.typecheck(typeEnv).equals(new IntType()))
            throw new TypecheckException("Expression is not int");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "read from " + this.exp.toString() + " into " + this.var_name;
    }
}
