package com.app.helper;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    private Image img;
    //
    public ImageLoader(String path, int width, int height) throws IOException {
        //
        File file = new File(path);
        //
        Image img = ImageIO.read(file);
        //
        img = img.getScaledInstance(width, height, 0);
        //
        this.img = img;
    }
    //
    public ImageLoader(String path, int size) throws IOException {
        //
        File file = new File(path);
        //
        Image img = ImageIO.read(file);
        //
        img = img.getScaledInstance(size, size, 0);
        //
        this.img = img;
    }
    //
    public Image get_image() {
        return img;
    }
}
