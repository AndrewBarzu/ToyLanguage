package model.expression;

import model.MyException;
import model.adt.MyIDictionary;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    int op;

    public ArithExp(int op, Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException{
        Value v1,v2;
        v1= e1.eval(tbl);
        if (op > 4 || op < 1){
            throw new MyException("Invalid operator");
        }
        if (!v1.getType().equals(new IntType())) {
            throw new MyException("first operand is not an integer");
        }
        v2 = e2.eval(tbl);
        if (!v2.getType().equals(new IntType())) {
            throw new MyException("second operand is not an integer");
        }
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1, n2;
        n1 = i1.getVal();
        n2 = i2.getVal();
        switch (op) {
            case 1:
                return new IntValue(n1 + n2);
            case 2:
                return new IntValue(n1 - n2);
            case 3:
                return new IntValue(n1 * n2);
            case 4: {
                if (n2 == 0) throw new MyException("division by zero");
                else return new IntValue(n1 / n2);
            }
            default:
                throw new MyException("bad operator");
        }
    }

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> this.e1 + "+" + this.e2;
            case 2 -> this.e1 + "-" + this.e2;
            case 3 -> this.e1 + "*" + this.e2;
            case 4 -> this.e1 + "/" + this.e2;
            default -> "Bad operator";
        };
    }
}
