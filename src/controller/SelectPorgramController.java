package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.statement.IStmt;

import java.util.List;
import java.util.stream.Collector;

class ListElem<T> {
    private final String name;
    private final T elem;
    ListElem(String name, T elem){
        this.name = name;
        this.elem = elem;
    }

    public T getElem() {
        return elem;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class SelectPorgramController {
    @FXML
    public VBox selectProgramScene;

    @FXML
    private ListView<ListElem<IStmt>> programList;

    public void setListItems(List<IStmt> elems){
        ObservableList<ListElem<IStmt>> forListView = elems.stream()
                .map(e -> new ListElem<>(e.toString(), e))
                .collect(Collector.of(
                        FXCollections::observableArrayList,
                        ObservableList::add,
                        (l1, l2) -> { l1.addAll(l2); return l1; }));
        programList.setItems(forListView);
    }

    @FXML
    public void onButtonPressed(ActionEvent event){
        System.out.println(programList.getSelectionModel().getSelectedItem());
    }
}
