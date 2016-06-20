package ru.javaproject.notes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Node;
import ru.javaproject.notes.objects.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by s0u1 on 16.06.2016.
 */
public class SecondaryController {

    private Note thisNote;

    private int count = 0;

    private String noteDate;

    @FXML
    private Label lblDate;

    @FXML
    private TextArea txtAreaField;

    @FXML
    private Button btnOk;

    @FXML
    public void saveAndExit(ActionEvent actionEvent) {

        if (!(getTxtAreaField().getText().isEmpty())){
            setThisNote(new Note(count + 1, getTxtAreaField().getText(), noteDate));
            count++;
        }

        Node thisSource = (Node)actionEvent.getSource();
        Stage stage = (Stage)thisSource.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void closeAndExit(ActionEvent actionEvent) {
        Node thisSource = (Node)actionEvent.getSource();
        Stage stage = (Stage)thisSource.getScene().getWindow();
        stage.hide();
    }


    public Note getThisNote() {
        return thisNote;
    }

    public void setThisNote(Note thisNote) {
        this.thisNote = thisNote;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void clearTextArea(){
        getTxtAreaField().clear();
    }


    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMMM h:mm");
        this.noteDate = simpleDateFormat.format(noteDate);
        this.lblDate.setText("Дата: " + simpleDateFormat.format(noteDate));
    }

    public TextArea getTxtAreaField() {
        return txtAreaField;
    }

    public void setTxtAreaField(TextArea txtAreaField) {
        this.txtAreaField = txtAreaField;
    }
}
