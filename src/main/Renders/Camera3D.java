package Renders;

import java.awt.Graphics;

import org.apache.commons.math4.core.jdkmath.JdkMath;

import Test.Player;

public class Camera3D implements Camera {
    private float PI = (float) JdkMath.PI;
    private boolean CamSet = false;
    private Player P;
    //
    final class math {
        private float[] cos = new float[360];
        private float[] sin = new float[360];
    }
    private math M;
    //
    public Camera3D() {
        int x;
        //
        for(x = 0; x < 360; x++) {
            M.cos[x] = (float) JdkMath.cos(x/180.0*PI);
            M.sin[x] = (float) JdkMath.sin(x/180.0*PI);
        }
        //player init (testing currently)
        P = new Player();
        P.x = 70;
        P.y = -110;
        P.z = 20;
        P.angle =0;
        P.look = 0;
    }
    //
    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        //
    }
    //
    @Override
    public void setViewPortSize(int width, int height) {
        ViewPort.width = width - 1;
        ViewPort.height = height - 38;
    }
    //
    @Override
    public boolean ViewPortAlreadySet() {
        return CamSet;
    }
}
