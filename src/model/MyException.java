package model;

public class MyException extends Exception {
    String msg;
    public MyException(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
