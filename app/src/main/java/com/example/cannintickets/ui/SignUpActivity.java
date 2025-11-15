package com.example.cannintickets.ui;

import android.os.Bundle;
import android.util.Log;
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
import com.example.cannintickets.controllers.SignupController;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;

public class SignUpActivity extends AppCompatActivity {
    EditText name, email, password1, password2;
    Spinner role;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
//        name = findViewById(R.id.name);
//        email = findViewById(R.id.email);
//        password1 = findViewById(R.id.password);
//        password2 = findViewById(R.id.password_again);
//        role = findViewById(R.id.roles_spinner);
        submit = findViewById(R.id.submit);
//
//        // taken from: https://developer.android.com/develop/ui/views/components/spinner
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this,
//                R.array.roles_array,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        role.setAdapter(adapter);
//            String parsedRole = role.getSelectedItem().toString();

        submit.setOnClickListener(V -> {
            SignupController endpoint = new SignupController();
//            Log.d("password", password1.getText().toString() + " " + password2.getText().toString() + " " + parsedRole);
            UserSignupResponseModel response = endpoint.POST(new UserSignupRequestModel("LenninSabogal", "lenninssp1021@gmail.com", "Sabogareto13*", "Seller"));

//            if (password1.getText().toString().equals(password2.getText().toString())){
//                UserSignupResponseModel response = endpoint.POST(new UserSignupRequestModel(name.getText().toString(), email.getText().toString(), password1.getText().toString(), parsedRole));
//
//                Log.d("response", response.toString());
//            }
//            else {
//                Toast.makeText(this, "The passwords are not equal", Toast.LENGTH_SHORT).show();
//            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}