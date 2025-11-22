package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;

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

public class SeeOrdersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SeeOrderAdapter adapter;

    List<GetEventResponseModel> events = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_orders);


        adapter = new SeeOrderAdapter(events);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();

        adapter.setOnItemClickListener(position -> {
            GetEventResponseModel clicked = events.get(position);

            Intent intent = new Intent(this, OrdersActivity.class);
            intent.putExtra("eventId", clicked.getId());
            intent.putExtra("eventName", clicked.getName());


            startActivity(intent);
        });
    }
    private void loadEvents() {
        GetEventsController endpoint = new GetEventsController();
        endpoint.GET(false).thenAccept(eventList -> {

            events.clear();
            events.addAll(eventList);
            adapter.notifyDataSetChanged();



        });


    }
}