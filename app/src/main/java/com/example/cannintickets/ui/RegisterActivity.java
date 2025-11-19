package com.example.cannintickets.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.auth.SignupController;
import com.example.cannintickets.entities.user.UserRole;
import com.example.cannintickets.models.user.auth.UserSignupRequestModel;

public class RegisterActivity extends AppCompatActivity {

    Spinner spinner;

    Button registerButton;
    EditText emailEditText;
    EditText passwordEditText;
    EditText usernameEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        spinner = findViewById(R.id.spinner_role);
        registerButton = findViewById(R.id.register_button);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        usernameEditText = findViewById(R.id.username);



        ArrayAdapter<UserRole> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                UserRole.values()
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);



        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            UserRole role = (UserRole) spinner.getSelectedItem();



            SignupController endpoint = new SignupController();
            endpoint.POST(
                    new UserSignupRequestModel(
                            username,
                            email,
                            password,
                            role.toString()
                    )
            ).thenApply(success -> {
                Toast.makeText(this, success.toString(), Toast.LENGTH_SHORT).show();
                if(!success.isSuccess()){
                    Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
                return success;
            });

        });




    }
}