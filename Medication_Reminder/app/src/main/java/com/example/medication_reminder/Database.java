package com.example.medication_reminder;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
    Room database singleton. Geraadpleegd op 5/11/2020.
    https://developer.android.com/codelabs/android-room-with-a-view#7

    allowMainThreadQueries nodig voor database access op main thread.
    https://stackoverflow.com/questions/52999851/why-is-recommended-not-to-use-allowmainthreadqueries-for-android-room
 */
@androidx.room.Database(entities = {Medication.class}, version = 1)
public abstract class Database extends RoomDatabase {

    // instantie van de medication dao
    public abstract MedicationDAO medicationDAO();

    // 1 instance nodig van de room database voor de app -> singleton.
    private static volatile Database singleton;

    // singleton aanmaken.
    static Database getDatabase(final Context context) {
        if (singleton == null) {
            synchronized (Database.class) {
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context.getApplicationContext(), Database.class, "Database").allowMainThreadQueries().build();
                }
            }
        }
        return singleton;
    }
}