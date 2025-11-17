package com.example.cannintickets.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.tickets.CreateTicketController;
import com.example.cannintickets.models.tickets.create.CreateTicketRequestModel;

public class TicketActivity extends AppCompatActivity {

    Button create, delete, modify, get;
    EditText eventId;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket);

        create = findViewById(R.id.create_ticket);
        delete = findViewById(R.id.delete_ticket);
        modify = findViewById(R.id.modify_ticket);
        get = findViewById(R.id.get_ticket);

        eventId = findViewById(R.id.event_id_text);
        textView = findViewById(R.id.tickets_display);

        


        create.setOnClickListener(V -> {
            CreateTicketController endoint = new CreateTicketController();
            endoint.POST(
                    new CreateTicketRequestModel(
                            "GA",
                            eventId.getText().toString(),
                            1000,
                            100.0
                    )
            ).thenApply(ticket -> {
                if (ticket.isSuccess()) {
                    Toast.makeText(this, "The ticket was created successfully", Toast.LENGTH_SHORT).show();
                    Log.d("tickets", "The event was created successfully");
                }
                else {
                    Toast.makeText(this, ticket.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tickets", ticket.getMessage());
                }
                return ticket;
            });

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}