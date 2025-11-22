package com.example.cannintickets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.cannintickets.controllers.orders.GetOrderController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.orders.OrderResponseModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    TextView eventName, createdAt, ticketType, ticketPrice, ticketQuantity, totalPrice;

    RecyclerView recyclerView;
    OrderAdapter adapter;

    List<OrderResponseModel> orders = new ArrayList<>();

    String eventId;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);


        adapter = new OrderAdapter(orders);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventId = getIntent().getStringExtra("eventId");
        eventName = findViewById(R.id.event_name);
        eventName.setText(getIntent().getStringExtra("eventName"));





        loadOrders();


    }

    private void loadOrders() {
        GetOrderController endpoint = new GetOrderController();
        endpoint.GET(eventId).thenAccept(orderList -> {

            orders.clear();
            orders.addAll(orderList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);




        });
    }
}