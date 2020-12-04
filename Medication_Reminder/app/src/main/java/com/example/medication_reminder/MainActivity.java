package com.example.medication_reminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.medication_reminder.adapter.CustomMedicationAdapter;
import com.example.medication_reminder.database.DatabaseRepository;
import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.helper.StatusHelper;
import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addMedication;
    private ListView showMedication;
    private DatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantie van de repository.
        databaseRepository = new DatabaseRepository(getApplication());

        // Ophalen id's.
        addMedication = findViewById(R.id.add_Medication_button);
        showMedication = findViewById(R.id.show_medication);

        // Listeners
        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMedication();
            }
        });

        // Tonen van de MedicationList
        showMedicationList();
    }

    /*
        Methode voor het toevoegen van een medication aan de MedicationList.
        Intent voor de navigatie naar CreateMedicationActivity.
        Deze intent verwacht in tegenstelling tot de gewone intent een response. Wordt opgevangen door de methode onActivityResult.
        startActivityForResult krijgt een intent en een requestcode mee (200 gekozen voor OK).

        startActivityForResult. Geraadpleegd op 4/11/2020
        https://developer.android.com/training/basics/intents/result
        https://developer.android.com/reference/android/app/Activity#startActivityForResult(android.content.Intent,%20int)
        https://stuff.mit.edu/afs/sipb/project/android/docs/training/basics/intents/result.html
     */
    public void addMedication(){
        Intent intent = new Intent(this, CreateMedicationActivity.class);
        startActivityForResult(intent, 200);
    }

    /*
        Methode voor het opvangen van de response op startActivityForResult. Bevat de data van de aangemaakte Medication.

        onActivityResult. Geraadpleegd op 4/11/2020.
        https://developer.android.com/reference/android/app/Activity#onActivityResult(int,%20int,%20android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 && resultCode == RESULT_OK && data != null){

            // Data ophalen uit de intent.
            String name = data.getStringExtra("name");
            String description = data.getStringExtra("description");
            int quantity = Integer.parseInt(data.getStringExtra("quantity"));
            String start_date = data.getStringExtra("start_date");
            String end_date = data.getStringExtra("end_date");
            String extra_information = data.getStringExtra("extra_information");

            // Aanmaken van een medication instantie.
            Medication medication = new Medication(name, description, quantity, start_date, end_date, extra_information);

            // Medication instantie opslaan in de database via de repository.
            long medicationID = databaseRepository.insertMedication(medication);

            // Bij het aanmaken van een Medication wordt op basis van de dagen tussen de start / eind datum een status aangemaakt.
            // Bijvoorbeeld: 3 pillen per dag -> 3 statusses per dag.
            try {
                StatusHelper.createStatuses(start_date, end_date, quantity, medicationID, databaseRepository);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Herladen van de lijst van 'Medications'.
            showMedicationList();
        }
    }

    /*
        Methode voor het tonen van de Medication items in de 'MedicationList'.
        De gewone adapter is vervangen door een custom adapter.

        Listview, voeg elementen toe. ArrayAdapter. Geraadpleegd op 5/11/2020
        https://stackoverflow.com/questions/4540754/how-do-you-dynamically-add-elements-to-a-listview-on-android
        https://www.tutorialspoint.com/dynamically-add-elements-in-listview-in-android
        https://developer.android.com/reference/android/widget/ArrayAdapter
     */
    public void showMedicationList(){
        ArrayList<Medication> medicationList = new ArrayList<>();
        for (Medication medication : databaseRepository.getAllMedication()){
            medicationList.add(medication);
        }

        // Custom adapter.
        // Medication list, MainActivity(this) & repository worden meegegeven zodat de adapter aan de methodes kan.
        final CustomMedicationAdapter adapter = new CustomMedicationAdapter(medicationList, this, databaseRepository);
        showMedication.setAdapter(adapter);
    }
}