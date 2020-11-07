package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.type.BoolType;
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
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1,v2;
        v1= e1.eval(tbl);
        if (!op.equals("and") && !op.equals("or")){
            throw new MyException("Invalid operator");
        }
        if (!v1.getType().equals(new BoolType())) {
            throw new MyException("first operand is not a boolean");
        }
        v2 = e2.eval(tbl);
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
}

