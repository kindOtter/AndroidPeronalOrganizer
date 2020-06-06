package com.example.androidhandin.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.androidhandin.AsyncDatabaseOperations.DeleteEventAsync;
import com.example.androidhandin.AsyncDatabaseOperations.DeleteNoteAsync;
import com.example.androidhandin.AsyncDatabaseOperations.InsertEventAsync;
import com.example.androidhandin.AsyncDatabaseOperations.InsertNoteAsync;
import com.example.androidhandin.AsyncDatabaseOperations.UpdateEventAsync;
import com.example.androidhandin.AsyncDatabaseOperations.UpdateNoteAsync;
import com.example.androidhandin.DAO.EventDAO;
import com.example.androidhandin.DAO.NoteDAO;
import com.example.androidhandin.DBmodels.Event;
import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.Database.PersonalOrganizerDatabase;
import com.example.androidhandin.Networking.QuoteApi;

import java.util.List;

public class PersonalOrganizerRepository {

    private NoteDAO noteDAO;
    private EventDAO eventDAO;
    private static PersonalOrganizerRepository instance;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> unfinishedNotes;
    private LiveData<List<Note>> finishedNotes;
    private LiveData<List<Event>> allEvents;
    private QuoteApi quoteApi;

    public PersonalOrganizerRepository(Application application) {
        PersonalOrganizerDatabase database = PersonalOrganizerDatabase.getInstance(application);
        noteDAO = database.noteDAO();
        eventDAO = database.eventDAO();
        allNotes = noteDAO.getAllNotes();
        unfinishedNotes = noteDAO.getUnfinishedNotes();
        allEvents = eventDAO.getAllEvents();
        finishedNotes = noteDAO.getFinishedNotes();
        quoteApi = new QuoteApi();
    }

    public static synchronized PersonalOrganizerRepository getInstance(Application application) {
        if(instance == null)
            instance = new PersonalOrganizerRepository(application);
        return instance;
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getFinishedNotes() {
        return finishedNotes;
    }

    public LiveData<List<Note>> getUnfinishedNotes() {
        return unfinishedNotes;
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }


    public void insertNote(Note note) {
        new InsertNoteAsync(noteDAO).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsync(noteDAO).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsync(noteDAO).execute(note);
    }

    public void insertEvent(Event event) {
        new InsertEventAsync(eventDAO).execute(event);
    }

    public void updateEvent(Event event) {
        new UpdateEventAsync(eventDAO).execute(event);
    }

    public void deleteEvent(Event event) {
        new DeleteEventAsync(eventDAO).execute(event);
    }

    public String getQuote() {
        try {
            return new QuoteApi().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
