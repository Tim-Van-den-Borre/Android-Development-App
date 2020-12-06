package com.example.medication_reminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.medication_reminder.database.DatabaseRepository;
import com.example.medication_reminder.entity.Medication;

public class ShowMedicationFragment extends Fragment {

    private TextView show_input_name, show_input_description, show_input_quantity, show_input_start_date, show_input_end_date, show_input_extra_information;
    private String name, description, quantity, start_date, end_date, extra_information;
    private DatabaseRepository databaseRepository;
    private int ID;
    private DetailMedicationActivity detailMedicationActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Linken van de view met een bepaalde layout(fragment aparte layout geven).
        View view = inflater.inflate(R.layout.fragment_show_medication, container, false);

        // detailMedicationActivity oproepen voor de id van een medication & de repository op te halen.
        detailMedicationActivity = (DetailMedicationActivity)getActivity();

        // ID ophalen van de activity
        ID = detailMedicationActivity.ID;

        // repository ophalen van de activity
        databaseRepository = new DatabaseRepository(detailMedicationActivity.getApplication());

        // Medication ophalen op basis van id.
        Medication medication = databaseRepository.getMedicationById(ID);

        // Ophalen id's.
        show_input_name = view.findViewById(R.id.show_input_name);
        show_input_description = view.findViewById(R.id.show_input_description);
        show_input_quantity = view.findViewById(R.id.show_input_quantity);
        show_input_start_date = view.findViewById(R.id.show_input_start_date);
        show_input_end_date = view.findViewById(R.id.show_input_end_date);
        show_input_extra_information = view.findViewById(R.id.show_input_extra_information);

        // Ophalen string content.
        name = show_input_name.getText().toString();
        description = show_input_description.getText().toString();
        quantity = show_input_quantity.getText().toString();
        start_date = show_input_start_date.getText().toString();
        end_date = show_input_end_date.getText().toString();
        extra_information = show_input_extra_information.getText().toString();

        // fields invullen bij de showmedication fragment.
        setTextFieldsInFragment(medication);

        return view;
    }

    /*
        String resource ophalen via naam. Geraadpleegd op 11/11/2020
        https://stackoverflow.com/questions/7493287/android-how-do-i-get-string-from-resources-using-its-name
     */
    public void setTextFieldsInFragment(Medication medication){
        show_input_name.setText(getResources().getString(R.string.Medication_Name) + ": " + medication.name);
        show_input_description.setText(getResources().getString(R.string.Medication_Description) + ": " + medication.description);
        show_input_quantity.setText(getResources().getString(R.string.Medication_Quantity) + ": " + medication.quantity);
        show_input_start_date.setText(getResources().getString(R.string.Medication_Start_Date) + ": " + medication.start_date);
        show_input_end_date.setText(getResources().getString(R.string.Medication_End_Date) + ": " + medication.end_date);
        show_input_extra_information.setText(getResources().getString(R.string.Medication_Extra_Info) + ": " + medication.extra_info);
    }
}
