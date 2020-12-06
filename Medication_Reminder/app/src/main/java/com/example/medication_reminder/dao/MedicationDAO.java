package com.example.medication_reminder.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.medication_reminder.entity.Medication;
import java.util.List;

/*
    Dao's. Geraadpleegd op 5/11/2020.
    https://developer.android.com/training/data-storage/room?hl=en#java
 */
@Dao
public interface MedicationDAO {

    // Alle medications ophalen
    @Query("SELECT * FROM Medication")
    List<Medication> getAllMedications();

    // Medication ophalen op basis van ID
    @Query("SELECT * FROM Medication WHERE id == :id")
    Medication getMedicationById(int id);

    // Medication toevoegen
    @Insert
    long insertMedication(Medication Medication);

    // Medication aanpassen
    @Update
    void updateMedication(Medication medication);

    // Medication verwijderen
    @Delete
    void deleteMedication(Medication medication);

    // Verwijderen van een medication op basis van id.
    @Query("DELETE FROM Medication WHERE id LIKE :id")
    void deleteMedicationById(int id);
}