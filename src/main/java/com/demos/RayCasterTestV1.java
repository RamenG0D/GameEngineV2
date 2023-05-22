package com.demos;

import java.awt.Color;
import java.awt.Graphics;

import com.Application.App;

public class RayCasterTestV1 extends App {

    public RayCasterTestV1() {
        px = 150;py = 400;pa = 90;
        pdx = (float) Math.cos(degToRad((int) pa));
        pdy = (float) -Math.sin(degToRad((int) pa));
    }

    public static void main(String[] args) {
        new RayCasterTestV1()
        .setAppTitle("RayCaster")
        .setDimensions(800, 600)
        .setDesiredFPS(60)
        .setFrameBuff(2)
        .start();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Graphics g) {
        /*drawMap2D();
        drawPlayer2D();*/
        drawRays2D();
    }

    @Override
    public void input() {
        if (keypressed("A")) {
            pa += 5; pa = FixAng((int) pa);
            pdx = (float) Math.cos(degToRad((int) pa));
            pdy = -(float) Math.sin((double) degToRad((int) pa));
        }
        if (keypressed("D")) {
            pa -= 5; pa = FixAng((int) pa);
            pdx = (float) Math.cos(degToRad((int) pa));
            pdy = -(float) Math.sin((double) degToRad((int) pa));
        }
        if (keypressed("W")) {
            px += pdx * 5;
            py += pdy * 5;
        }
        if (keypressed("S")) {
            px -= pdx * 5;
            py -= pdy * 5;
        }
    }

    private static final int mapx = 8;
    private static final int mapy = 8;
    private static final int mapS = 64;
    private static final int map[] = {
        1, 1, 1, 1, 1, 1, 1, 1,
        1, 0, 1, 0, 0, 0, 0, 1,
        1, 0, 1, 0, 0, 0, 0, 1,
        1, 0, 1, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 1, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 1,
        1, 1, 1, 1, 1, 1, 1, 1
    };

    void drawMap2D() {
        int x, y, xo, yo; Color c;
        for (y = 0; y < mapy; y++) {
            for (x = 0; x < mapx; x++) {
                if(map[y*mapx+x]==1) c = Color.lightGray;
                else c = Color.gray;
                xo = x * mapS;
                yo = y * mapS;
                g.setColor(c);
                g.drawRect(xo+1, yo+1, xo+1, mapS+yo-1);
                g.drawRect(mapS+xo-1, mapS+yo-1, mapS+xo-1, 0+yo+1);
            }
        }
    }

    private float degToRad(int a) {
        return (float) (a * Math.PI / 180.0);
    }

    private int FixAng(int a) {
        if (a > 359) a -= 360;
        if (a < 0) a += 360;
        return a;
    }

    private float px, py, pdx, pdy, pa;

    private void drawRays2D() {
        int r, mx=0, my=0, mp, dof;
        float vx, vy, rx, ry, ra, disV, disH, xo = 0, yo = 0;

        ra = FixAng((int) (pa + 30)); // ray set back 30 degrees

        for (r = 0; r < 60; r++) {
            // ---Vertical---
            dof = 0;
            disV = 100000;
            float Tan = (float) Math.tan(degToRad((int) ra));
            if (Math.cos(degToRad((int) ra)) > 0.001) {
                rx = (((int) px >> 6) << 6) + 64;
                ry = (px - rx) * Tan + py;
                xo = 64;
                yo = -xo * Tan;
            } // looking left
            else if (Math.cos(degToRad((int) ra)) < -0.001) {
                rx = (float) ((((int) px >> 6) << 6) - 0.0001);
                ry = (px - rx) * Tan + py;
                xo = -64;
                yo = -xo * Tan;
            } // looking right
            else {
                rx = px;
                ry = py;
                dof = 8;
            } // looking up or down. no hit

            while (dof < 8) {
                mx = (int) (rx) >> 6;
                my = (int) (ry) >> 6;
                mp = my * mapx + mx;
                if (mp > 0 && mp < mapx * mapy && map[mp] == 1) {
                    dof = 8;
                    disV = (float) (Math.cos(degToRad((int) ra)) * (rx - px) - Math.sin(degToRad((int) ra)) * (ry - py));
                } // hit
                else {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                } // check next horizontal
            }
            vx = rx;
            vy = ry;

            dof = 0;
            disH = 100000;
            Tan = (float) (1.0 / Tan);
            if (Math.sin(degToRad((int) ra)) > 0.001) {
                ry = (float) ((((int) py >> 6) << 6) - 0.0001);
                rx = (py - ry) * Tan + px;
                yo = -64;
                xo = -yo * Tan;
            } // looking up
            else if (Math.sin(degToRad((int) ra)) < -0.001) {
                ry = (((int) py >> 6) << 6) + 64;
                rx = (py - ry) * Tan + px;
                yo = 64;
                xo = -yo * Tan;
            } else {
                rx = px;
                ry = py;
                dof = 8;
            }

            while(dof < 8) {
                mx = (int) (rx) >> 6;
                my = (int) (ry) >> 6;
                mp = my * mapx + mx;
                if (mp > 0 && mp < mapx * mapy && map[mp] == 1) {
                    dof = 8;
                    disH = (float)(Math.cos(degToRad((int) ra)) * (rx - px) - Math.sin(degToRad((int) ra)) * (ry - py));
                } // hit
                else {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                } // check next horizontal
            }

            if (disV < disH) {
                rx = vx;
                ry = vy;
                disH = disV;
                g.setColor(Color.blue);
                g.drawLine((int) px, (int) py, (int) rx, (int) ry);
            }
            else {
                g.setColor(Color.BLACK);
                g.drawLine((int) px, (int) py, (int) rx, (int) ry);
            }

            int ca = FixAng((int)(pa-ra)); 
            disH=(float) (disH*Math.cos(degToRad(ca)));
            int lineH = (int) ((mapS * getHeight()) / disH);
            if (lineH > getHeight()) {
                lineH = getHeight();
            } // line height and limit
            int lineOff = 250 - (lineH >> 1);// line offset
            int l = (int)(r * 8); int rgb = (int)(Math.abs(Math.cos(192-(disH/disV)))*70);
            if(rgb > 255) rgb = 255; if(rgb < 0) rgb = 0;

            g.setColor(new Color(rgb, rgb, rgb));
            g.drawRect((int)l, (int)lineOff, (int)l, (int)(lineOff + lineH));

            ra = FixAng((int)(ra - 1));// go to next ray
        }
    }

}
