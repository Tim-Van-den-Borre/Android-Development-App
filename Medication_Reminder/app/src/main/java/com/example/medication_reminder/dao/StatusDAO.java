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

    @Query("SELECT * FROM Status WHERE medicationID == :medicationID")
    List<Status> getStatusByMedicationId(int medicationID);

    @Query("UPDATE Status SET checked = :checkbox WHERE id LIKE :id")
    void updateStatus(Boolean checkbox, int id);

    @Insert
    void insert(Status status);

    @Query("DELETE FROM Status WHERE medicationID LIKE :id")
    void delete(int id);
}
