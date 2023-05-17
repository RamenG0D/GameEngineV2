package com.Entities;

import java.awt.Graphics;

public interface Entity {
    
    public default boolean isVisible() {
        return true;
    }

    public void render(Graphics g);

}
