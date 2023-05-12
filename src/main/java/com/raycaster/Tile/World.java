package com.raycaster.Tile;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.raycaster.Entities.IEntity;
import com.raycaster.IO.IGameFile;
import com.raycaster.utils.IWorld;

public class World implements IWorld {

	protected ITileMap m_tileMap;
	protected Vector<IEntity> m_entities;
	
	public World(ITileMap p_tileMap){
		m_tileMap = p_tileMap;
	}
	
	@Override
	public boolean saveToFile(IGameFile gameFile) throws IOException {
	    gameFile.saveSaveable(m_tileMap);
		return true;
	}

	@Override
	public boolean loadFromFile(IGameFile gameFile) throws IOException {
		m_tileMap = (ITileMap) gameFile.readSaveable();
		return true;
	}

	@Override
	public String getTag() {
		return "World";
	}

	@Override
	public void onUpdate(float timeDiff) {
		//Do nothing yet
	}

	@Override
	public ITileMap getTileMap() {
		return m_tileMap;
	}

	@Override
	public List<IEntity> getEntities() {
		return m_entities;
	}

}
