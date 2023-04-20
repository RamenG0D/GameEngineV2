package com.utils;

import com.Renders.Screen;

public abstract class Entity {
    //this class shouldnt contain too much data as individual entities need so specify it themselves
    public int x, y;
    //
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //
    public void render(Screen screen) {
        //
    }
    /*
     * g.setColor(Color.RED);
     * g.drawRect(x, y, 10, 10);
     * g.drawImage(img, x, y, null);
     */
}
