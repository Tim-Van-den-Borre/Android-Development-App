package com.example.medication_reminder;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.medication_reminder", appContext.getPackageName());
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