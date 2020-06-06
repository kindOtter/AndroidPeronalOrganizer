package com.example.androidhandin.Database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.androidhandin.DAO.NoteDAO;
import com.example.androidhandin.DBmodels.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PersonalOrganizerDatabaseTest {
    private NoteDAO noteDAO;
    private PersonalOrganizerDatabase db;

    @Before
    public void createDB() {
        Context contex = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(contex, PersonalOrganizerDatabase.class).build();
        noteDAO = db.noteDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

}