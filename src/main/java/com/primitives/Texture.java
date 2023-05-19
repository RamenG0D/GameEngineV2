package com.primitives;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
    private BufferedImage img;
    private boolean failed;
    
    public Texture(String pathToTexture) {
        try {img = ImageIO.read(new File(pathToTexture));}
        catch(IOException e){failed=true;}
    }

    public BufferedImage getImage() {
        if(!failed) 
        return img;
        else 
        return ArrayToImage(NoTextureFound, 16);
    }

    /** IMPORTANT size of the array must be uniform Example: 16x16, 32x32, etc NOT 14x17 or 10x5 ALSO the array must be a [][] which is a 2 dimensional array */
    public static BufferedImage ArrayToImage(int[][] array, int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                mapTexture(array, x, y, img);
            }
        }
        return img;
    }

    public static void mapTexture(int[][] array, int x, int y, BufferedImage img) {
        switch (array[x][y])
        {
            case 0: {
                img.setRGB(x, y, Color.black.getRGB()); 
                break;
            }
            case 1: {
                img.setRGB(x, y, Color.red.getRGB()); 
                break;
            }
            case 2: {
                img.setRGB(x, y, Color.blue.getRGB()); 
                break;
            }
            case 3: {
                img.setRGB(x, y, Color.green.getRGB()); 
                break;
            }
            case 4: {
                img.setRGB(x, y, Color.ORANGE.getRGB()); 
                break;
            }
            case 5: {
                img.setRGB(x, y, Color.yellow.getRGB()); 
                break;
            }
            case 6: {
                img.setRGB(x, y, Color.cyan.getRGB()); 
                break;
            }
            case 7: {
                img.setRGB(x, y, Color.gray.getRGB()); 
                break;
            }
            case 8: {
                img.setRGB(x, y, Color.lightGray.getRGB()); 
                break;
            }
            case 9: {
                img.setRGB(x, y, Color.darkGray.getRGB());
                break;
            }
            case 10: {
                img.setRGB(x, y, Color.magenta.getRGB()); 
                break;
            }
            default: {
                img.setRGB(x, y, Color.white.getRGB());
                break;
            }
        }
    }

    public static final int[][] NoTextureFound = {
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},
        {0, 0, 0, 0, 0, 0, 0, 0,  10, 10, 10, 10, 10, 10, 10, 10},

        {1, 10, 10, 10, 10, 10, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0},
        {1, 10, 10, 10, 10, 1, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0},
        {10, 10, 10, 10, 10, 1, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0},
        {10, 10, 10, 10, 10, 1, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0},
        {10, 10, 10, 10, 10, 1, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0},
        {10, 10, 10, 10, 10, 1, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0},
        {10, 10, 10, 10, 10, 10, 1, 1,  0, 0, 0, 0, 0, 0, 0, 0},
        {10, 10, 10, 10, 10, 10, 1, 1,  0, 0, 0, 0, 0, 0, 0, 0}
    };

}
