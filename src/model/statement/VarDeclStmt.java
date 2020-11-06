package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class VarDeclStmt implements IStmt{
    final private String name;
    final private Type type;

    public VarDeclStmt(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Value value = type.defaultValue();
        if (tbl.isDefined(name)) {
            throw new MyException("Variable named "+ name + " already exists!");
        }
        tbl.add(name, value);
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
