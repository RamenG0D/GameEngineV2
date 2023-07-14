package com.Renders;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Canvas {
    private int Width, Height;
    private BufferedImage image;
    private int[] pixels;

    public Screen(int Width, int Height) {
        //
        this.Height = Height;
        this.Width = Width;
        //
        image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        //
        this.setSize(Width, Height);
        this.setPreferredSize(new Dimension(Width, Height));
    }

    public final Image getImage() {
        return image;
    }

    public final int getWidth() {
        return Width;
    }

    public final int getHeight() {
        return Height;
    }

    public final int[] getPixels() {
        return pixels;
    }

    public final void setImage(BufferedImage image) {
        this.image = image;
    }
}
