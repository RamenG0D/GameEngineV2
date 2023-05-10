package com.doom3d.renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.Application.App;
import com.primitives.Shapes.Cube;
import com.primitives.Shapes.Triangle;

public class Test extends App {

    public Test() {
        super();

        addCustomKey("SHIFT", KeyEvent.VK_SHIFT);
        /*addCustomKey("Q", KeyEvent.VK_Q);
        addCustomKey("E", KeyEvent.VK_E);
        addCustomKey("O", KeyEvent.VK_O);
        addCustomKey("P", KeyEvent.VK_P);*/
    }

    public static void main(String[] args) {
        new Test().run();
    }

    @Override
    public void input() {
        if(keypressed("w")) {
            player.z += 1;
        }
        if(keypressed("s")) {
            player.z -= 1;
        }
        if(keypressed("a")) {
            player.x += 1;
        }
        if(keypressed("d")) {
            player.x -= 1;
        }
        if(keypressed("spc")) {
            player.y += 1;
        }
        if(keypressed("shift")) {
            player.y -= 1;
        }

        //System.out.println("x: "+player.x+" y: "+player.y+" z: "+player.z);
    }

    @Override
    public void render() {}

    @Override
    public void update(float delta) {}

    private Cube c = new Cube(100);

    @Override
    public void deprecatedGraphics(Graphics g) {
        g.setColor(Color.WHITE);
        for(Triangle tris : c.getTriangles()) {
            int x1 = (int)tris.getV1().getX();int y1 = (int)tris.getV1().getY();
            int x2 = (int)tris.getV2().getX();int y2 = (int)tris.getV2().getY();
            int x3 = (int)tris.getV3().getX();int y3 = (int)tris.getV3().getY();

            //System.out.println("x: "+x2+" y: "+y2);

            x1 += getWidth()/2-c.getSize();y1 += getHeight()/2+c.getSize();
            x2 += getWidth()/2+c.getSize();y2 += getHeight()/2-c.getSize();
            x3 += getWidth()/2-c.getSize();y3 += getHeight()/2+c.getSize();
            
            x1 += Math.cos(player.x);
            y1 += -Math.sin(player.y);

            x2 += -Math.cos(player.x);
            y2 += Math.sin(player.y);

            x3 += Math.cos(player.x);
            y3 += -Math.sin(player.y);
            
            x1 += player.x-player.z;y1 += player.y+player.z;
            x2 += player.x+player.z;y2 += player.y-player.z;
            x3 += player.x+player.z;y3 += player.y+player.z;
            
            /*x1 *= c.getSize();y1 *= c.getSize();
            x2 *= c.getSize();y2 *= c.getSize();
            x3 *= c.getSize();y3 *= c.getSize();*/

            System.out.println("x: "+x1+" y: "+y1);

            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x1, y1);
        }
    }

    private Player player = new Player(0, 0, 0);

    private class Player {
        public int x, y, z;

        public Player(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
