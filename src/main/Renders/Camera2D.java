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
        // render stuff
        if(player != null) player.render(g);
        //
    }
    //
}
