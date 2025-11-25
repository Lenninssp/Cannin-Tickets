package com.example.cannintickets.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.cannintickets.controllers.tickets.GetTicketsController;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends BaseActivity {

    TicketAdapter adapter;
    RecyclerView recyclerView;
    TextView eventName;
    TextView eventDescription;
    TextView eventDate;
    TextView eventLocation;
    List<GetTicketResponseModel> tickets = new ArrayList<>();
    String eventId;
    Button checkoutButton;

    ImageView eventImage;
    Double total;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_event_detail);

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        adapter = new TicketAdapter(tickets);


        eventId = getIntent().getStringExtra("eventId");


        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("date");
        File image = (File) getIntent().getSerializableExtra("image");






        eventName = findViewById(R.id.event_name);
        eventDescription = findViewById(R.id.event_description);
        eventDate = findViewById(R.id.event_date);
        eventLocation = findViewById(R.id.event_location);
        checkoutButton = findViewById(R.id.checkout_button);
        eventImage = findViewById(R.id.event_image);




        eventName.setText(name);
        eventDescription.setText(description);
        eventDate.setText(date);
        eventLocation.setText(location);
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        eventImage.setImageBitmap(bitmap);





        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadTickets();

        checkoutButton.setOnClickListener(view -> {
            List<GetTicketResponseModel> ticketList = adapter.getTickets();
            List<Integer> quantityList = adapter.getQuantities();

            double total = 0.0;
            ArrayList<String> ticketIds = new ArrayList<>();
            ArrayList<Integer> quantities = new ArrayList<>();

            for (int i = 0; i < ticketList.size(); i++) {
                GetTicketResponseModel ticket = ticketList.get(i);
                int qty = quantityList.get(i);

                if (qty > 0) {
                    total += ticket.getPrice() * qty;
                    ticketIds.add(ticket.getId());
                    quantities.add(qty);
                }
            }

            Intent intent = new Intent(EventDetailActivity.this, CheckOutActivity.class);
            intent.putExtra("total", total);
            intent.putExtra("eventId", eventId);
            intent.putExtra("eventName", name);

            intent.putStringArrayListExtra("ticketIds", ticketIds);
            intent.putIntegerArrayListExtra("quantities", quantities);

            startActivity(intent);
        });



    }

    private void loadTickets() {
        GetTicketsController endpoint = new GetTicketsController();
        endpoint.GET(eventId).thenAccept(ticketList -> {

                adapter.setTickets(ticketList);
                progressBar.setVisibility(View.GONE);



        });
    }

}