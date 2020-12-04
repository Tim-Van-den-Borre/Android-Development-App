package com.example.medication_reminder.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.dao.MedicationDAO;
import com.example.medication_reminder.entity.Status;
import com.example.medication_reminder.dao.StatusDAO;

/*
    Room database singleton. Geraadpleegd op 5/11/2020.
    https://developer.android.com/codelabs/android-room-with-a-view#7

    allowMainThreadQueries nodig voor database access op main thread.
    https://stackoverflow.com/questions/52999851/why-is-recommended-not-to-use-allowmainthreadqueries-for-android-room

    Roomdatabase migration problems. fallbacktodestructivemigration
    https://stackoverflow.com/questions/49629656/please-provide-a-migration-in-the-builder-or-call-fallbacktodestructivemigration
 */
@androidx.room.Database(entities = {Medication.class, Status.class}, version = 9) // Versie waarde +1 bij verandering in Dao / entity.
public abstract class Database extends RoomDatabase {

    // Instantie van de medication dao zodat de database aan de calls kan.
    public abstract MedicationDAO medicationDAO();
    public abstract StatusDAO statusDAO();

    // 1 instance nodig van de room database voor de app -> singleton.
    private static volatile Database singleton;

    // Singleton aanmaken.
    static Database getDatabase(final Context context) {
        if (singleton == null) {
            synchronized (Database.class) {
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context.getApplicationContext(), Database.class, "Database")
                            .allowMainThreadQueries().fallbackToDestructiveMigration() // Fix migration problems & database access op main thread.
                            .build();
                }
            }
        }
        return singleton;
    }
}