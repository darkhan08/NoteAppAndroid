package com.example.noteapp;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Note {
    private String text;
    String current_date;

    public Note(String text,String current_date) {
        this.text = text;
        this.current_date = current_date;
    }

    public String getText() {
        return text;
    }
    public String getCurrent_date(){
        return current_date;
    }

    public void setText(String text) {
        this.text = text;
    }
}
