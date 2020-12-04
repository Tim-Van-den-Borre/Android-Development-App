package com.example.medication_reminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.example.medication_reminder.R;
import com.example.medication_reminder.entity.Status;
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
public class CustomStatusAdapter extends ArrayAdapter<Status> {

    // Lijst voor status items.
    private ArrayList<Status> statuslist;

    // Instantie DatabaseRepository
    private DatabaseRepository repository;

    // Contect van de applicatie.
    Context context;

    // Items voor in list (krijgen nadien layout).
    private static class ViewHolder {
        TextView date;
        CheckBox checkBox;
    }

    public CustomStatusAdapter(ArrayList<Status> data, Context context, DatabaseRepository repository) {
        super(context, R.layout.status_list_item, data);
        this.statuslist = data;
        this.context = context;
        this.repository = repository;
    }

    /*
        Elk element van de status list toevoegen + layout aan geven met 'status_list_item.xml'.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Status status = getItem(position);

        // Bevat een textview, details button en delete button.
        CustomStatusAdapter.ViewHolder viewHolder;

        final View result;

        if (view == null) {
            // Nieuw item -> viewHolder.
            viewHolder = new CustomStatusAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.status_list_item, parent, false);

            // Ophalen id's van elementen in status_list_item.
            viewHolder.date = (TextView)view.findViewById(R.id.Status_Date);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.Status_Checkbox);

            result = view;
            view.setTag(viewHolder);
        } else {
            viewHolder = (CustomStatusAdapter.ViewHolder)view.getTag();
            result = view;
        }

        // Text van item.
        viewHolder.date.setText(status.date.toString());
        viewHolder.checkBox.setChecked(status.checked);

        // update de checkbox
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                repository.updateStatusById(isChecked, status.id);
            }
        });
        return view;
    }
}
