package view;

import commands.ExitCommand;
import commands.RunExample;
import controller.Controller;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
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
import repository.RepoInterface;
import repository.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


interface ProgramFromList{
    static IStmt create(List<IStmt> statements){
        Optional<IStmt> result = statements.stream().reduce(CompStmt::new);
        return result.orElse(null);
    }
}

public class Interpreter {
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
                                new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+")),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), "+")),
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

    static IStmt example5(){
        return ProgramFromList.create(Arrays.asList(new VarDeclStmt("varf", new StringType()),
                new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new openRFile(new VarExp("varf")),
                new VarDeclStmt("varc", new IntType()),
                new readFile(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc")),
                new readFile(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc")),
                new readFile(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc"))
                )
        );
    }

    public static void main(String[] args) {
//        var prog = ProgramFromList.create(Arrays.asList(new VarDeclStmt("v", new IntType()), new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
//        System.out.println(prog);
        IStmt ex1 = example1();
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex1);
        RepoInterface repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1, true);
        IStmt ex2 = example2();
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex2);
        RepoInterface repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2, true);
        IStmt ex3 = example3();
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex3);
        RepoInterface repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3, true);
        IStmt ex4 = example4();
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex4);
        RepoInterface repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4, true);
        IStmt ex5 = example5();
        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex5);
        RepoInterface repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5, true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.show();
    }
}
