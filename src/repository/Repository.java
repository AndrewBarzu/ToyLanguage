package repository;

import model.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//
//interface Consumer {
//    static void print(@NotNull PrintWriter logFile, @NotNull String title, String structure, String regex) {
//        logFile.println(title);
//        if (!title.equals("FileTable:"))
//            structure = structure.replaceAll("=", "->");
//        else {
//            structure = structure.replaceAll("=.*([,}])", "$1");
//        }
//        String[] lines = structure.split(regex);
//        ArrayList<String> linesArr = new ArrayList<>(Arrays.asList(lines));
//        linesArr.stream()
//                .filter((line) -> !line.equals("") && !line.equals("\n"))
//                .forEach((line) -> logFile.println("   " + line));
//    }
//}

public class Repository implements RepoInterface {

    private List<PrgState> programs;
    private final String logFilePath;

    public Repository(PrgState prog, String logFilePath) {
        programs = new ArrayList<>();
        programs.add(prog);
        this.logFilePath = logFilePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.programs;
    }

    @Override
    public void setPrgList(List<PrgState> programs) {
        this.programs = programs;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException {
        if (this.logFilePath.equals("")){
            return;
        }
        var exeStack = prgState.getExeStack();
        var out = prgState.getOut();
        var symtbl = prgState.getSymTable();
        var heap = prgState.getHeap();
        var fileTable = prgState.getFileTable();
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.println("ID: " + prgState.getId());
            logFile.println("ExeStack:");
            exeStack.getContent().forEach(stmt -> logFile.println("   " + stmt.toString()));
            logFile.println("SymTable:");
            symtbl.getContent().forEach((key, value) -> logFile.println("   " + key + " <- " + value.toString()));
            logFile.println("Heap:");
            heap.getContent().forEach((key, value) -> logFile.println("   " + key + " <- " + value.toString()));
            logFile.println("Out:");
            out.getContent().forEach(value -> logFile.println("   " + value.toString()));
            logFile.println("FileTable:");
            fileTable.getContent().forEach((key, value) -> logFile.println("   " + key.toString()));
//            Consumer.print(logFile, "ExeStack:", exeStack.toString(), ", |[\\[\\]]");
//            Consumer.print(logFile, "SymTable:", symtbl.toString(), ", |[{}\n]");
//            Consumer.print(logFile, "Heap", heap.toString(), ", |[{}\n]");
//            Consumer.print(logFile, "Out:", out.toString(), ", |[\\[\\]]");
//            Consumer.print(logFile, "FileTable:", fileTable.toString(), ", |[{}]");
            logFile.println("------------");
        } catch (IOException e) {
            throw new MyException("IO error: " + e);
        }
    }
}
