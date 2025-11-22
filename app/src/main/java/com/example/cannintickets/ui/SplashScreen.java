package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.auth.GetUserController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1500;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);


        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser == null) {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
                return;
            }

            GetUserController endpoint = new GetUserController();

            endpoint.POST()
                    .thenAccept(response -> {

                        String role = response.getRole();

                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);


                            if ("BUYER".equals(role)) {
                                startActivity(new Intent(SplashScreen.this, BuyerEventsActivity.class));
                            } else if ("SELLER".equals(role)) {
                                startActivity(new Intent(SplashScreen.this, SellerEventsActivity.class));
                            } else {
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            }
                            finish();
                        });

                    })
                    .exceptionally(e -> {
                        runOnUiThread(() -> {
                            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            finish();
                        });
                        return null;
                    });

        }, SPLASH_DELAY);
    }
}
