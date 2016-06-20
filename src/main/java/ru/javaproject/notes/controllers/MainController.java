package ru.javaproject.notes.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.javaproject.notes.interfaces.impls.CollectionTableText;
import ru.javaproject.notes.objects.Note;
import ru.javaproject.notes.objects.ThreadDB;


import java.io.IOException;
import java.util.Date;

public class MainController {

    private CollectionTableText ColTT1 = new CollectionTableText();

    @FXML
    public TableView tableMain;

    @FXML
    private TableColumn<Note, Integer> columnNumber;

    @FXML
    private TableColumn<Note, String> columnText;

    @FXML
    private TableColumn<Note, Date> columnDate;

    @FXML
    private Button btnAdd;

    private Parent fxmlSec;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private SecondaryController secCon;

    private Stage secStage;

    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize(){

        Thread thread = new ThreadDB(ColTT1);
        thread.start();

        columnNumber.setCellValueFactory(new PropertyValueFactory<Note, Integer>("number"));
        columnText.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, Date>("noteDate"));

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tableMain.setItems(ColTT1.getNoteObservableList());

        initLoader();
    }

    @FXML
    public void showDialog(ActionEvent actionEvent) throws Exception {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAdd":

                openWindow();

                try {
                    if (ColTT1.size() != secCon.getCount()) {
                        Thread thread = new ThreadDB(ColTT1, secCon.getThisNote(), secCon.getCount());
                        thread.start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    public void initLoader(){
        try
        {
            fxmlLoader.setLocation(getClass().getResource("/fxml/Secondary.fxml"));
            fxmlSec = fxmlLoader.load();
            secCon = fxmlLoader.getController();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openWindow(){
        if (secStage == null) {
            try {
                secStage = new Stage();
                secStage.setTitle("Добавить запись");
                secStage.setMinHeight(300);
                secStage.setMinWidth(400);
                secStage.setResizable(false);
                secStage.setScene(new Scene(fxmlSec));
                secStage.initModality(Modality.WINDOW_MODAL);
                secStage.initOwner(mainStage);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        secCon.setCount(ColTT1.size());
        secCon.setNoteDate(new Date());
        secCon.clearTextArea();

        secStage.showAndWait();
    }
}