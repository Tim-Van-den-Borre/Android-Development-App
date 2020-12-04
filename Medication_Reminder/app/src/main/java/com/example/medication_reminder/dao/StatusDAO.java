package com.example.medication_reminder.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.medication_reminder.entity.Status;
import java.util.List;

/*
    Dao's. Geraadpleegd op 5/11/2020.
    https://developer.android.com/training/data-storage/room?hl=en#java
 */
@Dao
public interface StatusDAO {

    // Alle statussen ophalen op basis van een medication ID
    @Query("SELECT * FROM Status WHERE medicationID == :medicationID")
    List<Status> getStatusByMedicationId(int medicationID);

    // Status aanpassen -> Checked, niet checkedS
    @Query("UPDATE Status SET checked = :checkbox WHERE id LIKE :id")
    void updateStatusById(Boolean checkbox, int id);

    // Toevoegen van een status
    @Insert
    void insertStatus(Status status);

    // Verwijderen van een status
    @Query("DELETE FROM Status WHERE medicationID LIKE :id")
    void deleteStatusById(int id);
}
