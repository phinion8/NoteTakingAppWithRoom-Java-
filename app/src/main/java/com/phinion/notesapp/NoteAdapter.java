package com.phinion.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.phinion.notesapp.databinding.ActivityMainBinding;
import com.phinion.notesapp.databinding.SampleNoteBinding;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.viewHolder> {



    public NoteAdapter(){
        super(CALLBACK);



    }

    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNoteTitle().equals(newItem.getNoteTitle()) && oldItem.getNoteDescription().equals(newItem.getNoteDescription());
        }

    };
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_note, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Note note = getItem(position);

        holder.binding.noteTitle.setText(note.getNoteTitle());
        holder.binding.noteDescription.setText(note.getNoteDescription());



    }

    public Note getNote(int position){
        return getItem(position);
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        SampleNoteBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleNoteBinding.bind(itemView);
        }
    }


}
