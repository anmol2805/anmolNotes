package com.anmol.anmolnotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anmol on 2017-07-04.
 */

public class NotesAdapter extends ArrayAdapter<Notes> {
    private List<Notes>notesList;
    private Context context;

    public NotesAdapter(Context context, List<Notes>notesList) {
        super(context,R.layout.list_notes,notesList);
        this.notesList = notesList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_notes,null);
        }
        final Notes notes = notesList.get(position);
        if (notes!= null){
            TextView title = (TextView)v.findViewById(R.id.title);
            TextView description = (TextView)v.findViewById(R.id.description);
            TextView index = (TextView)v.findViewById(R.id.index);
            if(title!=null){
                title.setText(notes.getTitle());
                index.setText((position + 1) + ".");
            }
            if(description!=null){
                description.setText(notes.getDescription());
            }
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,EditnoteActivity.class);
                Bundle b = new Bundle();
                Notes notes = notesList.get(position);
                b.putString("source","editPress");
                b.putString("noteTitle",notes.getTitle());
                b.putString("noteDescription",notes.getDescription());
                b.putInt("noteId",notes.getId());
                i.putExtras(b);
                context.startActivity(i);
            }
        });
        return v;
    }
}
