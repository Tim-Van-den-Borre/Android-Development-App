package com.example.medication_reminder;

import android.app.Application;

import java.util.List;

/*
    Repository aanmaken. Geraadpleegd op 5/11/2020.
    https://developer.android.com/codelabs/android-room-with-a-view#8
 */
public class DatabaseRepository {
    // instantie van de medication dao.
    private MedicationDAO medicationDAO;

    // constructor. Heeft een instantie nodig van de room database & van de medicationdao.
    DatabaseRepository(Application application) {
        Database db = Database.getDatabase(application);
        medicationDAO = db.medicationDAO();
    }

    // crud calls
    public List<Medication> getAllMedication(){
        return medicationDAO.getAll();
    }

    public void insertMedication(Medication medication){
        medicationDAO.insert(medication);
    }

    public void updateMedication(Medication medication){
        medicationDAO.update(medication);
    }

    public void deleteMedication(Medication medication){
        medicationDAO.delete(medication);
    }
}
