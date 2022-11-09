package com.example.tarea7_notas;

import java.io.Serializable;

public class Notes implements Serializable {
    public int id;
    public String noteTitle;
    public String noteDescription;

    public Notes(int id, String noteTitle, String noteDescription) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDesc() {
        return noteDescription;
    }

    public void setNoteDesc(String noteDesc) {
        this.noteDescription = noteDesc;
    }
}
