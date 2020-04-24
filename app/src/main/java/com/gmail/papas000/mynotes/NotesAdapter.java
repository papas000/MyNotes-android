package com.gmail.papas000.mynotes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.papas000.mynotes.MainActivity.database;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerView;
        TextView textView;
        TextView delButton;

        NoteViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.activity_main_note_row);
            textView = view.findViewById(R.id.activity_main_note_row_textview);
            delButton = view.findViewById(R.id.activity_main_note_row_del_button);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note current = (Note) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);

                    v.getContext().startActivity(intent);
                }
            });
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note current = (Note) containerView.getTag();
                    database.noteDao().delete(current.id);
                    reload();
                }
            });
        }
    }

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_note_row, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.textView.setText(current.contents);
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void reload() {
        notes = database.noteDao().getALlNotes();
        notifyDataSetChanged();
    }
}
