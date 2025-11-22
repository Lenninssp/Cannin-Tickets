package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.auth.GetUserController;
import com.example.cannintickets.controllers.usertickets.GetUserTicketsController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PaymentErrorActivity extends AppCompatActivity {

    String eventId;
    String eventName;

    TextView errorMessage;

    private static final int SPLASH_DELAY = 1500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_error);

        eventId = getIntent().getStringExtra("eventId");
        eventName = getIntent().getStringExtra("eventName");

        errorMessage = findViewById(R.id.error_message);



        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(PaymentErrorActivity.this, BuyerEventsActivity.class);
            startActivity(intent);
            finish();


        }, SPLASH_DELAY);

    }

}
