package com.example.medication_reminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
    Helper class zodat de methode 'createStatuses' static aangeroepen kan worden.
 */
public class StatusHelper {
    public static void createStatuses(String start_date, String end_date, int quantity, long medicationID, DatabaseRepository repo) throws ParseException {
        ArrayList<Date> dates = new ArrayList<>();

        // Start / eind datum.
        Date start = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse(end_date);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(start);
        cal1.add(Calendar.DATE, -1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        cal2.add(Calendar.DATE, -1);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);

            String date = dateFormat.format(cal1.getTime());

            for(int i = 0; i < quantity; i++){
                int amount = i + 1;
                Status status = new Status(date + " - " + amount, false, medicationID);
                repo.insertStatus(status);
            }
        }
    }
}
