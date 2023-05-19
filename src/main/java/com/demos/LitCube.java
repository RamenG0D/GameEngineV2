package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import com.Application.App;
import com.Renders.Perspective3D;
import com.primitives.Mesh;
import com.primitives.Vector3;
import com.primitives.World;
import com.primitives.Shapes.Cube;

public class LitCube extends App {
    
    public static void main(String[] args) {
        new LitCube().run();
    }

    public LitCube() {
		super("demo...", 600, 600, 60, 2, Color.BLACK, null, false);
		addCustomKey("SHIFT", KeyEvent.VK_SHIFT);
		addCustomKey("SPACE", KeyEvent.VK_SPACE);
		world.addMesh(c);
		//world.addMesh(m);
		cam = new Perspective3D(90f, 0.1f, 1000f, 0f, 0f, getWidth(), getHeight(), new Vector3(0, 0, 0));
	}

    @Override
    public void update(float delta) {}

    private float theta; private Cube c = new Cube(1, 1, 1, "");
	//private Mesh m = new Mesh("src/main/java/com/demos/mountains.obj");
	private World world = new World(); private Perspective3D cam;

    @Override
    public void render(Graphics g) {
        theta += 0.01f * elapsedTime;
		cam.setSize(getHeight());
		cam.setRotation(theta);
		cam.render(g, world);
	}

    @Override
    public void input() {
		if(keypressed("w")) {
			cam.getPosition().z += 0.1 * elapsedTime;
		}
		if(keypressed("s")) {
			cam.getPosition().z -= 0.1 * elapsedTime;
		}
		if(keypressed("a")) {
			cam.getPosition().x += 0.1 * elapsedTime;
		}
		if(keypressed("d")) {
			cam.getPosition().x -= 0.1 * elapsedTime;
		}

		if(keypressed("shift")) {
			cam.getPosition().y -= 0.1 * elapsedTime;
		}
		if(keypressed("space")) {
			cam.getPosition().y += 0.1 * elapsedTime;
		}
    }

}
