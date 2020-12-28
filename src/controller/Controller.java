package controller;

import model.MyException;
import model.PrgState;
import model.value.Value;
import repository.RepoInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static model.GarbageCollector.getAddrFromSymTable;
import static model.GarbageCollector.safeGarbageCollector;

public class Controller {
    RepoInterface repo;
    boolean debugFlag;
    ExecutorService executor;

    public Controller(RepoInterface repo, boolean debugFlag) {
        this.repo = repo;
        this.debugFlag = debugFlag;
    }

    public Controller(RepoInterface repo) {
        this(repo, false);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) {

        List<Callable<PrgState>> callables = prgList.stream()
                .map(p -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callables).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            prgList.forEach(prg -> {
                try {
                    repo.logPrgStateExec(prg);
                } catch (MyException e) {
                    e.printStackTrace();
                }
            });
            this.repo.setPrgList(prgList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void allStep() {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = this.removeCompletedPrg(repo.getPrgList());
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(repo.getLogFilePath(), false)))) {
            logFile.print("");
        } catch (IOException e) {
            System.out.println("Error clearing log file " + repo.getLogFilePath());
        }
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        while (prgList.size() > 0) {
            List<Value> joint_sym_tables = (List<Value>) prgList.stream()
                    .map(prg -> prg.getSymTable().getContent().values())
                    .reduce(new ArrayList<>(), (sym1, sym2) -> {
                        var res = new ArrayList<Value>();
                        res.addAll(sym1);
                        res.addAll(sym2);
                        return res;
                    });
            joint_sym_tables = joint_sym_tables.stream().distinct().collect(Collectors.toList());
            prgList.get(0).getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(joint_sym_tables),
                    prgList.get(0).getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = this.removeCompletedPrg(prgList);
        }
        executor.shutdownNow();
        this.repo.setPrgList(prgList);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void setExecutor(){
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void shutdownExecutor(){
        this.executor.shutdownNow();
    }
}
