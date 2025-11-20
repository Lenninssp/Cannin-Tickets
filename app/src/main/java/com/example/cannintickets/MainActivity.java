package com.example.cannintickets;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.ui.BuyerEventsActivity;
import com.example.cannintickets.ui.CheckOutActivity;
import com.example.cannintickets.ui.EventActivity;
import com.example.cannintickets.ui.SignUpActivity;
import com.example.cannintickets.ui.TicketActivity;

public class MainActivity extends AppCompatActivity {
    Button goBuyersEvents;
    Button goPayment;
    Button goSignUp;
    Button goEvent;
    Button goTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        goBuyersEvents = findViewById(R.id.go_buyersevents);
        goSignUp = findViewById(R.id.go_signup);
        goEvent = findViewById(R.id.go_event);
        goTicket = findViewById(R.id.go_ticket);
        goPayment = findViewById(R.id.go_payment);

        goSignUp.setOnClickListener(V -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        goEvent.setOnClickListener(V -> {
            Intent intent = new Intent(this, EventActivity.class);
            startActivity(intent);
        });

        goTicket.setOnClickListener(V -> {
            Intent intent = new Intent(this, TicketActivity.class);
            startActivity(intent);
        });

        goPayment.setOnClickListener(V -> {
            Intent intent = new Intent(this, CheckOutActivity.class);
            startActivity(intent);
        });

        goBuyersEvents.setOnClickListener(V -> {
            Intent intent = new Intent(this, BuyerEventsActivity.class);
            startActivity(intent);
        });




        // taken from: https://www.youtube.com/watch?v=28YhHH1mp3o
        // This is how to get the stripe key
        // String stripeKey = BuildConfig.STRIPE_SECRET_KEY;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}