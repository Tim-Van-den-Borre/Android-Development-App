package com.example.medication_reminder;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.text.ParseException;

/*
    2 fragments in 1 activity voorbeeld. Geraadpleegd op 8/11/2020
    https://devcfgc.com/two-fragments-in-one-activity-278b5ee45ae9
 */
public class DetailMedicationActivity extends AppCompatActivity implements DetailsMedicationFragment.DetailsMedicationFragmentListener {

    // Algemene ID van de medication (voor update)
    public int ID;

    // Fragment
    StatusMedicationFragment fragment;

    // Fragment manager
    FragmentManager fragmentManager;

    // Show fragment. Data tonen uit de database(via communicatie van de DetailsMedicationFragment).
    ShowMedicationFragment medicationFragment;

    // Status fragment
    StatusMedicationFragment statusFragment;

    // Instantie van de database repository.
    private DatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseRepository = new DatabaseRepository(getApplication());
        fragment = new StatusMedicationFragment();
        fragmentManager = getSupportFragmentManager();

        // Medication id ophalen uit de CustomAdapter.
        Intent intent = getIntent();
        ID = intent.getIntExtra("medicationID", -1);

        setContentView(R.layout.activity_updatemedication);

        // ShowMedicationFragment ophalen.
        medicationFragment = (ShowMedicationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        // StatusMedicationFragment ophalen.
        statusFragment = (StatusMedicationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
    }

    /*
        Methode voor het updaten van een medication in de MedicationList.
     */
    @Override
    public void updateMedication(Medication medication) throws ParseException {
        // Update de fields in de showMedication fragment.
        medicationFragment.setTextFieldsInFragment(medication);

        // Voor update van medication de statusses verwijderen.
        databaseRepository.deleteStatus(medication.id);

        // Statusses opnieuw aanmaken.
        StatusHelper.createStatuses(medication.start_date, medication.end_date, medication.quantity, medication.id, databaseRepository);

        // Update de medication in de database.
        databaseRepository.updateMedication(medication);
    }

    /*
        Methode voor het weergeven van de statusList (fragment).

        Overlay fragment. Geraadpleegd op 01/12/2020.
        https://stackoverflow.com/questions/26300674/android-fragment-overlay-another-fragment-with-semi-transparent
     */
    public void showStatusFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment3, fragment);
        transaction.commit();
    }

    /*
        Methode voor het verwijderen van de status fragment.

        Destory fragment. Geraadpleegd op 01/12/2020.
        https://stackoverflow.com/questions/7119203/how-to-destroy-fragment
     */
    public void destroyFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment).commit();
    }
}