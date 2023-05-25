package com.demos;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import com.Application.App;
import com.Renders.Perspective3D;
import com.primitives.Mesh;
import com.primitives.Vector3;
import com.primitives.World;

public class LitCube extends App {
    
    public static void main(String[] args) {
        new LitCube()
		.setAppTitle("demo...")
		.setDimensions(800, 600)
		.setDesiredFPS(60)
		.setFrameBuff(2)
		.setDebug(true)
		.start();
    }

    public LitCube() {
		addCustomKey("SHIFT", KeyEvent.VK_SHIFT);
		addCustomKey("SPACE", KeyEvent.VK_SPACE);
		world.addMesh(m);
		cam = new Perspective3D(90f, 0.01f, 1000f, 0f, 0f, getWidth(), getHeight(), new Vector3(0, 15, 0));
	}

    @Override
    public void update(float delta){this.delta=delta;}

	private Mesh m = new Mesh("assets/mountains.obj");
	private World world = new World(); private Perspective3D cam;
	private float delta;

    @Override
    public void render(Graphics g) {
		cam.setSize(getWidth(), getHeight());
		cam.render(g, world);
	}

    @Override
    public void input() {
		if(keypressed("w")) {
			cam.getPosition().z += 0.01f * delta;
		}
		if(keypressed("s")) {
			cam.getPosition().z -= 0.01f * delta;
		}
		if(keypressed("a")) {
			cam.getPosition().x += 0.01f * delta;
		}
		if(keypressed("d")) {
			cam.getPosition().x -= 0.01f * delta;
		}
		if(keypressed("shift")) {
			cam.getPosition().y -= 0.01f * delta;
		}
		if(keypressed("space")) {
			cam.getPosition().y += 0.01f * delta;
		}
    }

}
