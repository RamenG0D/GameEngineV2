package com.demos;

import java.awt.Graphics;
import com.Application.App;
import com.Renders.Camera;
import com.Renders.DoomCamera;
public class Minecraft extends App {
    
    public Minecraft() {
        this.setAppTitle("Minecraft Clone");
        this.setDimensions(600, 600);
        this.setDesiredFPS(60);
        this.setDebug(true);
    }

    private Camera cam = new DoomCamera();

    public static void main(String[] args) {
        new Minecraft().run();
    }

    @Override
    public void update(float delta) {}

    @Override
    public void render(Graphics g) {
        cam.render(g);
    }

    @Override
    public void input() {
        if(keypressed("w")) cam.move(0, -1, 0);
        if(keypressed("s")) cam.move(0, 1, 0);
        if(keypressed("a")) cam.move(-1, 0, 0);
        if(keypressed("d")) cam.move(1, 0, 0);
    }

}
