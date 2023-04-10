package Test;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
    public int x, y;
    //
    public Player(int x, int y) {
        //
        this.x = x;
        this.y = y;
        //
    }
    //
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 10, 10);
        //
    }
}
