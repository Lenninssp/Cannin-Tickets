package com.example.cannintickets.ui;

import android.Manifest;
import android.content.Intent;
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

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.events.CreateEventController;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.models.events.create.CreateEventRequestModel;
import com.example.cannintickets.models.events.get.GetEventResponseModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CreateEventActivity2 extends AppCompatActivity {

    Button createEvent, addImageButton;
    String name, location, date, description;

    File myFile;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) return;

                    try {
                        myFile = uriToTempFile(uri);
                        Toast.makeText(CreateEventActivity2.this, "Image selected", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CreateEventActivity2.this, "Error loading image", Toast.LENGTH_SHORT).show();
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

        addImageButton.setOnClickListener(v -> {
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

        createEvent.setOnClickListener(v -> {
            if (myFile == null) {
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
                return;
            }

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
                if (event.isSuccess()) {
                    GetEventsController endpointCheck = new GetEventsController();
                    endpointCheck.GET(false).thenApply(events -> {
                        for (GetEventResponseModel eventCheck : events) {
                            if (eventCheck.getName().equals(name)) {
                                Intent intent = new Intent(this, CreateTicketsActivity.class);
                                intent.putExtra("eventName", name);
                                intent.putExtra("eventId", eventCheck.getId());
                                startActivity(intent);
                                break;
                            }
                        }
                        return events;
                    });
                } else {
                    System.out.println(event.getMessage());
                }
                return event;
            });
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
