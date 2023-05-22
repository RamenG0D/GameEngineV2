package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import com.Application.App;
import com.demos.Util.Matrix4;
import com.primitives.Vector3;
import com.primitives.Shapes.Cube;
import com.primitives.Shapes.Triangle;

public class Demo3D extends App {

    public Demo3D(){super();}

    @Override
    public void update(float delta) { this.delta = delta; }

    private float theta, delta; private Cube c = new Cube(1, 1, 1, "");
    private Matrix4 mat4;
    
    public static void main(String[] args) {
        new Demo3D()
        .setAppTitle("3D Demo")
        .setDimensions(800, 600)
        .setDesiredFPS(60)
        .setFrameBuff(2)
        .start();
    }

    @Override
    public void render(Graphics g) {
        theta += 0.01f * delta;
        Matrix4 matRotZ = new Matrix4(), matRotX = new Matrix4();
        
        matRotZ = Util.MatrixMakeRotationZ(theta * 0.5f);
        matRotX = Util.MatrixMakeRotationX(theta);

        mat4 = Util.MatrixMultiplyMatrix(matRotZ, matRotX);

        for (Triangle triangles : c.getTris()) {
            Vector3 v1, v2, v3; v1 = v2 = v3 = new Vector3();

            v1 = Util.MatrixMultiplyVector(mat4, triangles.p[0]);
			v2 = Util.MatrixMultiplyVector(mat4, triangles.p[1]);
			v3 = Util.MatrixMultiplyVector(mat4, triangles.p[2]);

            v1 = Util.VectorMul(v1, 0.5f);
            v2 = Util.VectorMul(v2, 0.5f);
            v3 = Util.VectorMul(v3, 0.5f);

            // the scale used to scale it back into screen space
            float scale = 1f; 

			v1.x += scale; v1.y += scale;
			v2.x += scale; v2.y += scale;
			v3.x += scale; v3.y += scale;

            v1.x *= 0.5f * (float)getWidth();
			v1.y *= 0.5f * (float)getHeight();
			v2.x *= 0.5f * (float)getWidth();
			v2.y *= 0.5f * (float)getHeight();
			v3.x *= 0.5f * (float)getWidth();
			v3.y *= 0.5f * (float)getHeight();

            g.setColor(Color.WHITE);
            g.drawLine((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y);
            g.drawLine((int)v2.x, (int)v2.y, (int)v3.x, (int)v3.y);
            g.drawLine((int)v3.x, (int)v3.y, (int)v1.x, (int)v1.y);
        }
    }

    @Override
    public void input() {}

}
