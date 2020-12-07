package com.example.medication_reminder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.example.medication_reminder.database.DatabaseRepository;
import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.helper.FragmentListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/*
    2 fragments in 1 activity voorbeeld. Geraadpleegd op 8/11/2020
    https://devcfgc.com/two-fragments-in-one-activity-278b5ee45ae9
 */
public class DetailsMedicationFragment extends Fragment{

    private EditText update_input_name, update_input_description, update_input_quantity, update_input_start_date, update_input_end_date, update_input_extra_information;
    private Button update_medication_button;
    private FragmentListener callBack;
    private String name, description, quantity, start_date, end_date, extra_information;
    public int ID;
    private DetailMedicationActivity detailMedicationActivity;
    private DatabaseRepository databaseRepository;

    private Button Medication_Status_Button;

    // Aanmaken calendar voor verplicht datum te kiezen uit een data picker.
    final Calendar start_date_calendar = Calendar.getInstance();
    final Calendar end_date_calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Linken van de view met een bepaalde layout(fragment aparte layout geven).
        View view = inflater.inflate(R.layout.fragment_details_medication, container, false);

        // detailMedicationActivity oproepen voor de id van een medication & de repository op te halen.
        detailMedicationActivity = (DetailMedicationActivity)getActivity();

        // ID ophalen van de activity
        ID = callBack.getMedicationId();

        // repository ophalen van de activity
        databaseRepository = new DatabaseRepository(detailMedicationActivity.getApplication());

        // Medication ophalen op basis van id.
        Medication medication = databaseRepository.getMedicationById(ID);

        // Ophalen id's.
        update_input_name = view.findViewById(R.id.update_input_name);
        update_input_description = view.findViewById(R.id.update_input_description);
        update_input_quantity = view.findViewById(R.id.update_input_quantity);
        update_input_start_date = view.findViewById(R.id.update_input_start_date);
        update_input_end_date = view.findViewById(R.id.update_input_end_date);
        update_input_extra_information = view.findViewById(R.id.update_input_extra_information);
        update_medication_button = view.findViewById(R.id.update_medication_button);
        Medication_Status_Button = view.findViewById(R.id.update_status_medication_button);

        // Alle fields de nieuwe waarde geven.
        setTextFieldsInFragment(medication);

        // Listeners
        Medication_Status_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailMedicationActivity.showStatusFragment();
            }
        });

        update_input_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Datepicker tonen als je op textview klikt.
                new DatePickerDialog(detailMedicationActivity,
                        update_start_date,
                        start_date_calendar.get(Calendar.YEAR),
                        start_date_calendar.get(Calendar.MONTH),
                        start_date_calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        update_input_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Datepicker tonen als je op textview klikt.
                new DatePickerDialog(detailMedicationActivity,
                        update_end_date,
                        end_date_calendar.get(Calendar.YEAR),
                        end_date_calendar.get(Calendar.MONTH),
                        end_date_calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        return view;
    }

    /*
        Methode voor het controleren van input fields.
        Controleer of de input van de fields leeg is -> Error message. Geraadpleegd op 06/12/2020
        https://stackoverflow.com/questions/18225365/show-error-on-the-tip-of-the-edit-text-android
    */
    public boolean checkInput(){
        boolean check = true;
        List<EditText> list = new ArrayList<>();

        list.add(update_input_name);
        list.add(update_input_description);
        list.add(update_input_quantity);
        list.add(update_input_start_date);
        list.add(update_input_end_date);
        list.add(update_input_extra_information);

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
    // Invullen van datum in textview
    DatePickerDialog.OnDateSetListener update_start_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            start_date_calendar.set(Calendar.YEAR, year);
            start_date_calendar.set(Calendar.MONTH, monthOfYear);
            start_date_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Date format kiezen & als text in textview zetten.
            String date_format = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(date_format, Locale.getDefault());
            update_input_start_date.setText(sdf.format(start_date_calendar.getTime()));
        }
    };

    // Invullen van datum in textview
    DatePickerDialog.OnDateSetListener update_end_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            end_date_calendar.set(Calendar.YEAR, year);
            end_date_calendar.set(Calendar.MONTH, monthOfYear);
            end_date_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Date format kiezen & als text in textview zetten.
            String date_format = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(date_format, Locale.getDefault());

            update_input_end_date.setText(sdf.format(end_date_calendar.getTime()));
        }
    };

    /*
        Methode voor het ophalen van de data uit de textfields.
     */
    public void getDataFromTextFields(){
        // Ophalen string content.
        name = update_input_name.getText().toString();
        description = update_input_description.getText().toString();
        quantity = update_input_quantity.getText().toString();
        start_date = update_input_start_date.getText().toString();
        end_date = update_input_end_date.getText().toString();
        extra_information = update_input_extra_information.getText().toString();
    }

    // controle zodat de listener altijd aangemaakt is (interface)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callBack = (FragmentListener)context;
        }catch (ClassCastException e){
            Log.e("Error", e.getMessage());
        }
    }

    /*
        Methode voor de fields van de fragment de nieuwe aangepaste waarde te geven.
     */
    public void setTextFieldsInFragment(Medication medication){
        update_input_name.setText(medication.name);
        update_input_description.setText(medication.description);
        update_input_quantity.setText(medication.quantity + "");
        update_input_start_date.setText(medication.start_date);
        update_input_end_date.setText(medication.end_date);
        update_input_extra_information.setText(medication.extra_info);
    }

    public void saveMedication(){
        // Data van de textfields ophalen.
        getDataFromTextFields();

        // Functie aanroepen van de interface (update van de medication).
        if(checkInput()){
            // Nieuwe medication aanmaken.
            Medication medication = new Medication(ID, name, description, Integer.parseInt(quantity), start_date, end_date, extra_information);
            try {
                callBack.updateMedication(medication);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}