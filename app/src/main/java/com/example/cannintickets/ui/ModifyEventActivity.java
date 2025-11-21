package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.ModifyEventController;
import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;
import com.example.cannintickets.services.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModifyEventActivity extends AppCompatActivity {

    String eventId;
    String eventName;
    String eventDescription;
    String eventDate;
    String eventLocation;

    Boolean isPrivate;

    EditText name_input, location_input, description_input;
    DatePicker date_picker;
    TimePicker time_picker;
    Button next_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify_event);

        name_input = findViewById(R.id.name_input);
        location_input = findViewById(R.id.location_input);
        description_input = findViewById(R.id.description_input);
        date_picker = findViewById(R.id.date_picker);
        time_picker = findViewById(R.id.time_picker);
        next_button = findViewById(R.id.next_button);

        eventId = getIntent().getStringExtra("event_id");
        eventName = getIntent().getStringExtra("event_name");
        eventDescription = getIntent().getStringExtra("event_description");
        eventDate = getIntent().getStringExtra("event_date");
        eventLocation = getIntent().getStringExtra("event_location");
        isPrivate = getIntent().getBooleanExtra("event_is_private", false);







        name_input.setText(eventName);
        location_input.setText(eventLocation);
        description_input.setText(eventDescription);
        DateTimeUtils.setDateAndTime(eventDate, date_picker, time_picker);
        time_picker.setIs24HourView(true);


        next_button.setOnClickListener(v -> {
            String name = name_input.getText().toString();
            String location = location_input.getText().toString();
            String description = description_input.getText().toString();

            if (name.isEmpty() || location.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int year = date_picker.getYear();
            int month = date_picker.getMonth() + 1;
            int day = date_picker.getDayOfMonth();

            LocalDate date = LocalDate.of(year, month, day);

            int hour;
            int minute;
            hour = time_picker.getHour();
            minute = time_picker.getMinute();


            LocalDateTime dateTime = date.atTime(hour, minute);
            String dateTimeString = dateTime.toString();

            ModifyEventController modifyEventController = new ModifyEventController();
            modifyEventController.POST(
                    new ModifyEventRequestModel(
                            eventId,
                            name,
                            location,
                            description,
                            dateTimeString,
                            isPrivate)).thenApply(response -> {
                        if (response.isSuccess()) {
                            Toast.makeText(this, "Event modified successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, CreateTicketsActivity.class);
                            intent.putExtra("eventId", eventId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Error modifying event", Toast.LENGTH_SHORT).show();
                        }
                        return null;
                    });
        });










    }
}