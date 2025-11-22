package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.cannintickets.controllers.events.DeleteEventController;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.controllers.tickets.DeleteTicketController;
import com.example.cannintickets.controllers.tickets.GetTicketsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;

import java.util.ArrayList;
import java.util.List;

public class SellerEventsActivity extends BaseActivity {


    RecyclerView recyclerView;
    EventSellerAdapter adapter;

    List<GetEventResponseModel> events = new ArrayList<>();

    Button createEventButton;
    private ProgressBar progressBar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_seller_events);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);


        createEventButton = findViewById(R.id.create_event);


        adapter = new EventSellerAdapter(events);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();

        adapter.setOnItemClickListener(new EventSellerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GetEventResponseModel clicked = events.get(position);

                Intent intent = new Intent(SellerEventsActivity.this, ModifyEventActivity.class);
                intent.putExtra("event_id", clicked.getId());
                intent.putExtra("event_name", clicked.getName());
                intent.putExtra("event_description", clicked.getDescription());
                intent.putExtra("event_date", clicked.getEventDate());
                intent.putExtra("event_location", clicked.getLocation());
                intent.putExtra("event_is_private", clicked.getPrivate());

                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                DeleteEventController endpoint = new DeleteEventController();
                endpoint.DELETE(events.get(position).getId()).thenApply(
                        response -> {
                            if(!response.isSuccess()){
                                Toast.makeText(SellerEventsActivity.this, "Error deleting event", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(SellerEventsActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();

                            }
                            loadEvents();
                            return null;
                        }
                );




            }
        });

        createEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateEventActivity.class);
            startActivity(intent);
        });


    }

    private void loadEvents() {
        GetEventsController endpoint = new GetEventsController();
        endpoint.GET(false ).thenAccept(eventList -> {

            events.clear();
            events.addAll(eventList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);




        });


    }


}