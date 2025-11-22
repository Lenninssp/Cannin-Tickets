package com.example.cannintickets.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.controllers.usertickets.GetUserTicketsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;

import java.util.ArrayList;
import java.util.List;

public class UserTicketActivity extends BaseActivity {


    RecyclerView recyclerView;
    UserTicketAdapter adapter;

    List<UserTicketsResponseModel> tickets = new ArrayList<>();

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_user_ticket);

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);


        adapter = new UserTicketAdapter(tickets);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTickets();


    }


    private void loadTickets() {


        GetUserTicketsController endpoint = new GetUserTicketsController();
        endpoint.GET(null).thenAccept(ticketList -> {
            runOnUiThread(() -> {
                tickets.clear();
                tickets.addAll(ticketList);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            });
        });



    }
}