package com.example.cannintickets.repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.FirebaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class ImageRepository {
    public String[] create(Context context, File image) {

        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        if (bitmap == null) {
            return new String[]{"ERROR", "Could not decode bitmap"};
        }

        File dir = new File(context.getExternalFilesDir("profilePictures"), "");
        if (!dir.exists() && !dir.mkdirs()) {
            return new String[]{"ERROR", "Could not create folder"};
        }

        File output = new File(dir, UUID.randomUUID().toString() + ".jpg");

        try (FileOutputStream out = new FileOutputStream(output)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return new String[]{"SUCCESS", output.getAbsolutePath()};
        }
        catch (Exception e) {
            return new String[]{"ERROR", "Could not save image: " + e.getMessage()};
        }
    }
}
