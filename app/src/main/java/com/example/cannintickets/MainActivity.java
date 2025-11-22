package com.example.cannintickets;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.ui.BaseActivity;
import com.example.cannintickets.ui.SignUpActivity;

public class MainActivity extends BaseActivity {

    Button goSignUp;



    Button createOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_main);

        goSignUp = findViewById(R.id.go_signup);










        goSignUp.setOnClickListener(V -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });


        // Stripe key




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