package com.example.medication_reminder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    Entity. Geraadpleegd op 5/11/2020.
    https://developer.android.com/training/data-storage/room?hl=en#java
    https://developer.android.com/training/data-storage/room/defining-data#java
 */
@Entity(tableName = "Medication")
public class Medication {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "start_date")
    public String start_date;

    @ColumnInfo(name = "end_date")
    public String end_date;

    @ColumnInfo(name = "extra_info")
    public String extra_info;

    public Medication(){

    }

    public Medication(String name, String description, int quantity, String start_date, String end_date, String extra_info) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.start_date = start_date;
        this.end_date = end_date;
        this.extra_info = extra_info;
    }
}
