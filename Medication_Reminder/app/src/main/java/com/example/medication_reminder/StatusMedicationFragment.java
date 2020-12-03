package com.example.medication_reminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class StatusMedicationFragment extends Fragment {

    private ListView showStatuses;
    private Button close_fragment3;
    private DetailMedicationActivity detailMedicationActivity;
    private DatabaseRepository databaseRepository;
    public int ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Linken van de view met een bepaalde layout(fragment aparte layout geven).
        View view = inflater.inflate(R.layout.statusmedicationfragment, container, false);

        // detailMedicationActivity oproepen voor de id van een medication & de repository op te halen.
        detailMedicationActivity = (DetailMedicationActivity)getActivity();

        // ID ophalen van de activity
        ID = detailMedicationActivity.ID;

        // repository ophalen van de activity
        databaseRepository = new DatabaseRepository(detailMedicationActivity.getApplication());

        // Medication ophalen op basis van id.
        Medication medication = databaseRepository.getMedicationById(ID);

        // Ophalen id's
        showStatuses = view.findViewById(R.id.show_status_list);
        close_fragment3 = view.findViewById(R.id.close_fragment3);

        showStatusList();

        // Listener
        close_fragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailMedicationActivity.destroyFragment();
            }
        });

        return view;
    }

    /*
    Methode voor het tonen van de Status items in de 'StatusList'.
    De gewone adapter is vervangen door een custom adapter.

    Listview, voeg elementen toe. ArrayAdapter. Geraadpleegd op 01/12/2020
    https://stackoverflow.com/questions/4540754/how-do-you-dynamically-add-elements-to-a-listview-on-android
    https://www.tutorialspoint.com/dynamically-add-elements-in-listview-in-android
    https://developer.android.com/reference/android/widget/ArrayAdapter
 */
    public void showStatusList(){
        ArrayList<Status> statusList = new ArrayList<>();
        for (Status status : databaseRepository.getAllStatuses(ID)){
            statusList.add(status);
        }

        // Custom adapter.
        // Status list, detailMedicationActivity(this) & repository worden meegegeven zodat de adapter aan de methodes kan.
        final CustomStatusAdapter adapter = new CustomStatusAdapter(statusList, detailMedicationActivity, databaseRepository);
        showStatuses.setAdapter(adapter);
    }
}
