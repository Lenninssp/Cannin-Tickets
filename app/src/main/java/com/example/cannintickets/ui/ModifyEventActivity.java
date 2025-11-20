package com.example.cannintickets.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;

public class ModifyEventActivity extends AppCompatActivity {

    String eventId;
    String eventName;
    String eventDescription;
    String eventDate;
    String eventLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify_event);

        eventId = getIntent().getStringExtra("event_id");
        eventName = getIntent().getStringExtra("event_name");
        eventDescription = getIntent().getStringExtra("event_description");
        eventDate = getIntent().getStringExtra("event_date");
        eventLocation = getIntent().getStringExtra("event_location");




    }
}