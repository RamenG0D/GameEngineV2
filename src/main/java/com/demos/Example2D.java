package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import com.Application.App;

public class Example2D extends App {
    private class Player{public double dx, dy, angle;public int x, y;public Player(int x, int y) {this.x = x;this.y = y;this.angle = 90;this.dx = Math.cos(angle*Math.PI/180.0) * 5;this.dy = -Math.sin(angle*Math.PI/180.0) * 5;}}
    private Player p = new Player(15, 40);

    public Example2D(String title, int width, int height, int desiredFps, Integer frameBuffer) {
        this.setAppTitle(title);
        this.setDimensions(width, height);
        this.setFrameBuff(frameBuffer);
        this.setDesiredFPS(desiredFps);

        addCustomKey("Q", KeyEvent.VK_Q); // Example of how to add a new key other than the defaults -> [w,a,s,d,esc,spc(space)]
    }

    @Override
    public void render(Graphics g) {
        p.dx = Math.cos(p.angle*Math.PI/180.0) * 5;
        p.dy = -Math.sin(p.angle*Math.PI/180.0) * 5;

        g.setColor(Color.RED);
        g.fillRect(p.x, p.y, 32, 32);
        g.setColor(Color.GREEN);
        g.drawLine(p.x+15, p.y+15, (int)((p.x+15) + p.dx * 10), (int)((p.y+15) + p.dy * 10));
    }
    
    @Override
    // delta is the time between now and the last frame or the FPS    
    public void update(float delta) {}

    public static void main(String[] args) {
        new Example2D("Test Application", 800, 600, 60, 2)
        .run();
    }

    @Override
    public void input() { // an elagent solution such as if( keypressed( "{KEY}" ) ) {KEY} being the name / letter of the key

        if(keypressed("w")) {
            p.x += p.dx*2;
            p.y += p.dy*2;
        }
        if(keypressed("a")) {
            p.angle += 2;
            FixAng(p.angle);
        }
        if(keypressed("s")) {
            p.x -= p.dx*2;
            p.y -= p.dy*2;
        }
        if(keypressed("d")) {
            p.angle -= 2;
            FixAng(p.angle);
        }
        if(keypressed("spc")) {
            p.x = 15;
            p.y = 40;
        }

    }

    private void FixAng(double a) {
        if(a < 0) a += 360;
        if(a > 359) a -= 360;
    }

    @SuppressWarnings("unused")
    private double DegToRad(double a) {
        return a * Math.PI/180.0;
    }
}
