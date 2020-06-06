package com.example.androidhandin.ui.notes;

import android.app.Application;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.R;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.NoteHolder> {

    private OnItemClickListener listener;
    private PersonalOrganizerRepository repository;

    public NotesAdapter(Application application) {
        super(DIFF_CALLBACK);
        repository = PersonalOrganizerRepository.getInstance(application);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getDate().equals(newItem.getDate());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, int position) {
        final Note currentNote = getItem(position);
        holder.noteViewTitle.setText(currentNote.getTitle());
        holder.noteViewDescription.setText(currentNote.getDescription());
        if(currentNote.isDone())
            holder.checkButton.setColorFilter(Color.GREEN);
        holder.checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNote.isDone()) {
                    currentNote.setDone(false);
                    holder.checkButton.setColorFilter(null);
                } else if(!currentNote.isDone()) {
                    currentNote.setDone(true);
                    holder.checkButton.setColorFilter(Color.GREEN);
                }
                repository.updateNote(currentNote);
            }
        });
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView noteViewTitle;
        private TextView noteViewDescription;
        private ImageView checkButton;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteViewTitle = itemView.findViewById(R.id.note_view_title);
            noteViewDescription = itemView.findViewById(R.id.note_view_description);
            checkButton = itemView.findViewById(R.id.note_check_image);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
