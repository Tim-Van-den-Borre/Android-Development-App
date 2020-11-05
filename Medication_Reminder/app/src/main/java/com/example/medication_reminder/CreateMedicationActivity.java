package com.example.medication_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateMedicationActivity extends AppCompatActivity {

    private Button saveMedication;
    private EditText input_name, input_description, input_quantity, input_start_date, input_end_date, input_extra_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmedication);

        // Id's ophalen van de input fields.
        saveMedication = findViewById(R.id.save_medication_button);
        input_name = findViewById(R.id.input_name);
        input_description = findViewById(R.id.input_description);
        input_quantity = findViewById(R.id.input_quantity);
        input_start_date = findViewById(R.id.input_start_date);
        input_end_date = findViewById(R.id.input_end_date);
        input_extra_information = findViewById(R.id.input_extra_information);

        // Na klikken op 'save' de Medication opslaan.
        saveMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedication();
            }
        });
    }
    /*
        Methode voor het opslaan van de medication in de database.
     */
    public void saveMedication(){
        String name, description, quantity, start_date, end_date, extra_information;

        // Text values ophalen van alle fields.
        name = input_name.getText().toString();
        description = input_description.getText().toString();
        quantity = input_quantity.getText().toString();
        start_date = input_start_date.getText().toString();
        end_date = input_end_date.getText().toString();
        extra_information = input_extra_information.getText().toString();

        /*
            Geef waardes terug met de intent. Geraadpleegd op 4/11/2020.
            https://www.javatpoint.com/android-startactivityforresult-example
         */
        // Aanmaken intent & waardes meegeven.
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("description", description);
        intent.putExtra("quantity", quantity);
        intent.putExtra("start_date", start_date);
        intent.putExtra("end_date", end_date);
        intent.putExtra("extra_information", extra_information);

        // Toevoegen result code & intent.
        setResult(RESULT_OK, intent);

        // Response terug sturen naar onActivityResult.
        finish();
    }
}
