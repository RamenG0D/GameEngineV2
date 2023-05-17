package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import com.Application.App;

public class PlayerTest extends App {
    private class Player {public double dx, dy, angle;public int x, y;public Player(int x, int y) {this.x = x;this.y = y;this.angle = 90;this.dx = Math.cos(angle*Math.PI/180.0) * 5;this.dy = -Math.sin(angle*Math.PI/180.0) * 5;}}
    //private MainMenu menu = new MainMenu();
    private Player p = new Player(15, 40);
    //
    public PlayerTest(String title, int width, int height, int desiredFps, Integer frameBuffer, Color BackgroundColor) {
        super(title, width, height, desiredFps, frameBuffer, BackgroundColor, null, true);
        //
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
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
    }
    
    public static void main(String[] args) {
        new PlayerTest("Test Application", 800, 600, 60, 3, Color.BLACK)
        .run();
    }

    public GameState gameState = GameState.Game;

    public enum GameState {
        Game,
        Menu
    }

    @Override
    public void input() { // an elagent solution such as if( keypressed( "{KEY}" ) ) {KEY} being the name / letter of the key

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
