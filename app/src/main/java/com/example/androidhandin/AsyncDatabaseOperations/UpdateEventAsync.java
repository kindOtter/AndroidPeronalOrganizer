package com.example.androidhandin.AsyncDatabaseOperations;

import android.os.AsyncTask;

import com.example.androidhandin.DAO.EventDAO;
import com.example.androidhandin.DBmodels.Event;

public class UpdateEventAsync extends AsyncTask<Event, Void, Void> {
    private EventDAO eventDAO;

    public UpdateEventAsync(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    protected Void doInBackground(Event... events) {
        eventDAO.update(events[0]);
        return null;
    }
}
