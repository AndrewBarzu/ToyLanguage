package model.expression;

import model.MyException;
import model.TypecheckException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

import java.util.Arrays;
import java.util.List;

public class RelExp implements Exp {
    Exp e1;
    Exp e2;
    String op;
    private static final List<String> operators = Arrays.asList("<", "<=", "==", "!=", ">", ">=");

    public RelExp(Exp e1, Exp e2, String op) {
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
        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new MyException("bad operator");
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);

        if (!typ1.equals(new IntType()))
            throw new TypecheckException("first operand is not an integer");

        if (!typ2.equals(new IntType()))
            throw new TypecheckException("second operand is not an integer");

        return new BoolType();
    }

    @Override
    public String toString() {
        return this.e1 + this.op + this.e2;
    }
}
