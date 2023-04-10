package Test;

import java.awt.Color;
import java.awt.Graphics;
import helper.Vector2;

public class Player {
    private Vector2 velocity = new Vector2();
    public int x, y, angle, look;
    //
    public Player(int x, int y) {
        //
        this.x = x;
        this.y = y;
        angle = 90;
        look = 0;
        //
    }
    //
    public Vector2 getVelocity() {
        return velocity;
    }
    //
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 15, 15);
        //
        g.setColor(Color.GREEN);
        g.drawLine(x, y, (int)((Math.cos(angle) + x) * 5), (int)((Math.sin(angle) + y) * 5));
        //
    }
}
