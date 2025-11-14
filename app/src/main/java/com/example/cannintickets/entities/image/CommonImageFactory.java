package com.example.cannintickets.entities.image;

import java.io.File;

public class CommonImageFactory  implements ImageFactory{
    @Override
    public ImageEntity create(File image) {
        return new CommonImage(image);
    }
}
