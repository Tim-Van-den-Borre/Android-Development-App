package com.example.medication_reminder.database;

import android.app.Application;

import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.dao.MedicationDAO;
import com.example.medication_reminder.entity.Status;
import com.example.medication_reminder.dao.StatusDAO;

import java.util.List;

/*
    Repository aanmaken. Geraadpleegd op 5/11/2020.
    https://developer.android.com/codelabs/android-room-with-a-view#8
 */
public class DatabaseRepository {

    // Instantie van de medication & status dao. Bevat de calls naar de database.
    private MedicationDAO medicationDAO;
    private StatusDAO statusDAO;

    // Constructor.
    // Repository heeft Room database instantie nodig & instantie van de medication / status dao.
    public DatabaseRepository(Application application) {
        Database db = Database.getDatabase(application);
        medicationDAO = db.medicationDAO();
        statusDAO = db.statusDAO();
    }

    // CRUD calls medication
    public List<Medication> getAllMedications(){
        return medicationDAO.getAllMedications();
    }

    public Medication getMedicationById(int id){
        return medicationDAO.getMedicationById(id);
    }

    public long insertMedication(Medication medication){
        long id = 0;
        id = medicationDAO.insertMedication(medication);
        return id;
    }

    public void updateMedication(Medication medication){
        medicationDAO.updateMedication(medication);
    }

    public void deleteMedication(Medication medication){
        medicationDAO.deleteMedication(medication);
    }

    public void deleteMedicationById(int id){
        medicationDAO.deleteMedicationById(id);
    }

    // CRUD calls status
    public List<Status> getStatusByMedicationId(int id){
        return statusDAO.getStatusByMedicationId(id);
    }

    public void insertStatus(Status status){
        statusDAO.insertStatus(status);
    }

    public void updateStatusById(Boolean b, int id){
        statusDAO.updateStatusById(b, id);
    }

    public void deleteStatusById(int id){
        statusDAO.deleteStatusById(id);
    }
}
