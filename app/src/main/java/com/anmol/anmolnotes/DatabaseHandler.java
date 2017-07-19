package com.anmol.anmolnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by anmol on 2017-07-03.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context,DatabaseValues.DATABASE_NAME,null,DatabaseValues.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseValues.TABLE_NOTES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseValues.TABLE_NOTES_DROP);
        onCreate(db);
    }
    public void addNote(Notes note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues noteValues = new ContentValues();
        noteValues.put(DatabaseValues.NOTES_TITLE,note.getTitle());
        noteValues.put(DatabaseValues.NOTES_DESCRIPTION,note.getDescription());
        db.insert(DatabaseValues.TABLE_NOTES,null,noteValues);
        db.close();
    }
    public void uptadeNote(Notes note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues noteValues = new ContentValues();
        noteValues.put(DatabaseValues.NOTES_TITLE,note.getTitle());
        noteValues.put(DatabaseValues.NOTES_DESCRIPTION,note.getDescription());
        db.update(DatabaseValues.TABLE_NOTES,noteValues,DatabaseValues.NOTES_ID + "+ ?",
                        new String[]
                                {String.valueOf(note.getId())
                                });
        db.close();
    }
    public void deleteNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + DatabaseValues.TABLE_NOTES + " WHERE " + DatabaseValues.NOTES_ID + " = '" + id + "'";
        db.execSQL(deleteQuery);
        db.close();
    }
    public List<Notes> getallNotes(){
        List<Notes>notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DatabaseValues.TABLE_NOTES;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Notes note = new Notes();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                notes.add(note);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;

    }
}
