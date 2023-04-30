package com.deprecated;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import com.utils.Tuple;

public class DepResourceManager implements DepIResourceManager {
    private static DepResourceManager manager;

    @Override
    public void SaveData(String fileName, String data) throws IOException {
        String filePath = "AppData/"+fileName;
        if(new File(filePath).exists()) return;
        FileWriter f = new FileWriter(filePath, Charset.defaultCharset(), false);
        f.write(data);
        f.close();
    }

    @Override
    public FileData LoadFile(String path) throws IOException {
        Tuple<String, String> split = SplitName(path);
        String name = split.getA(), ext = split.getB();
        BufferedReader f = getReader(path);
        String cur, buff = "";
        while((cur = f.readLine()) != null) {
            buff = buff+cur+"\n";
        }
        f.close();
        return new FileData(name, ext, path, buff);
    }

    private BufferedReader getReader(String file) throws IOException {
        return new BufferedReader(new FileReader("AppData/"+file, Charset.defaultCharset()));
    }

    private Tuple<String, String> SplitName(String name) {
        String f = name.replace('.', ':');
        String[] split = f.split(":");

        return new Tuple<String,String>(split[0], split[split.length-1]);
    }

    @Override
    public void CreateNewDir(String path) throws IOException {
        File newDir = new File("AppData/"+path);
        if(newDir != null) {
            newDir.mkdirs();
        }
    }

    private DepResourceManager() {
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

    public static DepResourceManager getInstance() {
        return (manager != null) ? manager : new DepResourceManager();
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