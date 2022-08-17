package com.phinion.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.phinion.notesapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        binding.floatingaddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
                intent.putExtra("type", "addMode");
                startActivityForResult(intent, 1);


            }
        });

        binding.noteList.setLayoutManager(new LinearLayoutManager(this));
        binding.noteList.setHasFixedSize(true);
        adapter = new NoteAdapter();
        binding.noteList.setAdapter(adapter);














        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.RIGHT){
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));



                    Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();


                }else {

                    Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
                    intent.putExtra("type", "update");
                    intent.putExtra("title", adapter.getNote(viewHolder.getAdapterPosition()).getNoteTitle());
                    intent.putExtra("noteDescription", adapter.getNote(viewHolder.getAdapterPosition()).getNoteDescription());
                    intent.putExtra("id", adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent, 2);

                }

            }
        }).attachToRecyclerView(binding.noteList);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("noteDescription");
            Note note = new Note(title, description);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == 2){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("noteDescription");
            Note note = new Note(title, description);
            note.setId(data.getIntExtra("id", 0));
            noteViewModel.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
    }


}