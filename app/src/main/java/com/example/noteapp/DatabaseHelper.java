package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "note.db";
    public static final String DATABASE_TABLE = "note";
    public static final String ID = "_id";
    public static final String TEXT = "_text";
    public static final String DATE = "_date";
    ArrayList<Note> notes;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DATABASE_TABLE +
                "("+ID + " integer primary key autoincrement, "+
                TEXT +" text not null, " + DATE +" text not null);");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_TABLE);
        onCreate(db);
    }

    public void insertNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEXT,note.getText());
        cv.put(DATE,note.getCurrent_date());
        db.insert(DATABASE_TABLE,null,cv);
    }

    public ArrayList<Note> getNote() {
        notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            notes.add(new Note(cursor.getString(cursor.getColumnIndex(TEXT)),cursor.getString(cursor.getColumnIndex(DATE))));
        }
        return notes;
    }

    public void remove(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + TEXT + " =\"" + name + "\";");
    }

    public void Update(String text, String newtext, String date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + DATABASE_TABLE + " SET " + TEXT +" =\"" + newtext + "\", " + DATE + "=\""+ date +"\" WHERE " + TEXT + " =\"" + text + "\";");


    }
}
