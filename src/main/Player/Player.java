package com.app.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import com.app.helper.Entity;
import java.awt.Point;
import com.app.helper.Vector2;

import java.awt.Image;

public class Player extends JComponent implements Entity {
    private Vector2 velocity = new Vector2();
    private Rectangle playerCollider;
    private double health;
    private Image img;
    public int x;
    public int y;
    //
    public Player(int x, int y, double health, Image PlayerSprite) {
        //
        this.health = health;
        this.x = x;
        this.y = y;
        this.playerCollider = new Rectangle(x, y, 65, 100);
        this.img = PlayerSprite;
        velocity.clamp(-5, 5);
        //
    }
    //
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //
        g.setColor(Color.RED);
        g.drawRect(playerCollider.x, playerCollider.y, playerCollider.width, playerCollider.height);
        //
        g.drawImage(img, x, y, null);
        //
    }
    //
    public Vector2 getVelocity() {
        return velocity;
    }
    //
    @Override
    public int getX() {
        return x;
    }
    //
    @Override
    public int getY() {
        return y;
    }
    //
	@Override
	public boolean isHostile() {
		return false;
	}
    //
	@Override
	public double getHealth() {
		return health;
	}
    //
	@Override
	public void setHealth(double health) {
		this.health += health;
	}
    //
    public Rectangle getCollider() {
        return playerCollider;
    }
    //
    public void setCollider(Rectangle playerCollider) {
        this.playerCollider = playerCollider;
    }
    //
    public boolean isColliding(Rectangle other) {
        if(playerCollider.intersects(other)) {
            return true;
        }
        return false;
    }
    //
    public boolean isColliding(Point other) {
        if(playerCollider.contains(other)) {
            return true;
        }
        return false;
    }
    //
    public void continueMoving() {
        //

        //
    }
    //
    @Override
    public void gravity() {
        velocity.add(0, gravity);
        this.y += gravity;
    }
}