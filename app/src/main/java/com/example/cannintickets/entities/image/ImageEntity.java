package com.example.cannintickets.entities.image;

import java.io.File;

public interface ImageEntity {
    boolean filePathIsCorrect();
    boolean isValid();
    File getImage();

}
