package com.example.medication_reminder.helper;

import com.example.medication_reminder.entity.Medication;

import java.text.ParseException;

public interface FragmentListener {
    void updateMedication(Medication medication) throws ParseException;

    int getMedicationId();
}
