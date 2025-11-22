package com.example.cannintickets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.usertickets.GetUserTicketsController;
import com.example.cannintickets.controllers.usertickets.ModifyUserTicketController;
import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;

import java.util.ArrayList;
import java.util.List;

public class TicketActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ManageTicketAdapter adapter;

    List<UserTicketsResponseModel> tickets = new ArrayList<>();

    String eventId;
    String eventName;

    TextView eventNameTextView;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket);

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new ManageTicketAdapter(tickets, (ticket, isChecked, position) -> {
            ModifyUserTicketController endpoint = new ModifyUserTicketController();
            endpoint.POST(ticket.getId(), isChecked);

        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventNameTextView = findViewById(R.id.event_name);


        eventId = getIntent().getStringExtra("eventId");

        eventName = getIntent().getStringExtra("eventName");

        eventNameTextView.setText(eventName);


        loadTickets();
    }

    private void loadTickets() {
        GetUserTicketsController endpoint = new GetUserTicketsController();
        endpoint.GET(eventId).thenAccept(ticketList -> {
            runOnUiThread(() -> {
                tickets.clear();
                tickets.addAll(ticketList);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            });
        });
    }
}
