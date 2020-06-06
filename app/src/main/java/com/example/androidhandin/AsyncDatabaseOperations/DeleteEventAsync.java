package com.example.androidhandin.AsyncDatabaseOperations;

import android.os.AsyncTask;

import com.example.androidhandin.DAO.EventDAO;
import com.example.androidhandin.DBmodels.Event;

public class DeleteEventAsync extends AsyncTask<Event,Void,Void> {
    private EventDAO eventDAO;

    public DeleteEventAsync(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    protected Void doInBackground(Event... events) {
        eventDAO.delete(events[0]);
        return null;
    }
}
