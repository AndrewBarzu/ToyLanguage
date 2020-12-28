package repository;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class GUIRepository implements RepoInterface{

    private final ReadOnlyListWrapper<PrgState> programs;

    public GUIRepository(PrgState prgState){
        programs = new ReadOnlyListWrapper<>(FXCollections.observableList(new ArrayList<>()));
        programs.add(prgState);
    }

    @Override
    public void logPrgStateExec(PrgState prgState){
    }

    @Override
    public String getLogFilePath() {
        return "";
    }

    @Override
    public List<PrgState> getPrgList() {
        return new ArrayList<>(this.programs);
    }

    @Override
    public void setPrgList(List<PrgState> programs) {
        this.programs.clear();
        this.programs.addAll(programs);
    }

    public ReadOnlyListProperty<PrgState> getListProperty(){
        return this.programs.getReadOnlyProperty();
    }
}
