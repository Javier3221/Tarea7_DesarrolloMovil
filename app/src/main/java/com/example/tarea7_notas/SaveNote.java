package com.example.tarea7_notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaveNote extends AppCompatActivity {
    TextView title;
    EditText noteTitle, noteDesc;
    Button cancel, save;
    Context context = this;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);

        title = findViewById(R.id.title);
        noteTitle = findViewById(R.id.noteTitle);
        noteDesc = findViewById(R.id.noteDesc);
        cancel = findViewById(R.id.btnCancel);
        save = findViewById(R.id.btnSave);

        Notes note = (Notes) getIntent().getSerializableExtra("note");

        if (note.id != 0){
            title.setText("Edit Note");

            noteTitle.setText(note.noteTitle);
            noteDesc.setText(note.noteDescription);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty()){
                        Toast.makeText(context, "You need to fill both fields", Toast.LENGTH_LONG).show();
                    }
                    else{
                        editNote(note.id, noteTitle.getText().toString(), noteDesc.getText().toString());
                    }
                }
            });
        }
        else{
            title.setText("New Note");

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty()){
                        Toast.makeText(context, "You need to fill both fields", Toast.LENGTH_LONG).show();
                    }
                    else{
                        registerNote(noteTitle.getText().toString(), noteDesc.getText().toString());
                    }
                }
            });
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMainActivity();
            }
        });
    }

    private boolean isEmpty(){
        boolean empty = false;

        if (noteTitle.getText().toString().isEmpty()){
            empty = true;
        }
        else if (noteDesc.getText().toString().isEmpty()){
            empty = true;
        }

        return empty;
    }

    private void registerNote(String title, String desc){
        Database connection = new Database(this, "NotesApp", null, 1);
        db = connection.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("title", title);
        registro.put("description", desc);

        db.insert("Notes", null, registro);
        db.close();

        Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
        (new Handler()).postDelayed(this::returnToMainActivity, 1000);
    }

    private void editNote(int id, String title, String desc){
        Database connection = new Database(this, "NotesApp", null, 1);
        db = connection.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("title", title);
        registro.put("description", desc);

        int affectedRows = db.update("Notes", registro, "id = " + id, null);
        db.close();

        if (affectedRows > 0){
            Toast.makeText(this, "Updated successfully! " + affectedRows, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "An unknown error occurred", Toast.LENGTH_SHORT).show();
        }

        (new Handler()).postDelayed(this::returnToMainActivity, 1000);
    }

    private void returnToMainActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}