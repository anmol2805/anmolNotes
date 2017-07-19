package com.anmol.anmolnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button addnote;
    DatabaseHandler databaseHandler;
    public static NotesAdapter notesAdapter;
    ListView noteListView;
    List<Notes>notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addnote = (Button)findViewById(R.id.addnote);
        addnote.setOnClickListener(this);
        databaseHandler = new DatabaseHandler(this);
        notesList = databaseHandler.getallNotes();
        notesAdapter = new NotesAdapter(this,notesList);
        noteListView=(ListView)findViewById(R.id.list);
        try {
            noteListView.setAdapter(notesAdapter);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        noteListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this,EditnoteActivity.class);
        Bundle b = new Bundle();
        b.putString("source","addPress");
        i.putExtras(b);
        startActivity(i);
    }
}
