package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import com.Entities.Player;
import com.utils.App;

public class PlayerTest extends App {
    //private MainMenu menu = new MainMenu();
    private Player p = new Player(15, 40);
    //
    public PlayerTest(String title, int width, int height, int desiredFps, Integer frameBuffer, Color BackgroundColor) {
        super(title, width, height, desiredFps, frameBuffer, BackgroundColor, null);
        //
        addCustomKey("Q", KeyEvent.VK_Q); // Example of how to add a new key other than the defaults -> [w,a,s,d,esc,spc(space)]
    }

    @Override
    public void render() {
        p.dx = Math.cos(p.angle*Math.PI/180.0) * 5;
        p.dy = -Math.sin(p.angle*Math.PI/180.0) * 5;

        getScreen().drawRect(p.x, p.y, 32, 32, Color.RED.getRGB());
        getScreen().drawLine(p.x+15, p.y+15, (int)((p.x+15) + p.dx * 10), (int)((p.y+15) + p.dy * 10), Color.GREEN.getRGB());
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

    @Override
    public void deprecatedGraphics(Graphics g) {
        g.setColor(Color.gray);
        g.drawRect(30, 30, 100, 100);
    }
}
