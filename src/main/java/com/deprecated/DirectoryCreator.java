package com.deprecated;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DirectoryCreator {
    private ArrayList<String> dirs = new ArrayList<>();

    public DirectoryCreator() {}
    
    public void initDefaultDirs() {
        File[] folders = {
            new File("AppData/settings"),
            new File("AppData/saves"),
            new File("AppData/assets")
        };

        for(File dir : folders) {
            if(!dir.exists()) {
                dir.mkdirs();  
            }
            dirs.add(dir.getPath());
        }
        for(String dir : dirs) {
            System.out.println(dir);
        }
    }

    public void CreateNewDir(String path) throws IOException {
        File newDir = new File("AppData/"+path);
        if(!newDir.exists()) {
            newDir.mkdirs();
        } else try
        {throw new RuntimeException(
            "Couldn't successfully create the new dir perhaps it already exists or thers a file with the same name"
            );
        }catch(Exception e){e.printStackTrace();}
    }

}
