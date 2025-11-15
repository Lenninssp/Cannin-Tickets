package com.example.cannintickets;

import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.app.appsearch.SetSchemaRequest.READ_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cannintickets.controllers.ProfilePictureController;
import com.example.cannintickets.ui.EventActivity;
import com.example.cannintickets.ui.ProfilePictureActivity;
import com.example.cannintickets.ui.SignUpActivity;
import com.google.firebase.FirebaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Button goImage;
    Button goSignUp;
    Button goEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        goImage = findViewById(R.id.go_image);
        goSignUp = findViewById(R.id.go_signup);
        goEvent = findViewById(R.id.go_event);

        goImage.setOnClickListener(V -> {
            Intent intent = new Intent(this, ProfilePictureActivity.class);
            startActivity(intent);
        });

        goSignUp.setOnClickListener(V -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        goEvent.setOnClickListener(V -> {
            Intent intent = new Intent(this, EventActivity.class);
            startActivity(intent);
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}