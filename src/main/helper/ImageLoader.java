package helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ImageLoader {
    //
    public static BufferedImage getImage(String path) throws IOException {
        File file = new File(path);
        BufferedImage img = ImageIO.read(file);
        return img;
    }
    //
}
