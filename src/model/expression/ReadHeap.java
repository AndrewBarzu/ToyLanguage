package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
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
    public String toString() {
        return "rH(" + this.expression.toString() + ")";
    }
}
