package com.example.tarea7_notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private ListView lv1;
    Context context = this;
    Button newNote;
    SQLiteDatabase db;

    ArrayList<Notes> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        lv1 = (ListView) findViewById(R.id.lv1);
        newNote = (Button) findViewById(R.id.newNote);

        Database connection = new Database(this, "NotesApp", null, 1);
        db = connection.getWritableDatabase();

        CustomAdapter adapter = new CustomAdapter(this, GetData());
        lv1.setAdapter(adapter);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SaveNote.class);
                intent.putExtra("note", new Notes(0, null, null));
                startActivity(intent);
            }
        });
    }

    private List<Notes> GetData() {
        list = new ArrayList<Notes>();

        Cursor c = db.rawQuery("select id, title, description from Notes", null);

        if(c.moveToFirst()){
            do{
                Notes obj = new Notes(c.getInt(0), c.getString(1), c.getString(2));
                list.add(obj);
            }
            while(c.moveToNext());
        }

        c.close();
        return list;
    }

    public void refresh(){
        finish();
        startActivity(getIntent());
    }
}