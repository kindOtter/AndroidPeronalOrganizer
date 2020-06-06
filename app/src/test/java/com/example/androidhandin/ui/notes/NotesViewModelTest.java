package com.example.androidhandin.ui.notes;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;

import com.example.androidhandin.DBmodels.Note;
import com.example.androidhandin.Repository.PersonalOrganizerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotesViewModelTest {

    private PersonalOrganizerRepository repository;
    private LiveData<List<Note>> observableNotes;
    private Note exampleNote;

    @Mock
    Application application;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        application = mock(Application.class);
        when(application.getApplicationContext()).thenReturn(context);
        repository = PersonalOrganizerRepository.getInstance(application);
        observableNotes = repository.getAllNotes();
        //exampleNote = new Note("Test Junit4", "Example description");
    }

    @Test
    public void switchTotFinishedNotes() {
        observableNotes = repository.getFinishedNotes();
        for (Note note: observableNotes.getValue()) {
            assertEquals(note.isDone(), true);
        }
    }

    @Test
    public void switchToUnfisnishedNotes() {
        observableNotes = repository.getUnfinishedNotes();
        for (Note note: observableNotes.getValue()) {
            assertEquals(note.isDone(), false);
        }
    }

    @Test
    public void switchToAllNotes() {
        observableNotes = repository.getAllNotes();
        int allNotesSize = observableNotes.getValue().size();
        observableNotes = repository.getFinishedNotes();
        int finishedNotesSize = observableNotes.getValue().size();
        observableNotes = repository.getUnfinishedNotes();
        int unfinishedNotesSize = observableNotes.getValue().size();
        assertEquals(allNotesSize, (unfinishedNotesSize + finishedNotesSize));
    }

//    @Test
//    public void addNote() {
//        repository.insertNote(exampleNote);
//    }
//
//    @Test
//    public void deleteNote() {
//        repository.deleteNote(exampleNote);
//    }
}