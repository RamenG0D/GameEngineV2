package com.Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;
import com.Entities.Player;
import com.utils.App;

public class Main extends App {
    //private MainMenu menu = new MainMenu();
    private Player p = new Player(10, 10, 0);
    //
    public Main(String title, int width, int height, int desiredFps, Integer frameBuffer/*, Camera cam*/) {
        super(title, width, height, desiredFps, frameBuffer/*, cam*/);
        //
        addCustomKey("Q", KeyEvent.VK_Q); // Example of how to add a new key other than the defaults -> [w,a,s,d,esc,spc(space)]
    }

    private Color color = new Color(0, 0, 0);

    @Override
    public void render() {
        //pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        drawLine(0, 0, 20, 20);
        //
        g.setColor(Color.green);
        g.drawLine(p.x+15, p.y+15, (int)(p.x + p.dx * 5), (int)(p.y + p.dy * 5));
        //
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        //
        float dx, dy, m;
        int px, py;
        //
        dx = x2 - x1;
        dy = y2 - y1;
        //
        m = dy / dx;
        /*
         * y = mx+b
         */
        //
        px = (int)((m*0)+y1);
        py = (int)((m*6)+y1);
        System.out.println("slope: "+m+" dx: "+dx+" dy: "+dy+" px: "+px+" py: "+py);
        /*
         * y2 - y1
         * ------- = m -> (slope)
         * x2 - x1
         */
        //
    }

    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
        /*if(gameState == GameState.Menu) { menu.show(); }
        else { menu.hide(); }*/
        input();
    }
    
    public static void main(String[] args) {
        new Main("Test Application", 800, 600, 60, 3/*, cam*/).run();
    }
    //
    public GameState gameState = GameState.Game;
    //
    public enum GameState {
        Game,
        Menu
    }
    //
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
        if(keypressed("esc")) {
            gameState = GameState.Menu;
        }
        //
        p.dx = Math.cos(p.angle*Math.PI/180.0) * 5;
        p.dy = -Math.sin(p.angle*Math.PI/180.0) * 5;
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
    //
}
