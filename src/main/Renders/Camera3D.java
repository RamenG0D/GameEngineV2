package Renders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Camera3D implements Camera {
    private Rectangle ViewPort;
    //
    public Camera3D(int x, int y, int width, int height) {
        //
        //
        //
    }
    //
    public Camera3D(Rectangle viewport) {
        ViewPort = viewport;
    }
    //
    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        //
        g.drawRect(ViewPort.x, ViewPort.y, ViewPort.width, ViewPort.height);
        //
    }
    //
    public Rectangle getViewPort() {
        return ViewPort;
    }
}
