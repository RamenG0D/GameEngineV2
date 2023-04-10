package helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public abstract class Entity2D implements Drawable {
    //this class shouldnt contain too much data as individual entities need so specify it themselves
    public Image img;
    public int x;
    public int y;
    //
    public Entity2D(Image img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }
    //
    @Override
    public void draw(Graphics g) {
        //
        g.setColor(Color.RED);
        g.drawRect(x, y, 10, 10);
        g.drawImage(img, x, y, null);
        //
    }
}
