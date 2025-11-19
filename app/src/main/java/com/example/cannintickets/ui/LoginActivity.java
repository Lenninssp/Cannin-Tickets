package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.MainActivity;
import com.example.cannintickets.R;
import com.example.cannintickets.controllers.auth.LoginController;
import com.example.cannintickets.entities.user.login.UserLoginEntity;
import com.example.cannintickets.models.user.auth.UserLoginRequestModel;

public class LoginActivity extends AppCompatActivity {



    EditText email;
    EditText password;
    Button login;
    TextView register;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);




        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        login = findViewById(R.id.login_button);
        register = findViewById(R.id.register_text);



        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        login.setOnClickListener(v -> {
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            if (emailText.isEmpty() || passwordText.isEmpty()) {
                email.setError("enter email");
                password.setError("enter password");
            } else {
                LoginController endpoint = new LoginController();
                endpoint.POST(
                        new UserLoginRequestModel(
                                emailText,
                                passwordText)



                ).thenApply(success -> {
                    //Toast.makeText(this, "Process completed", Toast.LENGTH_SHORT).show();
                    if(!success.isSuccess()){
                        Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                    return success;
                });

            }


        });



    }
}