package com.example.cannintickets;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.controllers.orders.CreateOrderController;
import com.example.cannintickets.models.orders.OrderRequestModel;
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

    Button createOrder;
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
        createOrder = findViewById(R.id.create_order);


        createOrder.setOnClickListener(V -> {
            String eventId = "5JKWsOb6PmI8QdndnKN5";
            String PaymentIntentId = "pi_3N";
            Integer quantities = 2;
            String ticketIds = "o7zyYGt4A6j1Fe9kpsIK";

            CreateOrderController orderController = new CreateOrderController();
            OrderRequestModel orderRequestModel =
                    new OrderRequestModel(
                            eventId,
                            PaymentIntentId,
                            quantities,
                            ticketIds
                    );
            orderController.POST(orderRequestModel)
                    .thenApply(response -> {
                        runOnUiThread(() -> {
                            if (!response.isSuccess()) {
                                Toast.makeText(
                                        this,
                                        "Order failed for ticket " + ticketIds +
                                                ": " + response.getMessage(),
                                        Toast.LENGTH_SHORT

                                ).show();
                                System.out.println(response.getMessage());
                            } else {
                                Toast.makeText(
                                        this,
                                        "Order created for ticket " + ticketIds,
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
                        return response;
                    })
                    .exceptionally(error -> {
                        runOnUiThread(() ->
                                Toast.makeText(
                                        this,
                                        "Order error: " + error.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show()
                        );
                        return null;
                    });

        });


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