package com.example.medication_reminder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medication_reminder.database.DatabaseRepository;
import com.example.medication_reminder.entity.Medication;
import com.example.medication_reminder.helper.FragmentListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ShowSymptomsFragment extends Fragment {
    private ListView showSymptoms;
    private Button close_fragment4;
    private DetailMedicationActivity detailMedicationActivity;
    private DatabaseRepository databaseRepository;
    private FragmentListener callBack;
    public int ID;
    private ListView show_symptoms;
    private String medication_name, URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Linken van de view met een bepaalde layout(fragment aparte layout geven).
        View view = inflater.inflate(R.layout.fragment_show_symptoms, container, false);

        // detailMedicationActivity oproepen voor de id van een medication & de repository op te halen.
        detailMedicationActivity = (DetailMedicationActivity)getActivity();

        // ID ophalen van de activity
        ID = callBack.getMedicationId();

        // repository ophalen van de activity
        databaseRepository = new DatabaseRepository(detailMedicationActivity.getApplication());

        // Medication ophalen op basis van id.
        Medication medication = databaseRepository.getMedicationById(ID);

        // Ophalen id's
        showSymptoms = view.findViewById(R.id.show_symptoms);
        close_fragment4 = view.findViewById(R.id.close_fragment4);

        // Listener

        close_fragment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailMedicationActivity.destroySymptomFragment();
            }
        });

        //URL maken voor de api call. Url van api: https://www.ehealthme.com/api/v1/docs/
        URL = "https://www.ehealthme.com/api/v1/ds/" + medication.name.toLowerCase() + "/weakness";

        makeApiCallAsync(URL);

        return view;
    }

    /*
     Methode voor het async opvragen van api data.

     Volley, Permissions, conversions, errors. Geraadpleegd op 12/11/2020.
     https://developer.android.com/training/volley/simple
     https://developer.android.com/training/volley
     https://developer.android.com/training/permissions/declaring
     https://stackoverflow.com/questions/56266801/java-net-socketexception-socket-failed-eperm-operation-not-permitted
     https://stackoverflow.com/questions/17037340/converting-jsonarray-to-arraylist
  */
    private void makeApiCallAsync(String URL){
        // Maak een request queue aan.
        RequestQueue queue = Volley.newRequestQueue(detailMedicationActivity);

        // Haal een response van een bepaalde url binnen.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Verwerken van data.
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject top_side_effects = object.getJSONObject("top_side_effects");
                    JSONArray side_effects = top_side_effects.names();

                    ArrayList<String> symptomList = new ArrayList<>();
                    for (int i=0; i < side_effects.length(); i++){
                        symptomList.add(side_effects.getString(i));
                    }

                    // Array adapter
                    show_symptoms = detailMedicationActivity.findViewById(R.id.show_symptoms);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(detailMedicationActivity, android.R.layout.simple_list_item_1, symptomList);
                    show_symptoms.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Array adapter
                show_symptoms = detailMedicationActivity.findViewById(R.id.show_symptoms);
                ArrayList<String> symptomList = new ArrayList<>();
                symptomList.add("No symptoms found!");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(detailMedicationActivity, android.R.layout.simple_list_item_1, symptomList);
                show_symptoms.setAdapter(adapter);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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
}
