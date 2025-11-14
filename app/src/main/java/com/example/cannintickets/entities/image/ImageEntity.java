package com.example.cannintickets.entities.image;

import java.io.File;

public interface Image {
    boolean filePathIsCorrect();
    boolean isValid();
    File getFile();

}
