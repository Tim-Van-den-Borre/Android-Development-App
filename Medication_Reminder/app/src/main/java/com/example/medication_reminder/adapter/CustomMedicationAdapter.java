package com.example.medication_reminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.medication_reminder.DetailMedicationActivity;
import com.example.medication_reminder.MainActivity;
import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.R;
import com.example.medication_reminder.ShowSymptomsActivity;
import com.example.medication_reminder.database.DatabaseRepository;

import java.util.ArrayList;
/*
    Custom adapter.
    Normale adapter ondersteunt gewoon 1 string.
    Custom adapter laat toe om objecten / custom layout mee te geven.
    De layout voor de items in de MainActivity is 'medication_list_item.xml'.

    Custom adapter. Geraadpleegd 5/11/2020
    https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */
public class CustomMedicationAdapter extends ArrayAdapter<Medication>{

    // Lijst voor medication items.
    private ArrayList<Medication> medicationlist;

    // Instantie DatabaseRepository
    private DatabaseRepository repository;

    // Contect van de applicatie.
    Context context;

    // Items voor in list (krijgen nadien layout).
    private static class ViewHolder {
        TextView medicationname;
        Button symptomButton;
        Button detailButton;
        Button deleteButton;
    }

    public CustomMedicationAdapter(ArrayList<Medication> data, Context context, DatabaseRepository repository) {
        super(context, R.layout.medication_list_item, data);
        this.medicationlist = data;
        this.context = context;
        this.repository = repository;
    }

    /*
        Elk element van de medication list toevoegen + layout aan geven met 'medication_list_item.xml'.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Medication medication = getItem(position);

        // Bevat een textview, details button en delete button.
        ViewHolder viewHolder;

        final View result;

        if (view == null) {
            // Nieuw item -> viewHolder.
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.medication_list_item, parent, false);

            // Ophalen id's van elementen in medication_list_item.
            viewHolder.medicationname = (TextView)view.findViewById(R.id.Medication_Name);
            viewHolder.symptomButton = (Button)view.findViewById(R.id.Medication_Symptoms_Button);
            viewHolder.detailButton = (Button)view.findViewById(R.id.Medication_Details_Button);
            viewHolder.deleteButton = (Button)view.findViewById(R.id.Medication_Delete_Button);

            result = view;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
            result = view;
        }

        String medicationname = "";
        if(medication.name.length() >= 10){
            medicationname = medication.name.substring(0, 10) + "...";
        }else{
            medicationname = medication.name;
        }
        // Text van item.
        viewHolder.medicationname.setText(medicationname);

        // Bekijk de mogelijke symptomen van de medicatie.
        viewHolder.symptomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowSymptomsActivity.class);
                intent.putExtra("medicationName", medication.name);
                context.startActivity(intent);
            }
        });

        // Bekijk de details van dit item.
        viewHolder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMedicationActivity.class);
                intent.putExtra("medicationID", medication.id);
                context.startActivity(intent);
            }
        });

        // Verwijder het item uit de lijst van medications.
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.deleteStatusById(medication.id);
                repository.deleteMedication(medication);

                // Parsen van context naar de MainActivity zodat we aan de methode kunnen.
                // https://stackoverflow.com/questions/36226932/how-to-call-method-in-another-activity
                ((MainActivity)context).showMedicationList();
            }
        });
        return view;
    }
}
