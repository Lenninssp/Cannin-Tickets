package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.usertickets.GetUserTicketsController;

public class SuccessActivity extends AppCompatActivity {

    String eventId;
    String eventName;
    Button ticketButton;

    private Handler handler = new Handler(Looper.getMainLooper());
    private static final int CHECK_INTERVAL_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_success);

        eventId = getIntent().getStringExtra("eventId");
        eventName = getIntent().getStringExtra("eventName");

        ticketButton = findViewById(R.id.ticket_button);

        Toast.makeText(this, "Event: " + eventName, Toast.LENGTH_SHORT).show();

        ticketButton.setEnabled(false);
        ticketButton.setText("Creating tickets...");

        checkTickets();

        ticketButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserTicketActivity.class);
            startActivity(intent);
        });
    }

    private void checkTickets() {
        GetUserTicketsController endpoint = new GetUserTicketsController();

        endpoint.GET(eventId)
                .thenAccept(tickets -> runOnUiThread(() -> {
                    if (tickets == null || tickets.isEmpty()) {
                        // No tickets yet -> try again later
                        handler.postDelayed(this::checkTickets, CHECK_INTERVAL_MS);
                        return;
                    }

                    boolean found = false;

                    for (int i = 0; i < tickets.size(); i++) {
                        String ticketName = tickets.get(i).getEventName();

                        Toast.makeText(this, "Ticket: " + ticketName, Toast.LENGTH_SHORT).show();

                        if (eventName.equals(ticketName)) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        ticketButton.setEnabled(true);
                        ticketButton.setText("Go to tickets");
                    } else {
                        handler.postDelayed(this::checkTickets, CHECK_INTERVAL_MS);
                    }
                }))
                .exceptionally(e -> {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Error checking tickets", Toast.LENGTH_SHORT).show()
                    );
                    handler.postDelayed(this::checkTickets, CHECK_INTERVAL_MS);
                    return null;
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
