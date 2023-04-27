package com.Resources;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IResourceManager {

    public void SaveData(String fileName, String data) throws IOException;

    public FileData LoadFile(String path) throws IOException, URISyntaxException;

    public void CreateNewDir(String path) throws IOException;

}

/*
 * --PLAYER File--
 * Name:player_name,\n
 * Health:player_health,\n
 * Position:player_pos,\n
 * Inventory:{ wood:number_of_items, OtherItem:num }\n
 */
