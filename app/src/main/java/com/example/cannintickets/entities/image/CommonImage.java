package com.example.cannintickets.entities.image;

import java.io.File;
import java.util.Arrays;

// taken from: https://www.baeldung.com/spring-boot-clean-architecture
public class CommonImage implements ImageEntity {
    File image;
    String[] acceptedFiletypes = {"png", "jpg", "jpeg", "tmp"};
    public CommonImage(File image){
        this.image = image;
    }

    @Override
    public boolean filePathIsCorrect() {
        String extension = "";
        String fileName = image.getAbsoluteFile().getName();
        int i = fileName.lastIndexOf('.');
        if (i >= 0){
            extension = fileName.substring(i+1);
        }
        return Arrays.asList(acceptedFiletypes).contains(extension);
    }
    @Override
    public boolean isValid() {
//        System.out.println("Executing " + image.getPath() + image.canRead() + !image.isDirectory() + image.exists() + filePathIsCorrect() );
        return image.canRead() && !image.isDirectory() && image.exists() && filePathIsCorrect();
    }

    public File getImage() {
        return image;
    }

}
