package com.example.medication_reminder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

public class DetailsMedicationFragment extends Fragment{

    private EditText update_input_name, update_input_description, update_input_quantity, update_input_start_date, update_input_end_date, update_input_extra_information;
    private Button update_medication_button;
    private DetailsMedicationFragmentListener callBack;
    private String name, description, quantity, start_date, end_date, extra_information;
    public int ID;
    private DetailMedicationActivity detailMedicationActivity;
    private DatabaseRepository databaseRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Linken van de view met een bepaalde layout(fragment aparte layout geven).
        View view = inflater.inflate(R.layout.detailsmedicationfragment, container, false);

        // detailMedicationActivity oproepen voor de id van een medication & de repository op te halen.
        detailMedicationActivity = (DetailMedicationActivity)getActivity();

        // ID ophalen van de activity
        ID = detailMedicationActivity.ID;

        // repository ophalen van de activity
        databaseRepository = new DatabaseRepository(detailMedicationActivity.getApplication());

        // Medication ophalen op basis van id.
        Medication medication = databaseRepository.getMedicationById(ID);

        // Ophalen van de id's van de input fields.
        update_input_name = view.findViewById(R.id.update_input_name);
        update_input_description = view.findViewById(R.id.update_input_description);
        update_input_quantity = view.findViewById(R.id.update_input_quantity);
        update_input_start_date = view.findViewById(R.id.update_input_start_date);
        update_input_end_date = view.findViewById(R.id.update_input_end_date);
        update_input_extra_information = view.findViewById(R.id.update_input_extra_information);
        update_medication_button = view.findViewById(R.id.update_medication_button);


        // onclick listener.
        update_medication_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Data van de textfields ophalen.
                getDataFromTextFields();

                // Nieuwe medication aanmaken.
                Medication medication = new Medication(ID, name, description, Integer.parseInt(quantity), start_date, end_date, extra_information);

                // Functie aanroepen van de interface (update van de medication).
                callBack.updateMedication(medication);
            }
        });

        // Alle fields de nieuwe waarde geven.
        setTextFieldsInFragment(medication);

        return view;
    }

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

    // interface voor het updaten van de medication.
    public interface DetailsMedicationFragmentListener {
        void updateMedication(Medication medication);
    }

    // controle zodat de listener altijd aangemaakt is (interface)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callBack = (DetailsMedicationFragmentListener)context;
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
}