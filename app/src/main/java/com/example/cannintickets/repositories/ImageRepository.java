package com.example.cannintickets.repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ImageRepository {

    final FirebaseStorage storage;
    final StorageReference storageRef;

    public ImageRepository() {
        this.storage = FirebaseStorage.getInstance("gs://cannintickets.firebasestorage.app");
        this.storageRef = storage.getReference();
    }

    public CompletableFuture<File> get(String imagePath) {
        System.out.println("LENNIN" + imagePath);
        CompletableFuture<File> returnImage = new CompletableFuture<>();

        StorageReference pathReference = storageRef.child(imagePath);
        File localFile = null;
        try {
            localFile = File.createTempFile("images", ".jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File finalLocalFile = localFile;
        pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                System.out.println(finalLocalFile);
                returnImage.complete(finalLocalFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                returnImage.completeExceptionally(e);
            }
        });

        return returnImage;
    }

    public CompletableFuture<String> create(File image, String eventId) {
        CompletableFuture<String> future = new CompletableFuture<>();

        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());

        if (bitmap == null) {
            future.complete("Could not decode bitmap");
        }

        StorageReference eventRef = storageRef.child("images/"+ eventId + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        assert bitmap != null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        UploadTask uploadTask = eventRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                future.complete("There was an error uploading the image");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageMetadata metadata = taskSnapshot.getMetadata();

                assert metadata != null;
                future.complete(metadata.getPath());
            }
        });
        return future;
    }
}
