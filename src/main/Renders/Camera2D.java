package Renders;
import java.awt.Color;
//
import java.awt.Graphics;

import Test.Player;

public class Camera2D implements Camera {
    private Player player;
    private int height;
    private int width;
    private int x;
    private int y;
    //
    public Camera2D(Player player, int x, int y, int width, int height) {
        //
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        //
        this.player = player;
    }
    //
    @Override
    public void render(Graphics g) {
        int dx = (int)(Math.cos(player.angle) * 5);
        int dy = (int)(Math.sin(player.angle) * 5);
        // render stuff
        if(player != null) player.draw(g);
        g.setColor(Color.BLUE);
        g.fillRect(40*dx, 40*dy, 10, 10);               g.fillRect(80*dx, 40*dy, 10, 10);
        g.fillRect(40*dx, 80*dy, 10, 10);               g.fillRect(80*dx, 80*dy, 10, 10);
        //
        /*g.fillRect(, , 10, 10);
        g.fillRect(, , 10, 10);
        g.fillRect(, , 10, 10);
        g.fillRect(, , 10, 10);*/
        //
    }
    //
}
