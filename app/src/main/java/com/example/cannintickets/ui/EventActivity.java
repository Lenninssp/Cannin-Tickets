package com.example.cannintickets.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.CreateEventController;
import com.example.cannintickets.models.events.create.request.CreateEventRequestModel;

import java.io.File;

public class EventActivity extends AppCompatActivity {
    Button createEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event);

        createEvent = findViewById(R.id.create_event);


        createEvent.setOnClickListener(V -> {
            CreateEventController endpoint = new CreateEventController();
            endpoint.POST(
                    new CreateEventRequestModel(
                            "Aviici tribute",
                            "Respects for the death guy",
                            "2027-04-20T18:30",
                            "Any city, Austria",
                            true,
                            new File("empty")
                    )
            ).thenApply(event -> {
                if (event.isSuccessful()){
                    Toast.makeText(this, "The event was created", Toast.LENGTH_SHORT).show();
                    System.out.println("The event creation was successful");
                }
                else {
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(event.getMessage());
                }
                return event;
            });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}