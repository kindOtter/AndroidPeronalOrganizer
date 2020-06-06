package com.example.androidhandin.AsyncDatabaseOperations;

import android.os.AsyncTask;

import com.example.androidhandin.DAO.NoteDAO;
import com.example.androidhandin.DBmodels.Note;

public class UpdateNoteAsync extends AsyncTask<Note, Void, Void> {
    private NoteDAO noteDAO;

    public UpdateNoteAsync(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.update(notes[0]);
        return null;
    }
}
