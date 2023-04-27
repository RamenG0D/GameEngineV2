package com.Tile;

import java.util.List;

import javax.swing.text.html.parser.Entity;

//import com.Entities.Sprite;
import com.Renders.RenderingAttributes;

public interface Tile {

    public boolean isOpaque();

	public boolean isSolid();

	public void setOpaque(boolean b);

	public void setSolid(boolean b);

	public String getName();

	public String getTypeId();

	//public List<Sprite> getSprites();

	public List<Entity> getEntities();

	public void addEntity(Entity entity);

	//public void addSprite(Sprite sprite);

	public RenderingAttributes getRenderingAttributes();

	public void setRenderingAttributes(RenderingAttributes ra);

	public void setName(String text);

}
