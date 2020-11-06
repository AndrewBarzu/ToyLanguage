package repository;

import model.PrgState;
import model.adt.*;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Repository implements RepoInterface{

    ArrayList<PrgState> programs;
    int crtPrg;
    String logFilePath;

    public Repository(IStmt prog, String logFilePath){
        programs = new ArrayList<>();
        PrgState mainProgram = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), prog);
        programs.add(mainProgram);
        crtPrg = 0;
        this.logFilePath = logFilePath;
    }


    @Override
    public void logPrgStateExec() {
        PrgState prog = programs.get(crtPrg);
        MyIStack<IStmt> exeStack = prog.getExeStack();
        MyIList<Value> out = prog.getOut();
        MyIDictionary<String, Value> symtbl = prog.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = prog.getFileTable();
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.println("ExeStack:");
            String[] lines = exeStack.toString().split(", |[\\[\\]]");
            ArrayList<String> linesArr = new ArrayList<>(Arrays.asList(lines));
            linesArr.removeAll(Arrays.asList("", null, "\n"));
            for (String line: linesArr){
                logFile.println(line);
            }
            logFile.println("SymTable:");
            lines = symtbl.toString().split(", |[{}\n]");
            linesArr = new ArrayList<>(Arrays.asList(lines));
            linesArr.removeAll(Arrays.asList("", null, "\n"));
            for (String line: linesArr){
                logFile.println(line);
            }
            logFile.println("Out:");
            lines = out.toString().split(", |[\\[\\]]");
            linesArr = new ArrayList<>(Arrays.asList(lines));
            linesArr.removeAll(Arrays.asList("", null, "\n"));
            for (String line: linesArr){
                logFile.println(line);
            }
            logFile.println("FileTable:");
            lines = fileTable.toString().split(", |[{}]");
            linesArr = new ArrayList<>(Arrays.asList(lines));
            linesArr.removeAll(Arrays.asList("", null, "\n"));
            for (String line: linesArr){
                logFile.println(line);
            }
            logFile.println("");
        } catch (IOException e) {
            System.out.println("Error opening or writing to file: " + e);
        }
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(crtPrg);
    }
}
