package com.example.cannintickets.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.cannintickets.controllers.events.DeleteEventController;
import com.example.cannintickets.controllers.events.GetEventsController;
import com.example.cannintickets.controllers.events.ModifyEventController;
import com.example.cannintickets.models.events.create.CreateEventRequestModel;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class EventActivity extends AppCompatActivity {
    Button createEvent, getEvents, updateEvent, deleteEvent, getImage;
    TextView debugText;
    EditText modId;

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
        setContentView(R.layout.activity_event);

        createEvent = findViewById(R.id.create_event);
        getEvents = findViewById(R.id.get_events);

        debugText = findViewById(R.id.events_text);

        updateEvent = findViewById(R.id.modify_event);
        deleteEvent = findViewById(R.id.delete_event);

        modId = findViewById(R.id.mod_id);

        updateEvent.setOnClickListener(V -> {
            ModifyEventController endpoint = new ModifyEventController();
            endpoint.POST(
                    new ModifyEventRequestModel(
                            modId.getText().toString(),
                            "Lennin modifuies",
                            null,
                            "Bogota, cundinamarca",
                            null,
                            null
                    )
            ).thenApply(event -> {
                if(event.isSuccess()) {
                    Toast.makeText(this, "The event was updated", Toast.LENGTH_SHORT).show();
                    System.out.println("The event was updated successful");
                }
                else {
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(event.getMessage());
                }
                return event;
            });
        });

        getEvents.setOnClickListener(v -> {

            GetEventsController endpoint = new GetEventsController();

            endpoint.GET(false).thenApply(events -> {

                StringBuilder sb = new StringBuilder();

                for (GetEventResponseModel event : events) {
                    System.out.println(event.getId());
                    if (event.hasError()) {
                        sb.append("❌ Error: ").append(event.getError()).append("\n\n");
                    } else {
                        sb.append(" ID: ").append(event.getId()).append("\n")
                                .append("📌 Name: ").append(event.getName()).append("\n")
                                .append("📄 Description: ").append(event.getDescription()).append("\n")
                                .append("📍 Location: ").append(event.getLocation()).append("\n")
                                .append("🗓 Date: ").append(event.getEventDate()).append("\n")
                                .append("Organizator: ").append(event.getOrganizerId()).append("\n");
                        sb.append("\n--------------------------\n\n");
                    }
                }

                String displayText = sb.toString();

                runOnUiThread(() -> {
                    debugText.setText(displayText);
                });

                return null;
            });

        });

        deleteEvent.setOnClickListener(V -> {
            DeleteEventController endpoint = new DeleteEventController();
            endpoint.DELETE(modId.getText().toString()).thenApply(event -> {
                if (event.isSuccess()){
                    Toast.makeText(this, "The event was Deleted", Toast.LENGTH_SHORT).show();
                    System.out.println("The event creation was deleted successful");
                }
                else {
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(event.getMessage());
                }
                return event;
            });;
        });

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

        createEvent.setOnClickListener(V -> {
            CreateEventController endpoint = new CreateEventController();
            endpoint.POST(
                    new CreateEventRequestModel(
                            "Aviici tribute",
                            "Respects for the death guy",
                            "2027-04-20T18:30",
                            "Any city, Austria",
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