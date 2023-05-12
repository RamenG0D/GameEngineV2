package com.doom3d.renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import com.Application.App;
import com.primitives.Transform;
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
    public void update(float delta) {}

    private Cube c = new Cube();

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        for(Triangle tris : c.getTriangles()) {
            //final double px = player.x, py = player.y, pz = player.z;
            final int sW2 = getWidth()/2, sH2 = getHeight()/2;
            final double size = 10;final Graphics gl = g;
            Thread[] threads = new Thread[2];

            for(int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int x1 = (int)tris.getV1().getX();int y1 = (int)tris.getV1().getY();
                        int x2 = (int)tris.getV2().getX();int y2 = (int)tris.getV2().getY();
                        int x3 = (int)tris.getV3().getX();int y3 = (int)tris.getV3().getY();
                        x1 += sW2-size; y1 += sH2+size;
                        x2 += sW2+size; y2 += sH2-size;
                        x3 += sW2-size; y3 += sH2+size;

                        /*x1 += Math.cos(px);
                        y1 += -Math.sin(py);

                        x2 += -Math.cos(px);
                        y2 += Math.sin(py);

                        x3 += Math.cos(px);
                        y3 += -Math.sin(py);
                        
                        x1 += px-pz;y1 += py-pz;
                        x2 += px+pz;y2 += py+pz;
                        x3 += px+pz;y3 += py+pz;*/

                        x1 *= size;y1 *= size;
                        x2 *= size;y2 *= size;
                        x3 *= size;y3 *= size;

                        x1 += Math.cos(player.x);
                        y1 += -Math.sin(player.y);

                        x2 += -Math.cos(player.x);
                        y2 += Math.sin(player.y);

                        x3 += Math.cos(player.x);
                        y3 += -Math.sin(player.y);
                        
                        x1 += player.x+player.z;y1 += player.y+player.z;
                        x2 += player.x+player.z;y2 += player.y+player.z;
                        x3 += player.x+player.z;y3 += player.y+player.z;

                        gl.drawLine(x1, y1, x2, y2);
                        gl.drawLine(x2, y2, x3, y3);
                        gl.drawLine(x3, y3, x1, y1);
                        
                        //System.out.println("x: "+x1+" y: "+y1);
                    }
                });

                threads[i].start();
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
