package com.app.helper;

import java.awt.Graphics;

/**
 * the interface used to specify that this class is an {@code Entity}
 * and should be treated as such, so anything that implements this will
 * have all traits a regular game {@code Entity} should normally have
 * @author RamenGOD
 */
public interface Entity {
    //
    public int gravity = 4;
    //
    public boolean isHostile();
    //
    public void paint(Graphics g);
    //
    public double getHealth();
    //
    public void setHealth(double health);
    //
    public void gravity();
    //
    public int getX();
    //
    public int getY();
    //
}
