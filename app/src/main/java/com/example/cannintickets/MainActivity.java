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
import com.google.firebase.FirebaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Button getImage;

    // taken from: https://developer.android.com/training/basics/intents/result#java
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) return;

                    try {
                        File tempFile = uriToTempFile(uri);

                        String[] response =
                                ProfilePictureController.POST(getApplicationContext(), tempFile);

                        Log.d("RESULT", response[1]);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // taken from: https://stackoverflow.com/questions/45977847/make-sure-to-call-firebaseapp-initializeappcontext-first-in-android
        FirebaseApp.initializeApp(this);

        getImage = findViewById(R.id.get_image);
        getImage.setOnClickListener(V -> {
            // taken from: https://codemia.io/knowledge-hub/path/convert_file_uri_to_file_in_android
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                    100
            );
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_MEDIA_IMAGES) ==
                    PackageManager.PERMISSION_GRANTED) {
                mGetContent.launch("image/*");

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private File uriToTempFile(Uri uri) throws Exception {
        File temp = new File(getCacheDir(), System.currentTimeMillis() + ".tmp");

        try (InputStream in = getContentResolver().openInputStream(uri);
             OutputStream out = new FileOutputStream(temp)) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return temp;
    }
}