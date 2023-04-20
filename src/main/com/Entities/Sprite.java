package com.Entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import com.utils.ImageLoader;

public class Sprite {
    public int width, height;
    private int[] pixels;
    public String path;
    //
    public Sprite(String path) {
        BufferedImage image = null;
        try {
            image = ImageLoader.getImage(path);
        } catch(IOException e) { e.printStackTrace(); }
        //
        if(image == null) return;
        //
        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();
        //
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        //
    }
    //
    public int[] getPixels() {
        return pixels;
    }
}
