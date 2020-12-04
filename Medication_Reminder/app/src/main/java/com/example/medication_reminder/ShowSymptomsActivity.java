package com.example.medication_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ShowSymptomsActivity extends AppCompatActivity{

    private ListView show_symptoms;
    private String medication_name, URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Medication id ophalen uit de CustomAdapter.
        Intent intent = getIntent();
        medication_name = intent.getStringExtra("medicationName");

        setContentView(R.layout.activity_showsymptoms);

        //URL maken voor de api call. Url van api: https://www.ehealthme.com/api/v1/docs/
        URL = "https://www.ehealthme.com/api/v1/ds/" + medication_name.toLowerCase() + "/weakness";

        makeApiCallAsync(URL);
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
        RequestQueue queue = Volley.newRequestQueue(this);

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
                    show_symptoms = findViewById(R.id.show_symptoms);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowSymptomsActivity.this, android.R.layout.simple_list_item_1, symptomList);
                    show_symptoms.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Array adapter
                show_symptoms = findViewById(R.id.show_symptoms);
                ArrayList<String> symptomList = new ArrayList<>();
                symptomList.add("No symptoms found!");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowSymptomsActivity.this, android.R.layout.simple_list_item_1, symptomList);
                show_symptoms.setAdapter(adapter);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
