package com.example.cannintickets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.auth.GetUserController;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.controllers.save.ToggleEventSave;
import com.example.cannintickets.models.events.get.GetEventResponseModel;

import java.util.ArrayList;
import java.util.List;

public class SavedEventsActivity extends BaseActivity {

    RecyclerView recyclerView;
    SaveEventAdapter adapter;

    List<GetEventResponseModel> events = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_saved_events);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        adapter = new SaveEventAdapter(events);

        adapter.setOnLikeClickListener(position -> {
            GetEventResponseModel likedEvent = events.get(position);

            GetUserController getUserController = new GetUserController();
            getUserController.POST().thenAccept(response -> {
                if (response.isSuccess()) {
                    ToggleEventSave toggleEventSave = new ToggleEventSave();
                    toggleEventSave.POST(response.getEmail(), likedEvent.getId());
                }
            });
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();
    }

    private void loadEvents() {

        GetEventsController endpoint = new GetEventsController();
        endpoint.GET(true).thenAccept(eventList -> {
            runOnUiThread(() -> {
                events.clear();
                events.addAll(eventList);

                adapter.setAllLiked();

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            });
        });
    }
}
