package com.example.androidhandin.ui.events.AddEdit;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.androidhandin.DBmodels.Event;
import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.Database.PersonalOrganizerDatabase;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditEventActivityViewModel extends AndroidViewModel {

    private PersonalOrganizerRepository repository;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    public AddEditEventActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonalOrganizerRepository(application);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        timeFormatter = new SimpleDateFormat("HH:mm");
    }

    public void createEvent(String title, String description, long date) {
        Event event = new Event(title, description, date);
        repository.insertEvent(event);
    }

    public void updateEvent(int id, String title, String description, long date) {
        Event event = new Event(title, description, date);
        event.setId(id);
        repository.updateEvent(event);
    }

    public String formatDate(Date date) {
        return dateFormatter.format(date);
    }

    public String formatTime(Date date) {
        return timeFormatter.format(date);
    }

}
