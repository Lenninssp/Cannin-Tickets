package com.example.cannintickets.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.usertickets.GetUserTicketsController;
import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;

import java.util.ArrayList;
import java.util.List;

public class UserTicketsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserTicketAdapter adapter;
    List<UserTicketsResponseModel> tickets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_ticket); // make sure this XML has recycler_view

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new UserTicketAdapter(tickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadTickets();
    }

    private void loadTickets() {
        GetUserTicketsController endpoint = new GetUserTicketsController();


        endpoint.GET(null).thenAccept(ticketList -> {
            runOnUiThread(() -> {
                tickets.clear();
                if (ticketList != null) {

                    tickets.addAll(ticketList);
                }
                adapter.notifyDataSetChanged();
            });
        });
    }
}
