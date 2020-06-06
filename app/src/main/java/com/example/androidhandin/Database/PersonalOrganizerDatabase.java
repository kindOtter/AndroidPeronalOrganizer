package com.example.androidhandin.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androidhandin.DAO.EventDAO;
import com.example.androidhandin.DAO.NoteDAO;
import com.example.androidhandin.DBmodels.Event;
import com.example.androidhandin.DBmodels.Note;

@Database(entities = {Note.class, Event.class}, version = 3)
public abstract class PersonalOrganizerDatabase extends RoomDatabase {

    private static PersonalOrganizerDatabase instance;
    public abstract NoteDAO noteDAO();
    public abstract EventDAO eventDAO();

    public PersonalOrganizerDatabase() {
    }

    public static synchronized PersonalOrganizerDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PersonalOrganizerDatabase.class, "Personal_Organizer_Database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO noteDAO;

        private PopulateDbAsyncTask(PersonalOrganizerDatabase db) {
            noteDAO = db.noteDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Title 1", "Description 1"));
            noteDAO.insert(new Note("Title 2", "Description 2"));
            return null;
        }
    }
}
