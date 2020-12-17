package view;

import controller.SelectPorgramController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.expression.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


interface ProgramFromList {
    static IStmt create(List<IStmt> statements) {
        Optional<IStmt> result = statements.stream().reduce(CompStmt::new);
        return result.orElse(null);
    }
}

public class Interpreter extends Application {
    static IStmt example1() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
    }

    static IStmt example2() {
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
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

    static IStmt example3() {
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

    static IStmt example4() {
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

    static IStmt example5() {
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

    static IStmt example6() {
        return ProgramFromList.create(Arrays.asList(
                new VarDeclStmt("v", new RefType(new IntType())),
                new New("v", new ValueExp(new IntValue(20))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new New("a", new VarExp("v")),
                new PrintStmt(new ReadHeap(new VarExp("v"))),
                new PrintStmt(new ArithExp(new ReadHeap(new ReadHeap(new VarExp("a"))), new ValueExp(new IntValue(5)), "+"))
        ));
    }

    static IStmt example7() {
        return ProgramFromList.create(Arrays.asList(
                new VarDeclStmt("v", new RefType(new IntType())),
                new New("v", new ValueExp(new IntValue(20))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new New("a", new VarExp("v")),
                new New("v", new ValueExp(new IntValue(30))),
                new PrintStmt(new ReadHeap(new ReadHeap(new VarExp("a"))))
        ));
    }

    static IStmt example8() {
        return ProgramFromList.create(Arrays.asList(
                new VarDeclStmt("a", new IntType()),
                new AssignStmt("a", new ValueExp(new IntValue(2))),
                new WhileStmt(new RelExp(new VarExp("a"), new ValueExp(new IntValue(0)), ">"),
                        new CompStmt(
                                new PrintStmt(new VarExp("a")),
                                new AssignStmt("a", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), "-"))
                        )),
                new PrintStmt(new VarExp("a"))
        ));
    }

    static IStmt example9() {
        return ProgramFromList.create(Arrays.asList(
                new VarDeclStmt("v", new IntType()),
                new VarDeclStmt("a", new RefType(new IntType())),
                new AssignStmt("v", new ValueExp(new IntValue(10))),
                new New("a", new ValueExp(new IntValue(22))),
                new Fork(ProgramFromList.create(Arrays.asList(
                        new WriteHeap("a", new ValueExp(new IntValue(30))),
                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                        new Fork(ProgramFromList.create(Arrays.asList(
                                new AssignStmt("v", new ValueExp(new IntValue(50))),
                                new PrintStmt(new VarExp("v"))
                        ))),
                        new NopStmt(),
                        new NopStmt(),
                        new NopStmt(),
                        new PrintStmt(new VarExp("v")),
                        new PrintStmt(new ReadHeap(new VarExp("a")))
                ))),
                new NopStmt(),
                new NopStmt(),
                new NopStmt(),
                new NopStmt(),
                new NopStmt(),
                new NopStmt(),
                new NopStmt(),
                new NopStmt(),
                new PrintStmt(new VarExp("v")),
                new PrintStmt(new ReadHeap(new VarExp("a")))
                )
        );
    }

    static IStmt crapa_la_typecheck() {
        return ProgramFromList.create(List.of(
                new VarDeclStmt("a", new IntType()),
                new openRFile(new VarExp("a")),
                new closeRFile(new VarExp("a"))
        ));
    }

    public static void main(String[] args) {
        IStmt ex1 = example1();
        IStmt ex2 = example2();
        IStmt ex3 = example3();
        IStmt ex4 = example4();
        IStmt ex5 = example5();
        IStmt ex6 = example6();
        IStmt ex7 = example7();
        IStmt ex8 = example8();
        IStmt ex9 = example9();
        IStmt ex10 = crapa_la_typecheck();
        launch(args);

//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), ex1));
//        menu.addCommand(new RunExample("2", ex2.toString(), ex2));
//        menu.addCommand(new RunExample("3", ex3.toString(), ex3));
//        menu.addCommand(new RunExample("4", ex4.toString(), ex4));
//        menu.addCommand(new RunExample("5", ex5.toString(), ex5));
//        menu.addCommand(new RunExample("6", ex6.toString(), ex6));
//        menu.addCommand(new RunExample("7", ex7.toString(), ex7));
//        menu.addCommand(new RunExample("8", ex8.toString(), ex8));
//        menu.addCommand(new RunExample("9", ex9.toString(), ex9));
//        menu.addCommand(new RunExample("10", ex10.toString() + "-- typecheck error", ex10));
//        menu.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("xmls/selectProgramScene.fxml"));
        VBox root = fxmlLoader.load();
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        SelectPorgramController controller = fxmlLoader.getController();
        controller.setListItems(List.of(
                example1(),
                example2(),
                example3(),
                example4(),
                example5(),
                example6(),
                example7(),
                example8(),
                example9(),
                crapa_la_typecheck()));

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
