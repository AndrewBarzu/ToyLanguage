package model.statement;

import model.MyException;
import model.PrgState;
import model.TypecheckException;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt {
    Exp exp;

    public closeRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        StringValue value = StringValueGetter.run(state, this.exp);
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        if (!fileTbl.isDefined(value)) {
            throw new MyException("File is not opened!");
        }
        BufferedReader reader = fileTbl.get(value);
        try {
            reader.close();
        } catch (IOException e) {
            throw new MyException("IO Exception: " + e);
        } finally {
            fileTbl.remove(value);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        if (!exp.typecheck(typeEnv).equals(new StringType()))
            throw new TypecheckException("Expression is not string");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "close " + this.exp.toString();
    }
}
