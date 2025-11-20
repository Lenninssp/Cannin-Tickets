package com.example.cannintickets.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.controllers.tickets.GetTicketsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;

import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends AppCompatActivity {

    TicketAdapter adapter;
    RecyclerView recyclerView;
    TextView eventName;
    TextView eventDescription;
    TextView eventDate;
    TextView eventLocation;
    List<GetTicketResponseModel> tickets = new ArrayList<>();
    String eventId;
    Button checkoutButton;
    Double total;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_detail);

        adapter = new TicketAdapter(tickets);


        eventId = getIntent().getStringExtra("eventId");


        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("date");



        eventName = findViewById(R.id.event_name);
        eventDescription = findViewById(R.id.event_description);
        eventDate = findViewById(R.id.event_date);
        eventLocation = findViewById(R.id.event_location);
        checkoutButton = findViewById(R.id.checkout_button);



        eventName.setText(name);
        eventDescription.setText(description);
        eventDate.setText(date);
        eventLocation.setText(location);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadTickets();

        checkoutButton.setOnClickListener(view -> {
            List<GetTicketResponseModel> ticketList = adapter.getTickets();
            List<Integer> quantityList = adapter.getQuantities();

            total = 0.0;

            for (int i = 0; i < ticketList.size(); i++) {
                GetTicketResponseModel ticket = ticketList.get(i);
                int qty = quantityList.get(i);

                if (qty > 0) {
                    String ticketId = ticket.getId();
                    double price = ticket.getPrice();

                    total += price * qty;

                    System.out.println("ticketId: " + ticketId + " qty: " + qty);
                }
            }

            System.out.println("TOTAL: " + total);

            // TODO: now you can send total + selected tickets to your order use case
        });



    }

    private void loadTickets() {
        GetTicketsController endpoint = new GetTicketsController();
        endpoint.GET(eventId).thenAccept(ticketList -> {

                adapter.setTickets(ticketList);


        });
    }

}