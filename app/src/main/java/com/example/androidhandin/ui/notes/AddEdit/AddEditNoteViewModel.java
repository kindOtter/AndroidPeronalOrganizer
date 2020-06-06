package com.example.androidhandin.ui.notes.AddEdit;

import android.app.AlertDialog;
import android.app.Application;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddEditNoteViewModel extends AndroidViewModel {

    private PersonalOrganizerRepository repository;

    public AddEditNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonalOrganizerRepository(application);
    }

    public void createNote(String title, String description) {
        Note note = new Note(title, description);
        repository.insertNote(note);
    }

    public void updateNote(int id, String title, String description) {
        Note note = new Note(title, description);
        note.setId(id);
        repository.updateNote(note);
    }
}
