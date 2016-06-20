package ru.javaproject.notes.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.javaproject.notes.interfaces.TableText;
import ru.javaproject.notes.objects.Note;
import ru.javaproject.notes.objects.SerializeNoteObject;


import java.util.ArrayList;


/**
 * Created by s0u1 on 17.06.2016.
 */
public class CollectionTableText implements TableText {

    private ObservableList<Note> noteObservableList = FXCollections.observableArrayList();

    public void fillDBData(){
        try {
            ArrayList<Note> list = SerializeNoteObject.readJavaObject();
            noteObservableList.clear();
            noteObservableList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Note note) {noteObservableList.add(note);}

    public ObservableList<Note> getNoteObservableList() {
        return noteObservableList;
    }

    public int size(){
        return noteObservableList.size();
    }

    public void clear() {
        this.noteObservableList.clear();
    }
}
