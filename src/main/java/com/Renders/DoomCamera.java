package com.Renders;

import java.awt.Graphics;

public class DoomCamera implements Camera {
    private int x, y, z, angle, look;
    
    public DoomCamera(){angle = 90;}

    @Override
    public void render(Graphics g) {}

    @Override
    public void move(double x, double y, double z) {this.x += x;this.y += y;this.z += z;}

    @Override
    public void rotate(double degrees) {}    
}
