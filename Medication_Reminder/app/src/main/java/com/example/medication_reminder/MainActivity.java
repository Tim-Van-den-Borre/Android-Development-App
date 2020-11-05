package com.example.medication_reminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addMedication;
    private ListView showMedication;
    private DatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nieuwe instantie van de repository.
        databaseRepository = new DatabaseRepository(getApplication());

        addMedication = findViewById(R.id.add_Medication_button);

        showMedication = findViewById(R.id.show_medication);

        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMedication();
            }
        });

        showMedicationList();
    }
    /*
        startActivityForResult. Geraadpleegd op 4/11/2020
        https://developer.android.com/training/basics/intents/result
        https://developer.android.com/reference/android/app/Activity#startActivityForResult(android.content.Intent,%20int)
        https://stuff.mit.edu/afs/sipb/project/android/docs/training/basics/intents/result.html
        Aanmaken van intent voor navigatie naar CreateMedicationActivity. Krijgt een intent & requestcode mee(200 voor OK).
     */
    public void addMedication(){
        Intent intent = new Intent(this, CreateMedicationActivity.class);
        startActivityForResult(intent, 200);
    }
    /*
        onActivityResult. Geraadpleegd op 4/11/2020.
        https://developer.android.com/reference/android/app/Activity#onActivityResult(int,%20int,%20android.content.Intent)
        override deze methode. Het is de response op de startActivityForResult.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 && resultCode == RESULT_OK && data != null){

            // data ophalen uit de intent.
            String name = data.getStringExtra("name");
            String description = data.getStringExtra("description");
            int quantity = Integer.parseInt(data.getStringExtra("quantity"));
            String start_date = data.getStringExtra("start_date");
            String end_date = data.getStringExtra("end_date");
            String extra_information = data.getStringExtra("extra_information");

            // logs
            Log.e("name", name);
            Log.e("description", description);
            Log.e("quantity", quantity + "");
            Log.e("start_date", start_date);
            Log.e("end_date", end_date);
            Log.e("extra_information", extra_information);

            // aanmaken van een medication.
            Medication medication = new Medication(name, description, quantity, start_date, end_date, extra_information);

            // medication in database zetten via de repository.
            databaseRepository.insertMedication(medication);

            // toon de medication list.
            showMedicationList();
        }
    }

    /*
        Listview, voeg elementen toe. ArrayAdapter. Geraadpleegd op 5/11/2020
        https://stackoverflow.com/questions/4540754/how-do-you-dynamically-add-elements-to-a-listview-on-android
        https://www.tutorialspoint.com/dynamically-add-elements-in-listview-in-android
        https://developer.android.com/reference/android/widget/ArrayAdapter

        Vervangen door custom adapter.
     */
    public void showMedicationList(){
        ArrayList<Medication> medicationList = new ArrayList<>();
        for (Medication medication : databaseRepository.getAllMedication()){
            medicationList.add(medication);
        }

        // adapter
        // context mainactivity meegeven(this). Zodat we in de adapter aan de methodes kunnen van de MainActivity.
        final CustomMedicationAdapter adapter = new CustomMedicationAdapter(medicationList, this, databaseRepository);
        showMedication.setAdapter(adapter);
    }
}