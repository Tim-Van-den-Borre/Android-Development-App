package com.example.medication_reminder;

import android.app.Application;

import java.util.List;

/*
    Repository aanmaken. Geraadpleegd op 5/11/2020.
    https://developer.android.com/codelabs/android-room-with-a-view#8
 */
public class DatabaseRepository {
    // Instantie van de medication dao. Bevat de calls naar de database.
    private MedicationDAO medicationDAO;

    // Constructor.
    // Repository heeft Room database instantie nodig & instantie van de medication dao.
    DatabaseRepository(Application application) {
        Database db = Database.getDatabase(application);
        medicationDAO = db.medicationDAO();
    }

    // CRUD calls
    public List<Medication> getAllMedication(){
        return medicationDAO.getAll();
    }

    public void insertMedication(Medication medication){
        medicationDAO.insert(medication);
    }

    public Medication getMedicationById(int id){
        return medicationDAO.getMedicationById(id);
    }

    public void updateMedication(Medication medication){
        medicationDAO.update(medication);
    }

    public void deleteMedication(Medication medication){
        medicationDAO.delete(medication);
    }
}
