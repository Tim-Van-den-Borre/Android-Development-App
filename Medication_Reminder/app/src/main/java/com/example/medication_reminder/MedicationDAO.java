package com.example.medication_reminder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
    Dao's. Geraadpleegd op 5/11/2020.
    https://developer.android.com/training/data-storage/room?hl=en#java
 */
@Dao
public interface MedicationDAO {
    @Query("SELECT * FROM Medication")
    List<Medication> getAll();

    @Insert
    void insert(Medication Medication);

    @Update
    void update(Medication medication);

    @Delete
    void delete(Medication medication);
}