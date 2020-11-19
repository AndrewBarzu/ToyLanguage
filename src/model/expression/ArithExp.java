package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

import java.util.Arrays;
import java.util.List;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    String op;
    private static final List<String> operators = Arrays.asList("+", "-", "*", "/");

    public ArithExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        if (!operators.contains(this.op)) {
            throw new MyException("Invalid operator");
        }
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (!v1.getType().equals(new IntType())) {
            throw new MyException("first operand is not an integer");
        }
        v2 = e2.eval(tbl, heap);
        if (!v2.getType().equals(new IntType())) {
            throw new MyException("second operand is not an integer");
        }
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1, n2;
        n1 = i1.getVal();
        n2 = i2.getVal();
        switch (op) {
            case "+":
                return new IntValue(n1 + n2);
            case "-":
                return new IntValue(n1 - n2);
            case "*":
                return new IntValue(n1 * n2);
            case "/": {
                if (n2 == 0) throw new MyException("division by zero");
                else return new IntValue(n1 / n2);
            }
            default:
                throw new MyException("bad operator");
        }
    }

    @Override
    public String toString() {
        return this.e1 + this.op + this.e2;
    }
}
