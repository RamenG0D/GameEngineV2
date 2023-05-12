package com.raycaster.utils;
import java.util.List;

import com.raycaster.Entities.IEntity;
import com.raycaster.IO.Saveable;
import com.raycaster.Tile.ITileMap;

public interface IWorld extends Saveable<IWorld>, Updateable {
	/**
	 * Returns TileMap of World
	 * 
	 * @return
	 */
	public ITileMap getTileMap();

	/**
	 * Returns List of Entities currently in World
	 * 
	 * @return
	 */
	public List<IEntity> getEntities();
}
