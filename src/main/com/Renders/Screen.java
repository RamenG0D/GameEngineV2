package com.Renders;

public class Screen {
    //public int xOffset = 0;
    //public int yOffset = 0;
    public int[] pixels;
    public int height;
    public int width;
    //
    public Screen(int width, int height) {
        pixels = new int[width * height];
    }
    //
    /*public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }*/
}
