package com.Entities;

public class Player {
    public double dx, dy, angle;
    public int x, y;
    //
    public Player(int x, int y) {
        //
        this.x = x;
        this.y = y;
        this.angle = 90;
        this.dx = Math.cos(angle*Math.PI/180.0) * 5;
        this.dy = -Math.sin(angle*Math.PI/180.0) * 5;
        //
    }
    /*
     * dx = Math.cos(angle*Math.PI/180.0);
     * dy = -Math.sin(angle*Math.PI/180.0);
     * g.drawLine(x+6, y+6, (int)((x+7) + dx * 25), (int)((y+7) + dy * 25));
     */
}
