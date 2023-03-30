package Renders;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Camera2D implements Camera {
    private boolean CameraSet = false; // false by default so no null or other error occur
    private Rectangle ViewPort;
    //
    public Camera2D(int x, int y, int width, int height) {
        ViewPort = new Rectangle(x, y, width, height);
    }
    //
    public Camera2D(int x, int y) {
        // zeros are temp untill set later by the paint method
        ViewPort = new Rectangle(x, y, 0, 0);
    }
    //
    public Camera2D(Rectangle viewport) {
        ViewPort = viewport;
    }
    //
    @Override
    public void render(Graphics g) {
        // render stuff
    }
    //
    @Override
    public void setViewPortSize(int width, int height) {
        ViewPort.width = width - 1;
        ViewPort.height = height - 38;
    }
    //
    public Rectangle getViewPort() {
        return ViewPort;
    }
    //
    @Override
    public boolean ViewPortAlreadySet() {
        return CameraSet;
    }
}
