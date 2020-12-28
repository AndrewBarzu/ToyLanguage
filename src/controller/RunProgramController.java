package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;
import repository.GUIRepository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static model.GarbageCollector.getAddrFromSymTable;
import static model.GarbageCollector.safeGarbageCollector;

//class PrgListItem{
//    private final PrgState prgState;
//    public PrgListItem(PrgState prgState){
//        this.prgState = prgState;
//    }
//
//    public PrgState getPrgState() {
//        return prgState;
//    }
//
//    @Override
//    public String toString() {
//        return Integer.toString(this.prgState.getId());
//    }
//}

public class RunProgramController {
    @FXML
    private TableView<Pair<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Pair<String, Value>, String> symbolTableVarNameColumn;
    @FXML
    private TableColumn<Pair<String, Value>, String> symbolTableValueColumn;
    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapAddressColumn;
    @FXML
    private TableColumn<Pair<Integer, Value>, Value> heapValueColumn;
    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private ListView<Value> outListView;
    @FXML
    private Text currentProgramIDText;
    @FXML
    private ListView<IStmt> exeStackListView;
    @FXML
    private Button backBtn;
    @FXML
    private ListView<PrgState> programStatesListView;
    @FXML
    private Text currentNumberOfProgramsText;

    private List<PrgState> programs;

    @FXML
    private Scene mainScene;

    private Controller controller;
    private PrgState selectedProgram;
    private MyIHeap<Integer, Value> heap;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    private void setupExeStack(PrgState prgState){
        if (prgState == null){
            return;
        }
        this.exeStackListView.getItems().clear();
        this.exeStackListView.getItems().addAll(prgState.getExeStack().getContent());

    }

    private void updateOutTable(){
        this.outListView.getItems().clear();
        this.outListView.getItems().addAll(this.out.getContent());
    }

    private void updateFileTable(){
        this.fileTableListView.getItems().clear();
        this.fileTableListView.getItems().addAll(this.fileTable.getContent().keySet());
    }

    private void setupSymTable(PrgState prgState){
        if (prgState == null){
            return;
        }
        this.symbolTableView.setItems(prgState.getSymTable().getContent().entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collector.of(FXCollections::observableArrayList, ObservableList::add, (l1, l2) -> {l1.addAll(l2); return l1;})));
    }

    private void updateHeapTable(){
        this.heapTableView.setItems(this.heap.getContent().entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collector.of(FXCollections::observableArrayList, ObservableList::add, (l1, l2) -> {l1.addAll(l2); return l1;})));
    }

    private void setupProgramProperties(PrgState prgState){
        this.setupExeStack(prgState);
        this.setupSymTable(prgState);
    }
    private void updateProgramProperties(PrgState prgState){
        this.setupProgramProperties(prgState);
        this.updateOutTable();
        this.updateFileTable();
        this.updateHeapTable();
    }

    @FXML
    public void initialize(){
        this.programStatesListView.getSelectionModel().selectedItemProperty()
                .addListener(((observableValue, prgState, t1) -> {
                    if (t1 == null)
                        return;
                    this.setupProgramProperties(t1);
                    this.selectedProgram = t1;
                    this.currentProgramIDText.textProperty().setValue(Integer.toString(this.selectedProgram.getId()));}));
        this.symbolTableVarNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getKey()));
        this.symbolTableValueColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getValue().toString()));

        this.heapAddressColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getKey()));
        this.heapValueColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getValue()));
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setController(Controller controller){
        this.controller = controller;
        this.programStatesListView.itemsProperty().bind(((GUIRepository)this.controller.repo).getListProperty());
        this.currentNumberOfProgramsText.textProperty().bind(Bindings.size(this.programStatesListView.getItems()).asString());
        this.programs = controller.removeCompletedPrg(this.controller.repo.getPrgList());
        var prg = controller.repo.getPrgList().get(0);
        this.heap = prg.getHeap();
        this.out = prg.getOut();
        this.fileTable = prg.getFileTable();
    }

    @FXML
    public void onBackPressed() {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }

    @FXML
    public void onNextPressed() {
        if (this.programs.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No more programs running!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        List<Value> joint_sym_tables = (List<Value>) programs.stream()
                .map(prg -> prg.getSymTable().getContent().values())
                .reduce(new ArrayList<>(), (sym1, sym2) -> {
                    var res = new ArrayList<Value>();
                    res.addAll(sym1);
                    res.addAll(sym2);
                    return res;
                });
        joint_sym_tables = joint_sym_tables.stream().distinct().collect(Collectors.toList());
        programs.get(0).getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(joint_sym_tables),
                programs.get(0).getHeap().getContent()));
        this.controller.setExecutor();
        this.controller.oneStepForAllPrg(programs);
        this.controller.shutdownExecutor();
        programs = this.controller.removeCompletedPrg(programs);
        this.updateProgramProperties(this.selectedProgram);
    }
}
