package com.app.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import com.app.helper.GameObject;

public class Box implements GameObject {
    private Point rightSide;
    private Point leftSide;
    private Rectangle rect;
    //
    public Box(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
        //
        rightSide = new Point((int) rect.getCenterX() - (int)(rect.getCenterX() / 4.8), (int) rect.getCenterY());
        leftSide = new Point((int) rect.getCenterX() + (int)(rect.getCenterX() / 4.8), (int) rect.getCenterY());
        //
    }
    //
	@Override
	public void paint(Graphics g) {
		//
        g.setColor(Color.GREEN);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        //
	}
    //
	@Override
	public Rectangle getRect() {
		return rect;
	}
    //
    public Point getRightSide() {
        return rightSide;
    }
    //
    public Point getLeftSide() {
        return leftSide;
    }
    //
}
