package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.MainActivity;
import com.example.cannintickets.R;
import com.example.cannintickets.controllers.tickets.CreateTicketController;
import com.example.cannintickets.controllers.tickets.DeleteTicketController;
import com.example.cannintickets.controllers.tickets.GetTicketsController;
import com.example.cannintickets.entities.ticket.TicketName;
import com.example.cannintickets.models.tickets.create.CreateTicketRequestModel;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketsActivity extends AppCompatActivity {


    private Spinner ticketNameSpinner;
    private EditText ticketPrice;
    private EditText ticketQuantity;
    private Button createTicketButton;
    private Button finishButton;

    private RecyclerView recyclerView;
    private TicketSellerAdapter adapter;

    private String eventName;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_tickets);

        ticketNameSpinner = findViewById(R.id.spinner_ticket);
        ticketPrice       = findViewById(R.id.price_input);
        ticketQuantity    = findViewById(R.id.quantity_input);
        createTicketButton = findViewById(R.id.create_ticket);
        finishButton      = findViewById(R.id.finish_button);
        recyclerView      = findViewById(R.id.recyclerView);

        eventName = getIntent().getStringExtra("eventName");
        eventId   = getIntent().getStringExtra("eventId");

        if (eventId == null) {
            Toast.makeText(this, "eventId is missing", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<TicketName> spinnerAdapter =
                new ArrayAdapter<>(this, R.layout.spinner_item, TicketName.values());
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ticketNameSpinner.setAdapter(spinnerAdapter);

        adapter = new TicketSellerAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter = new TicketSellerAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener((ticket, position) -> {
            DeleteTicketController endpoint = new DeleteTicketController();
            endpoint.DELETE(ticket.getId()).thenAccept(response -> {
                if (response.isSuccess()) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Ticket deleted", Toast.LENGTH_SHORT).show();
                    });
                }


            });
            adapter.removeTicketAt(position);
        });


        if (eventId != null) {
            loadTickets();
        }

        createTicketButton.setOnClickListener(view -> {
            if (eventId == null) {
                Toast.makeText(this, "Cannot create ticket: eventId is null", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = ticketNameSpinner.getSelectedItem().toString();

            int price;
            int quantity;

            try {
                price = Integer.parseInt(ticketPrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                quantity = Integer.parseInt(ticketQuantity.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            CreateTicketController endpoint = new CreateTicketController();
            CreateTicketRequestModel requestModel = new CreateTicketRequestModel(
                    name,
                    eventId,
                    quantity,
                    price

            );

            endpoint.POST(requestModel).thenAccept(response -> {
                if (response.isSuccess()) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Ticket created", Toast.LENGTH_SHORT).show();
                        loadTickets(); // Refresh list
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Failed to create ticket", Toast.LENGTH_SHORT).show()
                    );
                }
            }).exceptionally(throwable -> {
                runOnUiThread(() ->
                        Toast.makeText(this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
                return null;
            });
        });

        finishButton.setOnClickListener(
                view -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }

    private void loadTickets() {
        if (eventId == null) return;

        GetTicketsController endpoint = new GetTicketsController();
        endpoint.GET(eventId).thenAccept(ticketList -> {
            runOnUiThread(() -> {
                List<GetTicketResponseModel> safeList =
                        (ticketList != null) ? ticketList : new ArrayList<>();

                adapter.setTickets(safeList);
            });
        }).exceptionally(throwable -> {
            runOnUiThread(() ->
                    Toast.makeText(this, "Error loading tickets: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
            );
            return null;
        });
    }
}
