package com.example.androidhandin.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidhandin.DBmodels.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM Notes WHERE isDone = 0")
    LiveData<List<Note>> getUnfinishedNotes();

    @Query("SELECT * FROM Notes WHERE isDone = 1 ORDER BY id DESC")
    LiveData<List<Note>> getFinishedNotes();
}

