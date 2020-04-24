package com.gmail.papas000.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static NotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(), NotesDatabase.class, "notes")
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.activity_main_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        notesAdapter = new NotesAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notesAdapter);

        FloatingActionButton button = findViewById(R.id.add_note_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.noteDao().create();
                notesAdapter.reload();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesAdapter.reload();
    }
}
