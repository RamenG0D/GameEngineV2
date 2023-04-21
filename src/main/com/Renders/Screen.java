package com.Renders;

import java.awt.Color;

import com.Entities.Sprite;

public class Screen {
    private Sprite sprite;
    public int[] pixels;
    public int height;
    public int width;
    //
    public Screen(int width, int height) {
        pixels = new int[width * height];
        this.width = width;
        this.height = height;
    }
    //
    public void render(int xPos, int yPos, int color) {
        //
        int row, col;
        for(row = 0; row < sprite.width; row++) {
            for(col = 0; col < sprite.height; col++) {
                //pixels[(row*col)] = ; 
            }
        }
    }
}
