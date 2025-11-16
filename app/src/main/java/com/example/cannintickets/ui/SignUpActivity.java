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
import com.example.cannintickets.controllers.auth.LoginController;
import com.example.cannintickets.controllers.auth.LogoutController;
import com.example.cannintickets.controllers.auth.SignupController;
import com.example.cannintickets.models.user.auth.request.UserLoginRequestModel;
import com.example.cannintickets.models.user.auth.request.UserSignupRequestModel;

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

        logout.setOnClickListener(V -> {
            LogoutController endpoint = new LogoutController();
            endpoint.POST()
                    .thenApply(message -> {
                        if (message[0].equals("ERROR")){
                            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
                            debugtxt.setText(message[1]);
                            return  message;
                        }
                        else {
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                            debugtxt.setText("User logged out successfully");
                            return message;
                        }
                    });
        });

        login.setOnClickListener(V -> {
            LoginController endpoint = new LoginController();
            endpoint.POST(
                    new UserLoginRequestModel(
                            "cacorra@gmail.com",
                            "Sabogareto13*")

            ).thenApply(success -> {
                Toast.makeText(this, "Process completed", Toast.LENGTH_SHORT).show();
                if(!success.isSuccess()){
                    debugtxt.setText(success.getError());
                }
                else {
                    debugtxt.setText("success");
                }
                return success;
            });
        });

        signup.setOnClickListener(V -> {
            SignupController endpoint = new SignupController();
//            CompletableFuture<UserSignupResponseModel> response = endpoint.POST(
//                    new UserSignupRequestModel(
//                            "LenninSabogal",
//                            "lenninssp1021@gmail.com",
//                            "Sabogareto13*",
//                            "Seller")
            endpoint.POST(
                    new UserSignupRequestModel(
                            "cacorroCocarra",
                            "cacorra@gmail.com",
                            "Sabogareto13*",
                            "Buyer")
            ).thenApply(success -> {
                Toast.makeText(this, success.toString(), Toast.LENGTH_SHORT).show();
                if(!success.isSuccess()){
                    debugtxt.setText(success.getError());
                }
                else {
                    debugtxt.setText(success.toString());
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