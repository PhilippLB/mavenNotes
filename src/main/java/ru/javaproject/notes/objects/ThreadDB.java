package ru.javaproject.notes.objects;

import ru.javaproject.notes.interfaces.impls.CollectionTableText;

/**
 * Created by s0u1 on 18.06.2016.
 */
public class ThreadDB extends Thread {

    private CollectionTableText collectionTableText;
    private Note newNote;
    private int count;

    public ThreadDB(CollectionTableText collectionTableText){
        this.collectionTableText = collectionTableText;
    }

    public ThreadDB(CollectionTableText collectionTableText, Note newNote, int count){
        this.collectionTableText = collectionTableText;
        this.newNote = newNote;
        this.count = count;
    }

    @Override
    public void run() {

        if ((newNote != null) && (count != 0)) {
            try {
                SerializeNoteObject.writeJavaObject(newNote, count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        collectionTableText.fillDBData();
    }
}
