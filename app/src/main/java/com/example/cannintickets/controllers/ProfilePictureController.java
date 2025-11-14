package com.example.cannintickets.controllers;

import android.content.Context;

import androidx.activity.result.ActivityResultRegistry;

import com.example.cannintickets.usecases.ProfilePictureUseCase;

import java.io.File;

// taken from: https://www.baeldung.com/java-helper-vs-utility-classes
public class ProfilePictureController {
    private ProfilePictureController(){
        throw new UnsupportedOperationException("This is a controller class, it shouldn't be instantiated");
    }
    public static String[] POST(Context context, File file){
        ProfilePictureUseCase useCase = new ProfilePictureUseCase(context);
        String[] response = useCase.execute(file);
        return response;
    }
}
