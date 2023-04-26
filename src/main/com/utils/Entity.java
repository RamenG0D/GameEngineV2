package com.utils;

import com.Entities.Sprite;
import com.Tile.Tile;

public interface Entity {
    
    public Sprite getSprite();

    public float getX();

    public float getY();

    public int getWidth();

    public int getHeight();

    public World getWorld();

    public Tile getTile();

    public void setTile(Tile tile);

    public float getViewDirectionX();

	public float getViewDirectionY();

}
