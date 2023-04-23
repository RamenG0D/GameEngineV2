package com.Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

    @Override
    public void render() {
        //drawLine(0, 0, 0, 20);
        drawRect(0, 0, 60, 60, Color.BLUE.getRGB());
        /*g.setColor(Color.green);
        g.drawLine(p.x+15, p.y+15, (int)(p.x + p.dx * 5), (int)(p.y + p.dy * 5));*/
        //
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        ArrayList<Integer> px = new ArrayList<>();ArrayList<Integer> py = new ArrayList<>();
        double l = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        px.add(x1); px.add(x2); py.add(y1); py.add(y2);
        for(int i = 1; i < l; i++) {
            double d = i;
            int newX = (int) (x2 + (((x1 - y2) / (l) * d)));
            int newY = (int) (y2 + (((y1 - x2) / (l) * d)));
            System.out.println("nx: "+newX+" ny: "+newY);
            px.add(newX);
            py.add(newY);
        }
        //
        for (int j = 0; j < l; j++) {
            setPixel(px.get(j), py.get(j), Color.GREEN.getRGB());
        }
    }

    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
        /*if(gameState == GameState.Menu) { menu.show(); }
        else { menu.hide(); }*/
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
