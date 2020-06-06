package com.example.androidhandin.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidhandin.DBmodels.Event;

import java.util.List;

@Dao
public interface EventDAO {
    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM Events")
    void cleanEventsTable();

    /*
    @Query("SELECT TOP(10) FROM Events ORDER BY id DESC")
    void getLatestEvents();
    */
    @Query("SELECT * FROM Events ORDER BY id DESC")
    LiveData<List<Event>> getAllEvents();
}
