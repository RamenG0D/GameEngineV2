package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import com.Application.App;
import com.primitives.Shapes.Cube;
import com.primitives.Shapes.Triangle;
import com.Math3D.*;

public class Demo3D extends App {

    public static void main(String[] args) {
        new Demo3D().run();
    }

    public Demo3D() {
        super("3D Demo", 800, 600, 60, null, Color.black, null, false);
        mat4 = new Matrix4(); Matrix.getOrtho(0, getWidth(), 0, getHeight(), 0.1f, 1000f, mat4);
    }    

    @Override
    public void update(float delta) {}

    private float theta; private Cube c = new Cube(1, 1, 1);
    private Matrix4 mat4;

    @Override
    public void render(Graphics g) {
        theta += 0.01 * elapsedTime;
        Matrix4 matRotZ = new Matrix4(), matRotX = new Matrix4();
        
        Matrix.getRotation(theta, 1, 0, 0, matRotZ);
        Matrix.getRotation(theta, 0, 1, 0, matRotX);

        for (Triangle triangles : c.getTriangles()) {
            Triangle rotatedZ = new Triangle(), rotatedZX = new Triangle();
            Vector3 v1, v2, v3; v1 = v2 = v3 = new Vector3();

            Matrix.mult(matRotZ, triangles.getV1(), rotatedZ.getV1());
			Matrix.mult(matRotZ, triangles.getV2(), rotatedZ.getV2());
			Matrix.mult(matRotZ, triangles.getV3(), rotatedZ.getV3());

			Matrix.mult(matRotX, rotatedZ.getV1(), rotatedZX.getV1());
			Matrix.mult(matRotX, rotatedZ.getV2(), rotatedZX.getV2());
			Matrix.mult(matRotX, rotatedZ.getV3(), rotatedZX.getV3());
            
            v1 = rotatedZX.getV1();
            v2 = rotatedZX.getV2();
            v3 = rotatedZX.getV3();

            Vector.mult(0.5f, v1);
            Vector.mult(0.5f, v2);
            Vector.mult(0.5f, v3);

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
