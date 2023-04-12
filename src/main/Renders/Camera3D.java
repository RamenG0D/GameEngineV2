package Renders;

import java.awt.Graphics;

import org.apache.commons.math4.core.jdkmath.JdkMath;

import Test.Player;

public class Camera3D implements Camera {
    private float PI = (float) JdkMath.PI;
    private Player p;
    //
    final class math {
        private float[] cos = new float[360];
        private float[] sin = new float[360];
    }
    private math M;
    //
    public Camera3D(Player p) {
        M = new math();
        this.p = p;
        int x;
        //
        for(x = 0; x < 360; x++) {
            M.cos[x] = (float) JdkMath.cos(x/180.0*PI);
            M.sin[x] = (float) JdkMath.sin(x/180.0*PI);
        }
    }
    //
    @Override
    public void render(Graphics g) {
        //
    }
}
