package com.example.tarea7_notas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Notes> notes;

    public CustomAdapter(Context context, List<Notes> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView title;
        ImageButton edit;
        ImageButton delete;

        Notes n = notes.get(i);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_notes, null);
        }
        title = view.findViewById(R.id.note_title);
        edit = view.findViewById(R.id.ibEdit);
        delete = view.findViewById(R.id.ibDelete);

        title.setText(n.noteTitle);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SaveNote.class);
                intent.putExtra("note", new Notes(n.id, n.noteTitle, n.noteDescription));
                ((MainActivity)context).startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Are you sure you want to delete this note?")
                        .setTitle("Delete note")
                        .setNegativeButton("Keep it", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Delete it", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Database connection = new Database(context, "NotesApp", null, 1);
                                SQLiteDatabase db = connection.getWritableDatabase();

                                int affectedRows = db.delete("Notes", "id=" + n.id, null);
                                db.close();

                                if (affectedRows > 0){
                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    ((MainActivity)context).refresh();
                                    ((MainActivity)context).overridePendingTransition(0, 0);
                                }
                                else{
                                    Toast.makeText(context, "An unknown error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.show();
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowNote.class);
                intent.putExtra("note", new Notes(n.id, n.noteTitle, n.noteDescription));
                ((MainActivity)context).startActivity(intent);
            }
        });

        return view;
    }
}
