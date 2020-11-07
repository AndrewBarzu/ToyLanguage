package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expression.Exp;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class IfStmt implements IStmt {
    final private Exp exp;
    final private IStmt thenS;
    final private IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS){
        this.exp = exp;
        this.elseS = elseS;
        this.thenS = thenS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> tbl = state.getSymTable();

        Value val = exp.eval(tbl);
        BoolValue bool = new BoolValue(true);
        if (val.getType().equals(new BoolType())) {
            if(val.equals(bool)){
                stk.push(thenS);
            }
            else stk.push(elseS);
        }
        else throw new MyException("Expression type is not a boolean");
        return state;
    }

    @Override
    public String toString() {
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+"))";
    }
}
