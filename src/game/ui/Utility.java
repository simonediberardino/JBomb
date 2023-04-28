package game.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**

 A utility class containing helper methods for the game.
 */
public class Utility {
    public static boolean isValueInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**

     Converts a dimension in pixels to a dimension in screen units, based on the default screen size.
     @param dim The dimension in pixels to be converted.
     @return The converted dimension in screen units.
     */
    public static int px(int dim) {
        return (int)px((double)dim);
    }

    public static double px(double dim) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        return (dim * ((screenSize.getWidth()) / Dimensions.DEFAULT_SCREEN_SIZE.getWidth()));
    }
    /**

     Loads an image from a file with the given file name.
     @param fileName The file name of the image to be loaded.
     @return The loaded image, or null if the file could not be found or read.
     */
    public static BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
