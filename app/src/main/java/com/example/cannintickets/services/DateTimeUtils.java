package com.example.cannintickets.services;

import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTimeUtils {

    /**
     * Populates a DatePicker and TimePicker from an ISO date string.
     * Expected format: "YYYY-MM-DDTHH:MM"
     */
    public static void setDateAndTime(String isoDateTime,
                                      DatePicker datePicker,
                                      TimePicker timePicker) {

        if (isoDateTime == null || !isoDateTime.contains("T")) {
            return; // invalid format
        }

        try {
            // Split into date + time
            String[] dateTimeParts = isoDateTime.split("T");
            String datePart = dateTimeParts[0];     // e.g. "2025-11-28"
            String timePart = dateTimeParts[1];     // e.g. "05:25"

            // Parse date
            String[] dateParts = datePart.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1; // DatePicker uses 0–11
            int day = Integer.parseInt(dateParts[2]);

            // Parse time
            String[] timeParts = timePart.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            // Apply to pickers
            datePicker.updateDate(year, month, day);
            timePicker.setHour(hour);
            timePicker.setMinute(minute);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
