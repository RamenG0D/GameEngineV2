package Test;

import java.awt.Graphics;

public class Player {
    public int x, y, z; // position
    public int angle;
    public int look; // up and down look rotation for player

    public void draw(Graphics g) {
        g.fillRect(x, y, 1, 1);
        //
    }
}
