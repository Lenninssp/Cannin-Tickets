package com.example.cannintickets.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.SignupController;
import com.example.cannintickets.models.presenters.UserResponseFormatter;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;

import java.util.concurrent.CompletableFuture;

public class SignUpActivity extends AppCompatActivity {
    Button signup, login, logout, currentState;
    TextView debugtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        signup = findViewById(R.id.sign_up);
        login = findViewById(R.id.log_in);
        logout = findViewById(R.id.log_out);
        currentState = findViewById(R.id.current_state);
        debugtxt = findViewById(R.id.debug_txt);

        signup.setOnClickListener(V -> {
            SignupController endpoint = new SignupController();
//            CompletableFuture<UserSignupResponseModel> response = endpoint.POST(
//                    new UserSignupRequestModel(
//                            "LenninSabogal",
//                            "lenninssp1021@gmail.com",
//                            "Sabogareto13*",
//                            "Seller")
            CompletableFuture<UserSignupResponseModel> response = endpoint.POST(
                    new UserSignupRequestModel(
                            "CamiloMontero",
                            "camilo@gmail.com",
                            "Sabogareto13*",
                            "Seller")
            ).thenApply(success -> {
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();
                return success;
            }).exceptionally(error -> {
                Toast.makeText(this, "Unexpected error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                return null;
            });

        });

        


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}