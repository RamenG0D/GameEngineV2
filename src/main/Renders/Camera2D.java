package Renders;
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
    public Camera2D(int x, int y, int width, int height) {
        //
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        //
        player = new Player(0, 0);
    }
    //
    @Override
    public void render(Graphics g) {
        // render stuff
        if(player != null) player.draw(g);
        //
    }
    //
}
