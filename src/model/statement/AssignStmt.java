package model.statement;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expression.Exp;
import model.type.Type;
import model.value.Value;

public class AssignStmt implements IStmt {
    final private String id;
    final private Exp exp;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> tbl = state.getSymTable();

        if (tbl.isDefined(id)){
            Value val = exp.eval(tbl);
            Type type = (tbl.get(id)).getType();
            if (val.getType().equals(type)){
                tbl.update(id, val);
            }
            else {
                throw new MyException("declared type of variable" + id + " and type of  the assigned expression do not match");
            }
        }
        else throw new MyException("the used variable" +id + " was not declared before");
        return state;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }
}
