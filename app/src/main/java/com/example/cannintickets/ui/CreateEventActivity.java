package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cannintickets.R;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateEventActivity extends AppCompatActivity {
    EditText name_input, location_input, description_input;
    DatePicker date_picker;
    TimePicker time_picker;
    Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_event);

        name_input = findViewById(R.id.name_input);
        location_input = findViewById(R.id.location_input);
        description_input = findViewById(R.id.description_input);
        date_picker = findViewById(R.id.date_picker);
        time_picker = findViewById(R.id.time_picker);
        next_button = findViewById(R.id.next_button);

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

            Intent intent = new Intent(this, CreateEventActivity2.class);
            intent.putExtra("name", name);
            intent.putExtra("location", location);
            intent.putExtra("description", description);

            intent.putExtra("date", dateTimeString);

            startActivity(intent);
        });
    }
}
