package com.example.androidhandin.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private PersonalOrganizerRepository repository;

    public HomeViewModel(Application application) {
        super(application);
        repository = PersonalOrganizerRepository.getInstance(application);
    }

    public LiveData<List<Note>> getUnfinishedNotes() {
        LiveData<List<Note>> liveNotes = repository.getUnfinishedNotes();
        return liveNotes;
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    public String getQuote() {
        return repository.getQuote();
    }
}