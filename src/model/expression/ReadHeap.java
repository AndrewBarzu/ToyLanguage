package model.expression;

import model.MyException;
import model.TypecheckException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class ReadHeap implements Exp {

    private final Exp expression;

    public ReadHeap(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value val = expression.eval(tbl, heap);
        if (!(val instanceof RefValue))
            throw new MyException("Expression does not evaluate to a RefValue!");
        RefValue referencedValue = (RefValue) val;
        if (!heap.isDefined(referencedValue.getAddr()))
            throw new MyException("Address does not exist!");
        return heap.get(referencedValue.getAddr());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = expression.typecheck(typeEnv);
        if (!(typ instanceof RefType))
            throw new TypecheckException("the rH argument is not a Ref Type");

        RefType reft = (RefType) typ;
        return reft.getInner();
    }

    @Override
    public String toString() {
        return "rH(" + this.expression.toString() + ")";
    }
}
