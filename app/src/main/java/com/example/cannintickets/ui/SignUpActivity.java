package com.example.cannintickets.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.SignupController;
import com.example.cannintickets.models.auth.signup.request.UserSignupRequestModel;
import com.example.cannintickets.models.auth.response.UserResponseModel;

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
            CompletableFuture<UserResponseModel> response = endpoint.POST(
                    new UserSignupRequestModel(
                            "CamiloMontero",
                            "camilo@gmail.com",
                            "Sabogareto13*",
                            "Seller")
            ).thenApply(success -> {
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();
                if(success.isSuccess()){
                    debugtxt.setText(success.getError());
                }
                else {
                    debugtxt.setText("success");
                }
                return success;
            });

        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}