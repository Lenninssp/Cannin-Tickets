package com.example.cannintickets.ui;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cannintickets.BuildConfig;
import com.example.cannintickets.MainActivity;
import com.example.cannintickets.controllers.orders.CreateOrderController;
import com.example.cannintickets.models.orders.OrderRequestModel;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.*;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cannintickets.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {

    PaymentSheet paymentSheet;
    String SecretKey = BuildConfig.SECRET_KEY;
    String CustomerId = "";
    String ClientSecret = "";
    String EmphericalKey = "";
    String PaymentIntentId = "";

    String eventName;

    Double total;
    Long amountInCents;

    String eventId;
    List<String> ticketIds;
    List<Integer> quantities;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_out);

        eventName = getIntent().getStringExtra("eventName");

        total = getIntent().getDoubleExtra("total", 0.0);
        amountInCents = Math.round(total * 100);

        eventId = getIntent().getStringExtra("eventId");

        ticketIds = getIntent().getStringArrayListExtra("ticketIds");
        quantities = getIntent().getIntegerArrayListExtra("quantities");




        PaymentConfiguration.init(this, BuildConfig.PUBLISHABLE_KEY);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });



        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            CustomerId = jsonObject.getString("id");
                            Toast.makeText(getApplicationContext(), CustomerId, Toast.LENGTH_SHORT).show();

                            getEmphericalKey();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);

                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                ClientSecret,
                new PaymentSheet.Configuration(
                        "Cannin Tickets",
                        new PaymentSheet.CustomerConfiguration(
                                CustomerId,
                                EmphericalKey
                        )
                )
        );
    }



    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {

            if (ticketIds == null || quantities == null || ticketIds.size() == 0) {
                Toast.makeText(this, "No tickets selected", Toast.LENGTH_SHORT).show();
                return;
            }

            CreateOrderController orderController = new CreateOrderController();

            for (int i = 0; i < ticketIds.size(); i++) {
                final int index = i;

                OrderRequestModel orderRequestModel =
                        new OrderRequestModel(
                                eventId,
                                PaymentIntentId,
                                quantities.get(index),
                                ticketIds.get(index)
                        );
                orderController.POST(orderRequestModel)
                        .thenApply(response -> {
                            if (!response.isSuccess()) {
                                Toast.makeText(CheckOutActivity.this, "Order failed for ticket " + ticketIds.get(index) + ": " + response.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this, PaymentErrorActivity.class);
                                intent.putExtra("eventId", response.getMessage());
                                startActivity(intent);
                            } else {
                                Toast.makeText(CheckOutActivity.this, "Order created for ticket " + ticketIds.get(index), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this, SuccessActivity.class);
                                intent.putExtra("eventId", eventId);
                                intent.putExtra("eventName", eventName);
                                startActivity(intent);
                            }
                            return response;
                        })
                        .exceptionally(error -> {
                                    Toast.makeText(CheckOutActivity.this, "Order error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return null;
                        });
            }



            Toast.makeText(getApplicationContext(), "Payment Completed", Toast.LENGTH_SHORT).show();
        }
        else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getApplicationContext(), "Payment Canceled", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEmphericalKey() {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            EmphericalKey = jsonObject.getString("secret");
                            Toast.makeText(getApplicationContext(), CustomerId, Toast.LENGTH_SHORT).show();

                            getClientSecret(CustomerId, EmphericalKey);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                header.put("Stripe-Version", "2022-11-15");

                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getClientSecret(String customerId, String emphericalKey) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ClientSecret = jsonObject.getString("client_secret");
                            Toast.makeText(getApplicationContext(), ClientSecret, Toast.LENGTH_SHORT).show();

                            if (ClientSecret != null && ClientSecret.contains("_secret_")) {
                                PaymentIntentId = ClientSecret.split("_secret_")[0];
                            }



                            paymentFlow();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);

                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                params.put("amount", String.valueOf(amountInCents));
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
