package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;

import java.util.ArrayList;
import java.util.List;

public class BuyerEventsActivity extends BaseActivity {


    RecyclerView recyclerView;
    EventAdapter adapter;

    List<GetEventResponseModel> events = new ArrayList<>();

    private ProgressBar progressBar;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_buyer_events);

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);



        adapter = new EventAdapter(events);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();

        adapter.setOnItemClickListener(position -> {
            GetEventResponseModel clicked = events.get(position);

            Intent intent = new Intent(this, EventDetailActivity.class);
            intent.putExtra("eventId", clicked.getId());
            intent.putExtra("name", clicked.getName());
            intent.putExtra("description", clicked.getDescription());
            intent.putExtra("location", clicked.getLocation());
            intent.putExtra("date", clicked.getEventDate());

            startActivity(intent);
        });
    }

    private void loadEvents() {
        GetEventsController endpoint = new GetEventsController();
        endpoint.GET(false).thenAccept(eventList -> {

                events.clear();
                events.addAll(eventList);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);




        });


    }


}