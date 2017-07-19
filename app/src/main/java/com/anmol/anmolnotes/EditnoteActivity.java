package com.anmol.anmolnotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by anmol on 2017-07-04.
 */

public class EditnoteActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edittitle,editdescription;
    Button save,delete;
    String title,description;
    int noteId;
    Boolean isUpdateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);
        isUpdateMode = false;
        edittitle = (EditText)findViewById(R.id.title);
        editdescription=(EditText)findViewById(R.id.description);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        delete=(Button)findViewById(R.id.delete);
        delete.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        if (b!=null && b.containsKey("source")){
            if(b.getString("source").equalsIgnoreCase("editPress")){
                isUpdateMode = true;
                noteId = b.getInt("noteId");
                edittitle.setText(b.getString("noteTitle"));
                editdescription.setText(b.getString("noteDescription"));
                delete.setVisibility(View.VISIBLE);
            } else if(b.getString("source").equalsIgnoreCase("addPress")){
                isUpdateMode = false;
                delete.setVisibility(View.GONE);
            } else {
                Toast.makeText(this,"INVALID ARGUMENTS",Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                saveNote();
                break;
            case R.id.delete:
                deleteNote();
                break;
        }
    }


    private void saveNote() {
        title = edittitle.getText().toString();
        description = editdescription.getText().toString();
        if(!isValidNote()){
            return;
        }
        Notes notes = new Notes();
        notes.setTitle(title);
        notes.setDescription(description);
        DatabaseHandler databasehandler = new DatabaseHandler(this);
        if(!isUpdateMode){
            databasehandler.addNote(notes);
            Toast.makeText(this,"Note added successfully",Toast.LENGTH_LONG).show();
        }else{
            notes.setId(noteId);
            databasehandler.uptadeNote(notes);
            Toast.makeText(this,"Note updated successfully",Toast.LENGTH_LONG).show();
        }
        List<Notes>note = databasehandler.getallNotes();
        MainActivity.notesAdapter.clear();
        MainActivity.notesAdapter.addAll(note);
        MainActivity.notesAdapter.notifyDataSetChanged();
        super.onBackPressed();
    }

    private boolean isValidNote() {
        if(title.isEmpty() && description.isEmpty()){
            Toast.makeText(this,"Please Enter Title and Description",Toast.LENGTH_LONG).show();
            return false;
        }else if(title.isEmpty()){
            Toast.makeText(this,"Please Enter Title",Toast.LENGTH_LONG).show();
            return false;
        } else if(description.isEmpty()){
            Toast.makeText(this,"Please Enter Description",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void deleteNote() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler databasehandler = new DatabaseHandler(EditnoteActivity.this);
                        databasehandler.deleteNote(String.valueOf(noteId));
                        List<Notes>note = databasehandler.getallNotes();
                        MainActivity.notesAdapter.clear();
                        MainActivity.notesAdapter.addAll(note);
                        MainActivity.notesAdapter.notifyDataSetChanged();
                        EditnoteActivity.this.onBackPressed();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

}
