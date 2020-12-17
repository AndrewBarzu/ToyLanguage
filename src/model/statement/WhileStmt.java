package model.statement;

import model.MyException;
import model.PrgState;
import model.TypecheckException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStmt implements IStmt {
    Exp expression;
    IStmt stmt;

    public WhileStmt(Exp expression, IStmt stmt) {
        this.expression = expression;
        this.stmt = stmt;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTbl = state.getSymTable();
        var heap = state.getHeap();
        Value expVal = expression.eval(symTbl, heap);
        if (!expVal.getType().equals(new BoolType())) {
            throw new MyException("While condition cannot be evaluated to a boolean!");
        }
        if (expVal.equals(new BoolValue(false))) {
            return null;
        }
        var exeStack = state.getExeStack();
        exeStack.push(this);
        exeStack.push(this.stmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        var res = expression.typecheck(typeEnv);

        if (!res.equals(new BoolType()))
            throw new TypecheckException("Type is not bool");

        var clone = new MyDictionary<String, Type>();
        clone.setContent(typeEnv.getContent());
        stmt.typecheck(clone);

        return typeEnv;
    }

    @Override
    public String toString() {
        return "while " + this.expression.toString() + " do: " + this.stmt.toString();
    }
}
