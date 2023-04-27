package com.utils;

import java.util.List;

import com.Entities.Entity;
import com.Tile.TileMap;

public interface World {

    public TileMap getTileMap();
    
    public List<Entity> getEntities();

}
