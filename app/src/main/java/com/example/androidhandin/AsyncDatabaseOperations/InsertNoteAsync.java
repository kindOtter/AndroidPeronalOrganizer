package com.example.androidhandin.AsyncDatabaseOperations;

import android.os.AsyncTask;

import com.example.androidhandin.DAO.NoteDAO;
import com.example.androidhandin.DBmodels.Note;

public class InsertNoteAsync extends AsyncTask<Note,Void,Void> {
    private NoteDAO noteDao;

    public InsertNoteAsync(NoteDAO noteDAO) {
        this.noteDao = noteDAO;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.insert(notes[0]);
        System.out.println("PERSONAL inserrting note into database: " + notes[0].getTitle());
        return null;
    }
}
