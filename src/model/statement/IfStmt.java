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

public class IfStmt implements IStmt {
    final private Exp exp;
    final private IStmt thenS;
    final private IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.elseS = elseS;
        this.thenS = thenS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stk = state.getExeStack();
        var tbl = state.getSymTable();
        var heap = state.getHeap();

        Value val = exp.eval(tbl, heap);
        BoolValue bool = new BoolValue(true);
        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Expression type is not a boolean");
        }
        if (val.equals(bool)) {
            stk.push(thenS);
        } else stk.push(elseS);
        return null;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (!typexp.equals(new BoolType()))
            throw new TypecheckException("The condition of IF has not the type bool");
        var clone1 = new MyDictionary<String, Type>();
        var clone2 = new MyDictionary<String, Type>();
        clone1.setContent(typeEnv.getContent());
        clone2.setContent(typeEnv.getContent());
        thenS.typecheck(clone1);
        elseS.typecheck(clone2);
        return typeEnv;
    }
}
