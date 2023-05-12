package com.demos;

import java.awt.Color;
import java.awt.Graphics;

import com.Application.App;
import com.primitives.Matrix4;
import com.primitives.Quaternion;
import com.primitives.Shapes.Cube;
import com.primitives.Shapes.Triangle;
import com.primitives.Vectors.Vector3;

public class Demo3D extends App {

    public static void main(String[] args) {
        new Demo3D().run();
    }

    public Demo3D() {
        super("3D Demo", 800, 600, 60, 6, Color.black, null, false);
        mat4 = new Matrix4();

        final float near = 0.1f;
        final float far = 1000.0f;
        final float fov = 90.0f;
        final float aspectRatio = getWidth() / getHeight();
        final float fovRad = (float) (1 / Math.tan(fov * 0.5 / 180.0 * Math.PI));

        mat4.set(0, 0, aspectRatio * fovRad);
        mat4.set(1, 1, fovRad);
        mat4.set(2, 2, far / (far-near));
        mat4.set(3, 2, (-far * near) / (far - near));
        mat4.set(2, 3, 1.0f);
        mat4.set(3, 3, 0.0f);
    }

    private Matrix4 mat4;

    private Cube c = new Cube();

    @Override
    public void update(float delta) {}

    private float theta;

    @Override
    public void render(Graphics g) {
        theta += 0.02;

        Matrix4 
        matRotZ = new Matrix4(), 
        matRotX = new Matrix4();

        matRotX.rotateX(new Quaternion(theta, theta, theta, 0));
        matRotZ.rotateZ(new Quaternion(theta, theta, theta, 0));

        for (Triangle triangles : c.getTriangles()) {
            Triangle 
            projected = new Triangle(), 
            translated = new Triangle(), 
            rotatedZ = new Triangle(), 
            rotatedZX = new Triangle();
            Vector3 v1, v2, v3;

            v1 = projected.getV1();
            v2 = projected.getV2();
            v3 = projected.getV3();

            matRotZ.multiply(triangles.getV1(), rotatedZ.getV1());
			matRotZ.multiply(triangles.getV2(), rotatedZ.getV2());
			matRotZ.multiply(triangles.getV3(), rotatedZ.getV3());

			matRotX.multiply(rotatedZ.getV1(), rotatedZX.getV1());
			matRotX.multiply(rotatedZ.getV2(), rotatedZX.getV2());
			matRotX.multiply(rotatedZ.getV3(), rotatedZX.getV3());

            translated = rotatedZX;
			translated.getV1().setZ(rotatedZX.getV1().getZ() + 3);
			translated.getV2().setZ(rotatedZX.getV2().getZ() + 3);
			translated.getV3().setZ(rotatedZX.getV3().getZ() + 3);

            mat4.multiply(translated.getV1(), v1);
            mat4.multiply(translated.getV2(), v2);
            mat4.multiply(translated.getV3(), v3);

			v1.setX(v1.getX() + 1); v1.setY(v1.getY() + 1);
			v2.setX(v2.getX() + 1); v2.setY(v2.getY() + 1);
			v3.setX(v3.getX() + 1); v3.setY(v3.getY() + 1);
            
            v1.setX(v1.getX() * (getWidth()/2));
			v1.setY(v1.getY() * (getHeight()/2));
			v2.setX(v2.getX() * (getWidth()/2));
			v2.setY(v2.getY() * (getHeight()/2));
			v3.setX(v3.getX() * (getWidth()/2));
			v3.setY(v3.getY() * (getHeight()/2));
            
            System.out.println("x1: "+v1.getX()+" y1: "+v1.getY()+" x2: "+v2.getX()+" y2: "+v2.getY());

            g.setColor(Color.WHITE);
            g.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
            g.drawLine((int)v2.getX(), (int)v2.getY(), (int)v3.getX(), (int)v3.getY());
            g.drawLine((int)v3.getX(), (int)v3.getY(), (int)v1.getX(), (int)v1.getY());
        }

        /*try {Thread.sleep(10);} 
        catch(InterruptedException e) 
        {e.printStackTrace();}*/
    }

    @Override
    public void input() {}
    
}
