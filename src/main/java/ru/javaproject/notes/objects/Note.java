package ru.javaproject.notes.objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by s0u1 on 17.06.2016.
 */
public class Note implements Serializable{

    private int number;
    private String text;
    private String noteDate;

    public Note (int number, String text, String noteDate){
        this.number = number;
        this.text = text;
        this.noteDate = noteDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String  getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
