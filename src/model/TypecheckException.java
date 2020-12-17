package model;

public class TypecheckException extends MyException{
    public TypecheckException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return "Typecheck Error: " + super.toString();
    }
}
