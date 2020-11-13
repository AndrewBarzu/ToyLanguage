package repository;

import model.PrgState;
import model.adt.*;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

interface Consumer{
    static void print(@NotNull PrintWriter logFile, @NotNull String title, String structure, String regex){
        logFile.println(title);
        if (!title.equals("FileTable:"))
            structure = structure.replaceAll("=", "->");
        else {
            structure = structure.replaceAll("=.*([,}])", "$1");
        }
        String[] lines = structure.split(regex);
        ArrayList<String> linesArr = new ArrayList<>(Arrays.asList(lines));
        linesArr.stream()
                .filter((line) -> !line.equals("") && !line.equals("\n"))
                .forEach((line) -> logFile.println("   " + line));
    }
}

public class Repository implements RepoInterface{

    private final ArrayList<PrgState> programs;
    private final int crtPrg;
    private final String logFilePath;

    public Repository(PrgState prog, String logFilePath){
        programs = new ArrayList<>();
        programs.add(prog);
        crtPrg = 0;
        this.logFilePath = logFilePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    @Override
    public void logPrgStateExec() {
        PrgState prog = programs.get(crtPrg);
        MyIStack<IStmt> exeStack = prog.getExeStack();
        MyIList<Value> out = prog.getOut();
        MyIDictionary<String, Value> symtbl = prog.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = prog.getFileTable();
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            Consumer.print(logFile, "ExeStack:", exeStack.toString(), ", |[\\[\\]]");
            Consumer.print(logFile, "SymTable:", symtbl.toString(), ", |[{}\n]");
            Consumer.print(logFile, "Out:", out.toString(), ", |[\\[\\]]");
            Consumer.print(logFile, "FileTable:", fileTable.toString(), ", |[{}]");
            logFile.println("------------");
        } catch (IOException e) {
            System.out.println("Error opening or writing to file: " + e);
        }
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(crtPrg);
    }
}
