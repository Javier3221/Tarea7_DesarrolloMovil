package com.example.tarea7_notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowNote extends AppCompatActivity {

    TextView title, description;
    Button goBack;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        Notes note = (Notes) getIntent().getSerializableExtra("note");

        title = findViewById(R.id.noteTitle);
        description = findViewById(R.id.noteDescription);
        goBack = findViewById(R.id.btnBack);

        if (note != null){
            title.setText(note.noteTitle);
            description.setText(note.noteDescription);
            goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, MainActivity.class);
                    ((ShowNote)context).startActivity(i);
                }
            });
        }
    }
}