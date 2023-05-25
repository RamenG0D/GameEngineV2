package com.IO;

import java.io.File;
import java.io.IOException;

public class ResourceManager {
    public static final String SOURCE_DIR = "GameData";
    public static final String ASSETS_DIR = "Assets";
    public static final String SAVES_DIR = "Saves";
    private ResourceManager mgr = null;

    private ResourceManager(){}

    public ResourceManager getInstance() {
        if(mgr == null) mgr = new ResourceManager();
        return mgr;
    }

    public void initDirs() {
        File[] dirs = {
            new File("GameData/Assets"),
            new File("GameData/Saves")
        };

        for (File dir : dirs) {
            if(!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    public void makeDir(String directory) {
        File dir = new File(directory);
        if(!dir.exists()) dir.mkdirs();
    }

    private File dir = new File(SOURCE_DIR);
    private File file = null;

    public void moveToDir(String path) {
        dir = new File(SOURCE_DIR+path);
    }

    public void moveBack() {
        dir = new File(dir.getParent());
    }

    public String cwdToString() {
        return "Current Working Dir->"+dir.getPath();
    }

    public void toDataDir() {
        dir = new File(SOURCE_DIR);
    }

    public void newFile(String filePath) throws IOException {
        file = new File(SOURCE_DIR+filePath);
        if(!file.exists()) file.createNewFile();
    }

    /** <h3>gets a created file from a dir relative to the cwd of the RM instance</h3> */
    public File getFile(String file) throws Exception {
        File f = new File(dir.getPath()+file);
        if(!f.exists() || f == null) throw new Exception("File doesn't exist!");
        return f;
    }

}
