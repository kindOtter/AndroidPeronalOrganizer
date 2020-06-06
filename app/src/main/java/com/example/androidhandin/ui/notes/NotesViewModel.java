package com.example.androidhandin.ui.notes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private PersonalOrganizerRepository repository;
    private LiveData<List<Note>> observableNotes;

    public NotesViewModel(Application application) {
        super(application);
        repository = PersonalOrganizerRepository.getInstance(application);
        observableNotes = repository.getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return observableNotes;
    }

    public void switchToAllNotes() {
        observableNotes = repository.getAllNotes();
    }

    public void switchTotFinishedNotes() {
        observableNotes = repository.getFinishedNotes();
    }

    public void switchToUnfisnishedNotes() {
        observableNotes = repository.getUnfinishedNotes();
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }
}