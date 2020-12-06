package com.example.medication_reminder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateMedicationActivity extends AppCompatActivity {

    private Button saveMedication;
    private EditText input_name, input_description, input_quantity, input_start_date, input_end_date, input_extra_information;

    // Aanmaken calendar voor verplicht datum te kiezen uit een data picker.
    final Calendar start_date_calendar = Calendar.getInstance();
    final Calendar end_date_calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setContentView(R.layout.activity_createmedication);

        // Ophalen id's.
        saveMedication = findViewById(R.id.save_medication_button);
        input_name = findViewById(R.id.input_name);
        input_description = findViewById(R.id.input_description);
        input_quantity = findViewById(R.id.input_quantity);
        input_start_date = findViewById(R.id.input_start_date);
        input_end_date = findViewById(R.id.input_end_date);
        input_extra_information = findViewById(R.id.input_extra_information);

        // Listeners
        saveMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Na klikken op 'save' de Medication opslaan.
                saveMedication();
            }
        });

        input_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Datepicker tonen als je op textview klikt.
                new DatePickerDialog(CreateMedicationActivity.this,
                        start_date,
                        start_date_calendar.get(Calendar.YEAR),
                        start_date_calendar.get(Calendar.MONTH),
                        start_date_calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        input_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Datepicker tonen als je op textview klikt.
                new DatePickerDialog(CreateMedicationActivity.this,
                        end_date,
                        end_date_calendar.get(Calendar.YEAR),
                        end_date_calendar.get(Calendar.MONTH),
                        end_date_calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    /*
        Methode voor het opslaan van de medication in de database.

        Geef waardes terug met de intent. Geraadpleegd op 4/11/2020.
        https://www.javatpoint.com/android-startactivityforresult-example
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

        if(checkInput()){

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

    /*
        Methode voor het controleren van input fields.
        Controleer of de input van de fields leeg is -> Error message. Geraadpleegd op 06/12/2020
        https://stackoverflow.com/questions/18225365/show-error-on-the-tip-of-the-edit-text-android
     */
    public boolean checkInput(){
        boolean check = true;
        List<EditText> list = new ArrayList<>();

        list.add(input_name);
        list.add(input_description);
        list.add(input_quantity);
        list.add(input_start_date);
        list.add(input_end_date);
        list.add(input_extra_information);

        for (EditText item : list) {
            if(item.getText().toString().trim().isEmpty() || item.getText().toString().trim().length() == 0 || item.getText().toString().trim().equals(""))
            {
                item.setError("This field can not be blank");
                check = false;
            }
        }
        return check;
    }

    /*
        Datepicker voor textview voor start en end date. Geraadpleegd op 11/11/2020.
        https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext/29660148
    */

    // Invullen van datum in textview start_date
    DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            start_date_calendar.set(Calendar.YEAR, year);
            start_date_calendar.set(Calendar.MONTH, monthOfYear);
            start_date_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Date format kiezen & als text in textview zetten.
            String date_format = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(date_format, Locale.getDefault());

            input_start_date.setText(sdf.format(start_date_calendar.getTime()));
        }
    };

    // Invullen van datum in textview end_date
    DatePickerDialog.OnDateSetListener end_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            end_date_calendar.set(Calendar.YEAR, year);
            end_date_calendar.set(Calendar.MONTH, monthOfYear);
            end_date_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Date format kiezen & als text in textview zetten.
            String date_format = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(date_format, Locale.getDefault());

            input_end_date.setText(sdf.format(end_date_calendar.getTime()));
        }
    };
}