package com.example.cannintickets.usecases;

import android.content.Context;

import com.example.cannintickets.entities.image.CommonImage;
import com.example.cannintickets.entities.image.CommonImageFactory;
import com.example.cannintickets.entities.image.ImageEntity;
import com.example.cannintickets.entities.image.ImageFactory;
import com.example.cannintickets.repositories.ImageRepository;

import java.io.File;

//TODO: The whole use case is missing authentication, I just wanted to start with the images because they seemed hard
public class ProfilePictureUseCase {
    private final Context context;
    final CommonImageFactory imageFactory;
    final ImageRepository imageRepo;
    public ProfilePictureUseCase(Context context) {
        this.context = context.getApplicationContext(); // safe
        this.imageFactory = new CommonImageFactory();
        this.imageRepo = new ImageRepository();
    }


    public String[] execute(File profilePicture){
        ImageEntity image = imageFactory.create(profilePicture);

        if(
                !image.isValid()
        ) {
            return new String[]{"ERROR", "Profile picture is invalid"};
        }

        String[] result  = imageRepo.create(context, image.getImage());

        if (result[0].equals( "SUCCESS")) {
            System.out.println(result[1]);
            return new String[]{"SUCCESS", "Profile picture user case compiled successfully"};
        }
        else {
            System.out.println(result[1]);
            return new String[]{"ERROR", "Profile picture user case compiled successfully"};
        }


    }
}
