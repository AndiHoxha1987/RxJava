package com.example.rxjava.utils;

import com.example.rxjava.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Note> createDataList(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("Work","Get info from...","Info"));
        notes.add(new Note("Workout","Three times a week","Fitness"));
        notes.add(new Note("Sea", "Go to the beach","Relax"));
        notes.add(new Note("Work", "Finish this project first...","Work"));
        notes.add(new Note("Info","Project is completed!","Info"));
        return notes;
    }
}
