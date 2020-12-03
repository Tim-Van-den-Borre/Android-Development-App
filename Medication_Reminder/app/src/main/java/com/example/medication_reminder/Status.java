package com.example.medication_reminder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    Entity. Geraadpleegd op 5/11/2020.
    https://developer.android.com/training/data-storage/room?hl=en#java
    https://developer.android.com/training/data-storage/room/defining-data#java
 */
@Entity(tableName = "Status")
public class Status {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "checked")
    public Boolean checked;

    @ColumnInfo(name = "medicationID")
    public long medicationID;

    public Status() {
    }

    public Status(String date, Boolean checked, long medicationID) {
        this.date = date;
        this.checked = checked;
        this.medicationID = medicationID;
    }
}
