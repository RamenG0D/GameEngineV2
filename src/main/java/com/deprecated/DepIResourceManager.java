package com.deprecated;

import java.io.IOException;
import java.net.URISyntaxException;

public interface DepIResourceManager {

    public void SaveData(String fileName, String data) throws IOException;

    public FileData LoadFile(String path) throws IOException, URISyntaxException;

    public void CreateNewDir(String path) throws IOException;

}
