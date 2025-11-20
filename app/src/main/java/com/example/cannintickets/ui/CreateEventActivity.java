package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cannintickets.R;

import java.time.LocalDate;

public class CreateEventActivity extends AppCompatActivity {

    EditText name_input, location_input, description_input;
    DatePicker date_picker;
    Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_event);

        name_input = findViewById(R.id.name_input);
        location_input = findViewById(R.id.location_input);
        next_button = findViewById(R.id.next_button);
        date_picker = findViewById(R.id.date_picker);
        description_input = findViewById(R.id.description_input);

        next_button.setOnClickListener(v -> {
            String name = name_input.getText().toString();
            String location = location_input.getText().toString();
            String description = description_input.getText().toString();


            int year = date_picker.getYear();
            int month = date_picker.getMonth() + 1;
            int day = date_picker.getDayOfMonth();

            LocalDate date = LocalDate.of(year, month, day);

            if (name.isEmpty() || location.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, CreateEventActivity2.class);
            intent.putExtra("name", name);
            intent.putExtra("location", location);

            intent.putExtra("date", date.toString());

            intent.putExtra("description", description);
            startActivity(intent);
        });
    }
}
