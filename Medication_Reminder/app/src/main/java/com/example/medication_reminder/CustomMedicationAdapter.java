package com.example.medication_reminder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
/*
    Custom adapter. Geraadpleegd 5/11/2020
    Normale adapter ondersteunt gewoon 1 string. Deze adapter laat toe om objecten mee te geven
    alsook een custom layout (medication_list_item.xml)
    https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */
public class CustomMedicationAdapter extends ArrayAdapter<Medication>{

    // lijst van medications
    private ArrayList<Medication> dataSet;

    // instantie van repository voor adapter.
    private DatabaseRepository repository;

    // context van de applicatie.
    Context context;

    // items voor in list (krijgen nadien layout).
    private static class ViewHolder {
        TextView medicationname;
        Button detailButton;
        Button deleteButton;
    }

    public CustomMedicationAdapter(ArrayList<Medication> data, Context context, DatabaseRepository repository) {
        super(context, R.layout.medication_list_item, data);
        this.dataSet = data;
        this.context = context;
        this.repository = repository;
    }

    /*
        Elk element van de medication list toevoegen + layout aan geven.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Medication medication = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.medication_list_item, parent, false);
            viewHolder.medicationname = (TextView)convertView.findViewById(R.id.Medication_Name);
            viewHolder.detailButton = (Button)convertView.findViewById(R.id.Medication_Details_Button);
            viewHolder.deleteButton = (Button)convertView.findViewById(R.id.Medication_Delete_Button);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.medicationname.setText(medication.name);
        viewHolder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("name", medication.name + "details");
            }
        });
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.deleteMedication(medication);

                // Parsen van context naar de MainActivity zodat we aan de methode kunnen.
                ((MainActivity)context).showMedicationList();
            }
        });
        return convertView;
    }
}
