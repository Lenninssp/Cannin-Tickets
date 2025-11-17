package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.BuildConfig;
import com.example.cannintickets.models.orders.OrderPersistenceModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class OrdersRepotitory {
    final String stripeSecretKey;
    final FirebaseFirestore db;

    public OrdersRepotitory() {
        this.stripeSecretKey = BuildConfig.STRIPE_SECRET_KEY;
        this.db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<String> create(OrderPersistenceModel order) throws StripeException {
        CompletableFuture<String> future = new CompletableFuture<>();
        Stripe.apiKey = stripeSecretKey;
        long amountInCents = Math.round(order.getAmount() * 100);
        // taken from: https://docs.stripe.com/payments/accept-a-payment?platform=android&lang=java
        PaymentIntentCreateParams paymentIntentParams =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency("cad")
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

        Map<String, Object> newOrder = new HashMap<>();
        newOrder.put("customerEmail", order.getCustomerEmail());
        newOrder.put("ticketId", order.getTicketId());
        newOrder.put("ticketName", order.getTicketName());
        newOrder.put("ticketPrice", order.getTicketPrice());
        newOrder.put("status", order.getStatus());
        newOrder.put("amount", order.getAmount());
        newOrder.put("quantity", order.getQuantity());
        newOrder.put("paymentIntentId", paymentIntent.getClientSecret());
        newOrder.put("eventName", order.getEventName());

        db.collection("Event")
                .add(newOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        future.complete("The event was successfully created");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        future.complete(e.toString());
                    }
                });
        return future;

//        responseData.put("paymentIntent", paymentIntent.getClientSecret());
//        responseData.put("publishableKey", "pk_test_51SRx36DyAcGrBz3Luekq2mIpgXSsmpJDUuujRWO3sbMgi9ks8s3faZfsx9I2QalqzVstpfARQCpIeCAh9PJnVrhJ008Nxmtiaz");

    }
}
