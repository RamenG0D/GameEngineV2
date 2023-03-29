package com.app.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import com.app.helper.GameObject;

public class Ground implements GameObject {
	private Rectangle rect;
	//
	public Ground(int x, int y, int width, int height) {
		//
		rect = new Rectangle(x, y, width, height);
		//
	}
    //
	@Override
	public void paint(Graphics g) {
		//
		g.setColor(Color.RED);
		g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		//
	}
	//
	public Rectangle getRect() {
		return rect;
	}
}
