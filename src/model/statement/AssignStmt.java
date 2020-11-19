package model.statement;

import model.MyException;
import model.PrgState;
import model.expression.Exp;
import model.type.Type;
import model.value.Value;

public class AssignStmt implements IStmt {
    final private String id;
    final private Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var tbl = state.getSymTable();
        var heap = state.getHeap();

        if (!tbl.isDefined(id)) {
            throw new MyException("the used variable" + id + " was not declared before");
        }
        Value val = exp.eval(tbl, heap);
        Type type = (tbl.get(id)).getType();
        if (!val.getType().equals(type)) {
            throw new MyException("declared type of variable" + id + " and type of  the assigned expression do not match");
        }
        tbl.update(id, val);
        return state;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }
}
