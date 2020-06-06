package com.example.androidhandin.ui.events;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidhandin.DBmodels.Event;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    private PersonalOrganizerRepository repository;

    public EventsViewModel(Application application) {
        super(application);
        repository = PersonalOrganizerRepository.getInstance(application);
    }

    public LiveData<List<Event>> getAllEvents() {
        return repository.getAllEvents();
    }

    public void deleteEvent(Event event) {
        repository.deleteEvent(event);
    }
}