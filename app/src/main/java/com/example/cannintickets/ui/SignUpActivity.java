package com.example.cannintickets.ui;

import android.content.Intent;
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
import com.example.cannintickets.models.user.auth.UserLoginRequestModel;
import com.example.cannintickets.models.user.auth.UserSignupRequestModel;

public class SignUpActivity extends BaseActivity {
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setChildContentView(R.layout.activity_sign_up);

        logout = findViewById(R.id.log_out);


        logout.setOnClickListener(V -> {
            LogoutController endpoint = new LogoutController();
            endpoint.POST()
                    .thenApply(message -> {
                        if (message[0].equals("ERROR")){
                            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
                            return  message;
                        }
                        else {
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            return message;
                        }
                    });
        });




    }

}