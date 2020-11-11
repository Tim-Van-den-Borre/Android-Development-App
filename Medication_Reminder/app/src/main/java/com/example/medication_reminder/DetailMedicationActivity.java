package com.example.medication_reminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailMedicationActivity extends AppCompatActivity implements DetailsMedicationFragment.DetailsMedicationFragmentListener {

    // ID voor de update uit te voeren.
    public int ID;

    // Data tonen uit de database(via communicatie van de DetailsMedicationFragment).
    ShowMedicationFragment medicationFragment;

    // Instantie van de database repository.
    private DatabaseRepository databaseRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseRepository = new DatabaseRepository(getApplication());

        // Medication id ophalen uit de CustomAdapter.
        Intent intent = getIntent();
        ID = intent.getIntExtra("medicationID", -1);

        setContentView(R.layout.activity_updatemedication);

        // ShowMedicationFragment ophalen.
        medicationFragment = (ShowMedicationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
    }

    /*
        Methode voor het updaten van een medication.
     */
    @Override
    public void updateMedication(Medication medication) {
        // Update de fields in de showMedication fragment.
        medicationFragment.setTextFieldsInFragment(medication);

        // Update de medication in de database.
        databaseRepository.updateMedication(medication);
    }
}

/*
    2 fragments in 1 activity voorbeeld. Geraadpleegd op 8/11/2020
    https://devcfgc.com/two-fragments-in-one-activity-278b5ee45ae9
 */