package com.example.cannintickets.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.CreateEventController;
import com.example.cannintickets.models.events.create.CreateEventRequestModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CreateEventActivity2 extends AppCompatActivity {

    Button createEvent, addImageButton;
    String name, location, date, description;

    File myFile;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) return;

                    try {

                        myFile = uriToTempFile(uri);

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
        setContentView(R.layout.activity_create_event2);


        name = getIntent().getStringExtra("name");
        location = getIntent().getStringExtra("location");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");



        createEvent = findViewById(R.id.next_button);
        addImageButton = findViewById(R.id.add_image_button);

        addImageButton.setOnClickListener(V -> {
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


        createEvent.setOnClickListener(V -> {
            if (myFile == null) {
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT);}
            else {
                CreateEventController endpoint = new CreateEventController();
                endpoint.POST(
                        new CreateEventRequestModel(
                                name,
                                description,
                                date,
                                location,
                                false,
                                myFile
                        )
                ).thenApply(event -> {
                    if (event.isSuccess()){
                        Toast.makeText(this, "The event was created", Toast.LENGTH_SHORT).show();
                        System.out.println("The event creation was successful");
                    }
                    else {
                        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(event.getMessage());
                    }
                    return event;
                });
                }
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