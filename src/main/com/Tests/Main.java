package com.Tests;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import com.Entities.Player;
import com.utils.App;

public class Main extends App {
    //private MainMenu menu = new MainMenu();
    private Player p = new Player(15, 40);
    //
    public Main(String title, int width, int height, int desiredFps, Integer frameBuffer/*, Camera cam*/) {
        super(title, width, height, desiredFps, frameBuffer/*, cam*/);
        //
        addCustomKey("Q", KeyEvent.VK_Q); // Example of how to add a new key other than the defaults -> [w,a,s,d,esc,spc(space)]
    }

    @Override
    public void render() {
        //
        p.dx = Math.cos(p.angle*Math.PI/180.0) * 5;
        p.dy = -Math.sin(p.angle*Math.PI/180.0) * 5;
        drawRect(0, 0, getWidth(), getHeight(), Color.BLACK.getRGB());
        drawRect(p.x, p.y, 32, 32, Color.RED.getRGB());
        drawLine(p.x+15, p.y+15, (int)((p.x+15) + p.dx * 10), (int)((p.y+15) + p.dy * 10));
        //
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1;
        double l = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        for(int t = 1; t < l+1; t++) {
            int newX = (int)(x1 + (((dx / l) * t)));
            int newY = (int)(y1 + (((dy / l) * t)));
            if(newX > getWidth() - 1 || newY > getHeight() - 1) continue;
            if(newX < 0 || newY < 0) continue;
            getScreen().setRGB(newX, newY, Color.GREEN.getRGB());
        }
        //
    }

    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
    }
    
    public static void main(String[] args) {
        new Main("Test Application", 800, 600, 60, 3)
        .run();
    }

    public GameState gameState = GameState.Game;

    public enum GameState {
        Game,
        Menu
    }

    @Override
    public void input() { // an elagent solution such as if( keypressed( "{KEY}" ) ) {KEY} being the name / letter of the key
        //
        if(keypressed("w")) {
            p.x += p.dx*5;
            p.y += p.dy*5;
        }
        if(keypressed("a")) {
            p.angle += 2;
            FixAng(p.angle);
        }
        if(keypressed("s")) {
            p.x -= p.dx*5;
            p.y -= p.dy*5;
        }
        if(keypressed("d")) {
            p.angle -= 2;
            FixAng(p.angle);
        }
        if(keypressed("spc")) {
            p.x = 15;
            p.y = 40;
        }
        //
    }
    //
    private void FixAng(double a) {
        if(a < 0) a += 360;
        if(a > 359) a -= 360;
    }
    //
    @SuppressWarnings("unused")
    private double DegToRad(double a) {
        return a * Math.PI/180.0;
    }

    @Override
    public void deprecatedGraphics(float delta) {
        //
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibre", Font.PLAIN, 12));
        g.drawString("fps: " + fps, 20, 50);
        //
    }
}
