package com.Tile;

public interface TileMap {

    public Tile getTileAtPos(double x, double y);

	public void setTileAtPos(double x, double y, Tile tile);

	public Tile getTileAt(int x, int y);

	public void setTileAt(int x, int y, Tile tile);

	public boolean isEditable();

	public int getWidth();

	public int getHeight();
}
