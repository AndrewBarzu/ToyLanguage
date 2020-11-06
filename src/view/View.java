package view;

import controller.Controller;
import model.MyException;
import model.expression.ArithExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.Repository;

public class View {

    Controller controller;

    public View(Controller controller){
        this.controller = controller;
    }

    static IStmt example1(){
        return new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
    }

    static IStmt example2(){
        return new CompStmt(
                new VarDeclStmt("a",new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt(
                                        "a",
                                        new ArithExp(
                                                1, new ValueExp(new IntValue(2)),
                                                new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)))
                                        )
                                ),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
    }

    static IStmt example3(){
        return new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
    }

    static IStmt example4(){
        return new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new openRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new readFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new VarExp("varf"))
                                                                )
                                                        )
                                                )

                                        )
                                )
                        )
                )
        );
    }

    public static void main(String[] args){

        Repository repo = new Repository(View.example4(), "log_ex4");
        Controller controller = new Controller(repo, true);
        try {
            controller.allStep();
        }
        catch (MyException e) {
            System.out.println(e.toString());
        }
        System.out.println(controller.getMainProg().originalProgram.toString());
    }
}
