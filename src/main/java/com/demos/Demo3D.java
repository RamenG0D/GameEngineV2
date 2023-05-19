package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import com.Application.App;
import com.demos.Util.Matrix4;
import com.primitives.Vector3;
import com.primitives.Shapes.Cube;
import com.primitives.Shapes.Triangle;

public class Demo3D extends App {

    public static void main(String[] args) {
        new Demo3D().run();
    }

    public Demo3D() {
        super("3D Demo", 800, 600, 60, null, Color.black, null, false);
        //mat4 = Util.MatrixMakeProjection(90f, (float)(getHeight()/getWidth()), 0.1f, 1000f);
    }

    @Override
    public void update(float delta) {}

    private float theta; private Cube c = new Cube(1, 1, 1, "");
    private Matrix4 mat4;

    @Override
    public void render(Graphics g) {
        theta += 0.01 * elapsedTime;
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

			v1.x += 1; v1.y += 1;
			v2.x += 1; v2.y += 1;
			v3.x += 1; v3.y += 1;

            v1.x *= (getWidth()/2);
			v1.y *= (getHeight()/2);
			v2.x *= (getWidth()/2);
			v2.y *= (getHeight()/2);
			v3.x *= (getWidth()/2);
			v3.y *= (getHeight()/2);

            g.setColor(Color.WHITE);
            g.drawLine((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y);
            g.drawLine((int)v2.x, (int)v2.y, (int)v3.x, (int)v3.y);
            g.drawLine((int)v3.x, (int)v3.y, (int)v1.x, (int)v1.y);
        }
    }

    @Override
    public void input() {}
    
}
