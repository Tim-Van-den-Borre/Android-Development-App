package com.example.medication_reminder;

import com.example.medication_reminder.entity.Medication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void createMedication(){
        int id, quantity;
        String name, description, start_date, end_date, extra_info;

        id = 1;
        name = "Dafalgan";
        description = "Pijnstiller";
        start_date = "12/11/2020";
        end_date = "22/11/2020";
        quantity = 30;
        extra_info = "Nothing to mention.";
        Medication medication = new Medication(id, name, description, quantity, start_date, end_date, extra_info);

        assertEquals("Dafalgan", medication.name);
    }
}