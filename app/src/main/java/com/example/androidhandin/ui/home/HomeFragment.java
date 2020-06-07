package com.example.androidhandin.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhandin.ui.events.AddEdit.AddEditEventActivity;
import com.example.androidhandin.ui.notes.AddEdit.AddEditNoteActivity;
import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.R;
import com.example.androidhandin.ui.notes.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FloatingActionButton extendButton;
    private FloatingActionButton addNoteButton;
    private FloatingActionButton addEventButton;
    private TextView quoteOfTheDayTextView;
    private boolean isFloatingButtonOpen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.home_listOfNotes_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final NotesAdapter adapter = new NotesAdapter(getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        homeViewModel.getUnfinishedNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });
        isFloatingButtonOpen = false;
        quoteOfTheDayTextView = root.findViewById(R.id.quoteOfTheDayTextView);
        extendButton = root.findViewById(R.id.floatingButtonAddEventOrNote);
        addNoteButton = root.findViewById(R.id.floatingButtonAddNote_home);
        addEventButton = root.findViewById(R.id.floatingButtonAddEvent_home);
        String quote = homeViewModel.getQuote();
        //String quote = "\"If you cannot do great things, do small things in a great way\" ~ Napoleon Hill";
        if(quote == null) {
            quoteOfTheDayTextView.setText(R.string.emergency_quote);
        } else {
            quoteOfTheDayTextView.setText(quote);
        }
        extendButton.bringToFront();
        extendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFloatingButtonOpen) {
                    hideFloatingButtons();
                } else {
                    showFloatingButtons();
                }
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                homeViewModel.deleteNote(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(getContext(), AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DATE, note.getDate());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                startActivity(intent);
            }
        });
        return root;
    }



    private void showFloatingButtons() {
        isFloatingButtonOpen = true;
        addNoteButton.animate().translationY(-180);
        addEventButton.animate().translationY(-360);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddNoteActivity();
            }
        });
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddEventActivity();
            }
        });
    }

    private void hideFloatingButtons() {
        isFloatingButtonOpen= false;
        addNoteButton.animate().translationY(0);
        addEventButton.animate().translationY(0);
    }

    private void startAddNoteActivity() {
        Context context = getContext();
        Class addNote = AddEditNoteActivity.class;
        Intent intent = new Intent(context, addNote);
        startActivity(intent);
    }

    private void startAddEventActivity() {
        Context context = getContext();
        Class addEvent = AddEditEventActivity.class;
        Intent intent = new Intent(context, addEvent);
        startActivity(intent);
    }
}
