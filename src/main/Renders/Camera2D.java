package Renders;
//
import java.awt.Graphics;

public class Camera2D implements Camera {
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
    }
    //
    public Camera2D(int x, int y) {
        //
        this.x = x;
        this.y = y;
        //
    }
    //
    @Override
    public void render(Graphics g) {
        // render stuff
    }
    //
    @Override
    public void setViewPortSize(int width, int height) {
        //
        this.height = height;
        this.width  =  width;
        //
    }
}
