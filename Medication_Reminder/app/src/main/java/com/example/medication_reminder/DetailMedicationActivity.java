package com.example.medication_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.medication_reminder.database.DatabaseRepository;
import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.helper.FragmentListener;
import com.example.medication_reminder.helper.StatusHelper;

import java.text.ParseException;

/*
    2 fragments in 1 activity voorbeeld. Geraadpleegd op 8/11/2020
    https://devcfgc.com/two-fragments-in-one-activity-278b5ee45ae9
 */
public class DetailMedicationActivity extends AppCompatActivity implements FragmentListener {

    // Algemene ID van de medication (voor update)
    private int ID;

    // Fragment
    StatusMedicationFragment fragment3;
    ShowSymptomsFragment fragment4;

    // Fragment manager
    FragmentManager fragmentManager;

    // Show fragment. Data tonen uit de database(via communicatie van de DetailsMedicationFragment).
    ShowMedicationFragment medicationFragment;

    // Detail fragment
    DetailsMedicationFragment detailFragment;

    // Status fragment
    StatusMedicationFragment statusFragment;

    // Instantie van de database repository.
    private DatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseRepository = new DatabaseRepository(getApplication());
        fragment3 = new StatusMedicationFragment();
        fragment4 = new ShowSymptomsFragment();
        fragmentManager = getSupportFragmentManager();

        // Medication id ophalen uit de CustomAdapter.
        Intent intent = getIntent();
        ID = intent.getIntExtra("medicationID", -1);

        setContentView(R.layout.activity_update_medication);

        // DetailMedicationFragment
        detailFragment = (DetailsMedicationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        // ShowMedicationFragment ophalen.
        medicationFragment = (ShowMedicationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        // StatusMedicationFragment ophalen.
        statusFragment = (StatusMedicationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
    }

    // Zet de save knop in de naviagation bar
    // https://stackoverflow.com/questions/38158953/how-to-create-button-in-action-bar-in-android

    // Het juiste menu wordt gekozen en de buttons worden er aan toegevoegd.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_medication, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Sla de medicatie op via het 'save' icoon.
        if (id == R.id.update_medication_button) {
            detailFragment.saveMedication();
        }

        // Verwijder de medicatie op basis van id.
        if(id == R.id.Medication_Delete_Button){
            databaseRepository.deleteStatusById(ID);
            databaseRepository.deleteMedicationById(ID);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(id == R.id.Medication_Symptoms_Button){
            if(fragment4.isAdded())
            {
                return false;
            }else {
                showSymptomFragment();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        Methode voor het updaten van een medication in de MedicationList.
     */
    @Override
    public void updateMedication(Medication medication) throws ParseException {
        // Update de fields in de showMedication fragment.
        medicationFragment.setTextFieldsInFragment(medication);

        // Voor update van medication de statusses verwijderen.
        databaseRepository.deleteStatusById(medication.id);

        // Statusses opnieuw aanmaken.
        StatusHelper.createStatuses(medication.start_date, medication.end_date, medication.quantity, medication.id, databaseRepository);

        // Update de medication in de database.
        databaseRepository.updateMedication(medication);
    }

    @Override
    public int getMedicationId() {
        return ID;
    }

    /*
        Methode voor het weergeven van de statusList (fragment).

        Overlay fragment. Geraadpleegd op 01/12/2020.
        https://stackoverflow.com/questions/26300674/android-fragment-overlay-another-fragment-with-semi-transparent
     */
    public void showStatusFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment3, fragment3);
        transaction.commit();
    }

    /*
    Methode voor het weergeven van de SymptomList (fragment).

    Overlay fragment. Geraadpleegd op 01/12/2020.
    https://stackoverflow.com/questions/26300674/android-fragment-overlay-another-fragment-with-semi-transparent
 */
    public void showSymptomFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment4, fragment4);
        transaction.commit();
    }

    /*
        Methode voor het verwijderen van de status fragment.

        Destory fragment. Geraadpleegd op 01/12/2020.
        https://stackoverflow.com/questions/7119203/how-to-destroy-fragment
     */
    public void destroyStatusFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment3).commit();
    }

    /*
    Methode voor het verwijderen van de status fragment.

    Destory fragment. Geraadpleegd op 01/12/2020.
    https://stackoverflow.com/questions/7119203/how-to-destroy-fragment
 */
    public void destroySymptomFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment4).commit();
    }
}