package com.Renders;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
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

    public final void setPixel(int i, int color) {
        pixels[i] = color;
    }

    public final void setPixel(int x, int y, int color) {
        pixels[y*getWidth()+x] = color;
    }

    public void drawLine(int x1, int y1, int x2, int y2, int r, int g, int b) {
        int dx = x2 - x1, dy = y2 - y1;
        double l = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        for(int t = 1; t < l+1; t++) {
            int newX = (int)(x1 + (((dx / l) * t)));
            int newY = (int)(y1 + (((dy / l) * t)));
            if(newX > getWidth() - 1 || newY > getHeight() - 1) continue;
            if(newX < 0 || newY < 0) continue;
            setPixel(newX, newY, new Color(r, g, b).getRGB());
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1, dy = y2 - y1;
        double l = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        for(int t = 1; t < l+1; t++) {
            int newX = (int)(x1 + (((dx / l) * t)));
            int newY = (int)(y1 + (((dy / l) * t)));
            if(newX > getWidth() - 1 || newY > getHeight() - 1) continue;
            if(newX < 0 || newY < 0) continue;
            setPixel(newX, newY, color);
        }
    }

    public void drawRect(int x, int y, int width, int height, int r, int g, int b) {
        for(int row = 0; row < image.getWidth(); row++) {
            for(int col = 0; col < image.getHeight(); col++) {
                if(contains(row, col, new Rectangle(x, y, width, height)))
                setPixel(row, col, new Color(r, g, b).getRGB());
                else continue;
            }
        }
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        for(int r = 0; r < image.getWidth(); r++) {
            for(int c = 0; c < image.getHeight(); c++) {
                if(contains(r, c, new Rectangle(x, y, width, height))) 
                setPixel(r, c, color);
                else continue;
            }
        }
    }

    public final boolean contains(int x, int y, int width, int height, int x2, int y2) {
        return new Rectangle(x, y, width, height).contains(x2, y2);
    }

    public final boolean contains(int x, int y, Rectangle r) {
        return r.contains(x, y);
    }

    public final void setImage(BufferedImage image) {
        this.image = image;
    }
}
