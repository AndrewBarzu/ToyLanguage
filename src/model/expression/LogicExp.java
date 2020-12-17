package model.expression;

import model.MyException;
import model.TypecheckException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    String op;

    public LogicExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (!op.equals("and") && !op.equals("or")) {
            throw new MyException("Invalid operator");
        }
        if (!v1.getType().equals(new BoolType())) {
            throw new MyException("first operand is not a boolean");
        }
        v2 = e2.eval(tbl, heap);
        if (!v2.getType().equals(new BoolType())) {
            throw new MyException("second operand is not a boolean");
        }
        BoolValue i1 = (BoolValue) v1;
        BoolValue i2 = (BoolValue) v2;
        boolean n1, n2;
        n1 = i1.getVal();
        n2 = i2.getVal();
        return switch (op) {
            case "and" -> new BoolValue(n1 && n2);
            case "or" -> new BoolValue(n1 || n2);
            default -> throw new MyException("bad operator");
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);

        if (!typ1.equals(new BoolType()))
            throw new TypecheckException("first operand is not a bool");

        if (!typ2.equals(new BoolType()))
            throw new TypecheckException("second operand is not a bool");

        return new BoolType();
    }


}

