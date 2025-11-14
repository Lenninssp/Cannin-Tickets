package com.example.cannintickets.entities.image;

import java.io.File;

public interface ImageFactory {
    ImageEntity create(File image);
}
