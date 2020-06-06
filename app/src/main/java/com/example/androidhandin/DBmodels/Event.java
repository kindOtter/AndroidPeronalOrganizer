package com.example.androidhandin.DBmodels;

import android.icu.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "Events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private long alarmDate;

    public Event(String title, String description, long alarmDate) {
        this.title = title;
        this.description = description;
        this.alarmDate = alarmDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(long alarmDate) {
        this.alarmDate = alarmDate;
    }
}
