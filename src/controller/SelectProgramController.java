package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.MyException;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.statement.IStmt;
import repository.GUIRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;

class ListElem<T> {
    private final String name;
    private final T elem;

    ListElem(String name, T elem) {
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

public class SelectProgramController {

    @FXML
    public Button selectProgramBtn;

    @FXML
    private ListView<ListElem<IStmt>> programList;

    public void setListItems(List<IStmt> elems) {
        ObservableList<ListElem<IStmt>> forListView = elems.stream()
                .map(e -> new ListElem<>(e.toString(), e))
                .collect(Collector.of(
                        FXCollections::observableArrayList,
                        ObservableList::add,
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));
        programList.setItems(forListView);
    }

    @FXML
    public void onSelectProgramPressed() throws IOException {
        var selectedProgram = programList.getSelectionModel().getSelectedItem();
        if (selectedProgram == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            selectedProgram.getElem().typecheck(new MyDictionary<>());
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.toString(), ButtonType.OK);
            alert.showAndWait();
            return;
        }
        PrgState program = new PrgState(new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                new MyHeap(),
                selectedProgram.getElem());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/xmls/runProgramScene.fxml"));
        Stage stage = (Stage) this.selectProgramBtn.getScene().getWindow();
        Parent root = loader.load();

        Scene scene = new Scene(root, 640, 400);
        stage.setScene(scene);

        RunProgramController controller = loader.getController();
        controller.setMainScene(this.selectProgramBtn.getScene());
        controller.setController(new Controller(new GUIRepository(program)));

        stage.show();
    }
}
