package com.Resources;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Vector;

public class ResourceManager implements IResourceManager {
    private static ResourceManager manager;
    private Vector<FileData> loadedFiles;

    public void loadAsset(SaveData data) {
        try {
            System.out.println(data.readString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveSettingsData() {
        //
    }

    public void SavePlayerData() {
        //
    }

    @Override
    public void SaveData(String fileName, String string) throws IOException {
        FileWriter f = new FileWriter("AppData/"+fileName, Charset.defaultCharset(), false);
        f.write(string);
        f.close();
    }

    @Override
    public FileData LoadFile(String path) throws IOException {
        String f = path.replace('.', ':');
        String[] split = f.split(":");
        String name = split[0], ext = split[1];
        BufferedReader fl = new BufferedReader(new FileReader("AppData/"+path, Charset.defaultCharset()));
        String d = null;String buff = "";
        while((d = fl.readLine()) != null) {
            buff = buff+d+"\n";
        }
        fl.close();
        return new FileData(name, ext, buff);
    }

    public void getLoadedFiles() {
        for (FileData file : loadedFiles) {
            System.out.println(file.getName());
        }
    }

    @Override
    public void CreateNewDir(String path) throws IOException {
        File newDir = new File("AppData/"+path);
        if(newDir != null) {
            newDir.mkdirs();
        }
    }

    private ResourceManager() {
        File[] folders = {
            new File("AppData/settings"),
            new File("AppData/saves"),
            new File("AppData/assets")
        };

        for(File dir : folders) {
            if(!dir.exists()) {
                dir.mkdirs();    
            }
        }
    }

    public static ResourceManager getNewInstance() {
        return (manager != null) ? manager : new ResourceManager();
    }

    public int[][] getImageAsPixelBuffer(BufferedImage img) {
        final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        final int width = img.getWidth(); final int height = img.getHeight();
        final boolean hasAlphaChannel = img.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        return result;
    }

    
}

/*
 * FILE STRUCTURE
 * 
 * IDENTIFIER -> file type eg: player, map/level, etc
 *      |
 *    Data
 *      |
 *     EOF (End Of File)
 *           
 */